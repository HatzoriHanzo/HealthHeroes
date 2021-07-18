package com.example.crudsaude.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crudsaude.R;
import com.example.crudsaude.bean.AgenteDeSaude;
import com.example.crudsaude.bo.AgenteDeSaudeBo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import static android.util.Patterns.EMAIL_ADDRESS;

public class CadastroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final int SELECT_PICTURE = 200;
    private FirebaseAuth mAuth;
    private ImageView IVPreviewImage, iv_Logout;
    private Button mBtn_Cadastrarimagem, mBtn_Salvar;
    private EditText mEditText_Nome, mEditText_Email, mEditText_Senha, mEditText_Telefone, mEditText_Especializacao1, mEditText_Especializacao2, mEditText_Data, mEditText_Crm;
    private CheckBox mCheckBox_Masculino, mCheckBox_Feminino, mCheckBox_Nda;
    private String profissao;
    private java.util.Date data;
    private Calendar calendar;
    private Uri selectedImageUri;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        initViews();

        mBtn_Cadastrarimagem.setOnClickListener(v -> imageChooser());

        iv_Logout.setOnClickListener(v -> {
            Toast.makeText(this, "Você está sendo deslogado....", Toast.LENGTH_LONG).show();
            mAuth.signOut();
            Handler handler = new Handler();
            handler.postDelayed(() -> startActivity(new Intent(this, LoginActivity.class)), 2000);
        });

        mBtn_Salvar.setOnClickListener(v -> {
            AgenteDeSaude agenteDeSaude = new AgenteDeSaude();
            if (mEditText_Nome.getText().toString().isEmpty() || mEditText_Nome.getText().toString() == null || mEditText_Nome.getText().toString().isEmpty() || mEditText_Nome.getText().toString() == null
                    || mEditText_Senha.getText().toString().isEmpty() || mEditText_Telefone.getText().toString().isEmpty() || mEditText_Telefone.getText().toString() == null
                    || profissao.isEmpty() || profissao == null || data == null || mEditText_Email.getText().toString().isEmpty() || !EMAIL_ADDRESS.matcher(mEditText_Email.getText().toString()).matches() || mEditText_Crm.getText().toString().isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos para prosseguir.", Toast.LENGTH_SHORT).show();
            } else if (mEditText_Senha.getText().toString().length() < 6) {
                Toast.makeText(this, "Sua senha precisa ser maior que 6 digitos!", Toast.LENGTH_SHORT).show();
            } else {
                agenteDeSaude.setNome(mEditText_Nome.getText().toString());
                agenteDeSaude.setEmail(mEditText_Email.getText().toString());
                agenteDeSaude.setSenha(mEditText_Senha.getText().toString());

                mAuth.createUserWithEmailAndPassword(mEditText_Email.getText().toString(), mEditText_Senha.getText().toString());
                mAuth.signInWithEmailAndPassword(mEditText_Email.getText().toString(), mEditText_Senha.getText().toString()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(CadastroActivity.this, "Login Realizado com Sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });
                agenteDeSaude.setTelefone(Integer.parseInt(mEditText_Telefone.getText().toString()));

                agenteDeSaude.setProfissao(profissao);

                agenteDeSaude.setDataDeNascimento(data);

                agenteDeSaude.setSelfie(selectedImageUri.toString());

                if (mCheckBox_Masculino.isChecked()) {
                    agenteDeSaude.setGenero("Masculino");
                } else if (mCheckBox_Feminino.isChecked()) {
                    agenteDeSaude.setGenero("Feminino");
                } else {
                    agenteDeSaude.setGenero("Não Informado");
                }
                agenteDeSaude.setRegistro(Integer.parseInt(mEditText_Crm.getText().toString()));
                agenteDeSaude.setEspecializacao(mEditText_Especializacao1.getText().toString());
                agenteDeSaude.setEspecializacao2(mEditText_Especializacao2.getText().toString());

                Intent intent = new Intent(this, SucessoActivity.class);

                AgenteDeSaudeBo agentedeSaudeBo = new AgenteDeSaudeBo(this);

                agentedeSaudeBo.insert(agenteDeSaude);

                startActivity(intent);
            }

        });
        mEditText_Data.setOnClickListener(view1 -> {
            Calendar mcurrentTime = Calendar.getInstance();
            calendar = Calendar.getInstance();

            int ano = mcurrentTime.get(Calendar.YEAR);
            int mes = mcurrentTime.get(Calendar.MONTH);
            int dia = mcurrentTime.get(Calendar.DATE);

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


            datePickerDialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
                calendar.set(i, i1, i2);
                mEditText_Data.setText(dateFormat.format(calendar.getTime()));
                data = calendar.getTime();
            }, ano, mes, dia);
            datePickerDialog.setTitle(this.getString(R.string.selecione_um_horario));
            datePickerDialog.show();
        });
    }


    private void imageChooser() {


        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);


        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    IVPreviewImage.setImageURI(selectedImageUri);
                    Log.e("uri:", "--" + selectedImageUri);
                    Log.e("uri:", "--" + selectedImageUri.getPath());
                }
            }
        }
    }
//    private void selectImage(Context context) {
//        final CharSequence[] options = { "Tire uma foto", "Abra da Galeria","Cancelar" };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Choose your profile picture");
//
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (options[item].equals("Tire uma foto")) {
//                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePicture, 0);
//
//                } else if (options[item].equals("Abra da Galeria")) {
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto , 1);
//
//                } else if (options[item].equals("Cancelar")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_CANCELED) {
//            switch (requestCode) {
//                case 0:
//                    if (resultCode == RESULT_OK && data != null) {
//                         selectedImage = (Bitmap) data.getExtras().get("data");
//                        IVPreviewImage.setImageBitmap(selectedImage);
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                         byteArray = stream.toByteArray();
//                    }
//
//                    break;
//                case 1:
//                    if (resultCode == RESULT_OK && data != null) {
//                        selectedImageUri = data.getData();
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        if (selectedImageUri != null) {
//                            Cursor cursor = getContentResolver().query(selectedImageUri,
//                                    filePathColumn, null, null, null);
//                            if (cursor != null) {
//                                cursor.moveToFirst();
//
//                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                String picturePath = cursor.getString(columnIndex);
////                                IVPreviewImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                IVPreviewImage.setImageURI(selectedImageUri);
//                                cursor.close();
//                            }
//                        }
//
//                    }
//                    break;
//            }
//        }
//    }


    private void initViews() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.profissoes_spinner, android.R.layout.simple_dropdown_item_1line);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mBtn_Cadastrarimagem = findViewById(R.id.btn_foto);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
        mEditText_Nome = findViewById(R.id.nome);
        mEditText_Email = findViewById(R.id.email);
        mEditText_Senha = findViewById(R.id.senha);
        mEditText_Telefone = findViewById(R.id.telefone);
        mEditText_Especializacao1 = findViewById(R.id.especializacao1);
        mEditText_Especializacao2 = findViewById(R.id.especializacao2);
        mEditText_Crm = findViewById(R.id.crm);
        mCheckBox_Masculino = findViewById(R.id.checkBoxMasculino);
        mCheckBox_Feminino = findViewById(R.id.checkBoxFeminino);
        mCheckBox_Nda = findViewById(R.id.checkBoxNDA);
        mEditText_Data = findViewById(R.id.data);
        mBtn_Salvar = findViewById(R.id.btn_Salvar);
        iv_Logout = findViewById(R.id.logout);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        profissao = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}