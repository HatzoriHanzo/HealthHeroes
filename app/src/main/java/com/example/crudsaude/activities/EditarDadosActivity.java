package com.example.crudsaude.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.bumptech.glide.Glide;
import com.example.crudsaude.R;
import com.example.crudsaude.bean.AgenteDeSaude;
import com.example.crudsaude.bo.AgenteDeSaudeBo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import mobi.stos.podataka_lib.exception.NoPrimaryKeyFoundException;
import mobi.stos.podataka_lib.exception.NoPrimaryKeyValueFoundException;

public class EditarDadosActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AgenteDeSaude agenteDeSaude = new AgenteDeSaude();
    private final int SELECT_PICTURE = 200;
    private FirebaseAuth mAuth;
    private ImageView IVPreviewImage,iv_Logout;
    private Button mBtn_Cadastrarimagem, mBtn_Salvar;
    private EditText mEditText_Nome, mEditText_Email, mEditText_Senha, mEditText_Telefone, mEditText_Especializacao1, mEditText_Especializacao2,mEditText_Crm, mEditText_Data;
    private CheckBox mCheckBox_Masculino, mCheckBox_Feminino, mCheckBox_Nda;
    private String generoo,profissao;
    private java.util.Date data;
    private Calendar calendar;
    private Uri selectedImageUri;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_dados);
        initViews();
        loadViews();

        iv_Logout.setOnClickListener(v -> {
            Toast.makeText(this, "Você está sendo deslogado....", Toast.LENGTH_LONG).show();
            mAuth.signOut();
            Handler handler = new Handler();
            handler.postDelayed(() -> startActivity(new Intent(this,LoginActivity.class)), 2000);
        });

        mBtn_Cadastrarimagem.setOnClickListener(v -> imageChooser());

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

        mBtn_Salvar.setOnClickListener(view2-> {

                agenteDeSaude.setNome(mEditText_Nome.getText().toString());
                agenteDeSaude.setEmail(mEditText_Email.getText().toString());
                agenteDeSaude.setSenha(mEditText_Senha.getText().toString());
                mAuth.createUserWithEmailAndPassword(mEditText_Email.getText().toString(),mEditText_Senha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.e("hey there bud","it worked!");
                        }
                    }
                });
                agenteDeSaude.setTelefone(Integer.parseInt(mEditText_Telefone.getText().toString()));
                agenteDeSaude.setProfissao(profissao);
                agenteDeSaude.setDataDeNascimento(data);
                if (selectedImageUri == null){
                    agenteDeSaude.setSelfie(agenteDeSaude.getSelfie());
                }
                else{
                    agenteDeSaude.setSelfie(selectedImageUri.toString());
                }

                if (mCheckBox_Masculino.isChecked()) {
                    agenteDeSaude.setGenero("Masculino"); }
                else if (mCheckBox_Feminino.isChecked()) {
                    agenteDeSaude.setGenero("Feminino"); }
                else {
                    agenteDeSaude.setGenero("Não Informado");
                }
                agenteDeSaude.setRegistro(Integer.parseInt(mEditText_Crm.getText().toString()));
                agenteDeSaude.setEspecializacao(mEditText_Especializacao1.getText().toString());
                agenteDeSaude.setEspecializacao2(mEditText_Especializacao2.getText().toString());
                AgenteDeSaudeBo agentedeSaudeBo = new AgenteDeSaudeBo(this);
                try {
                    agentedeSaudeBo.update(agenteDeSaude);
                    startActivity(new Intent(this, SucessoActivity.class));
                } catch (NoPrimaryKeyFoundException | NoPrimaryKeyValueFoundException e) {
                    e.printStackTrace();
                }



        });
    }

    private void loadViews() {
        agenteDeSaude = (AgenteDeSaude) getIntent().getSerializableExtra("AGENTEID");
        DateFormat dateFormatDia = new SimpleDateFormat("dd/MM/yyyy");

        mEditText_Nome.setText(agenteDeSaude.getNome());
        generoo = agenteDeSaude.getGenero();
        switch (generoo) {
            case "Masculino":
                mCheckBox_Masculino.setChecked(true);
                break;
            case "Feminino":
                mCheckBox_Feminino.setChecked(true);
                break;
            case "Não Informado":
                mCheckBox_Nda.setChecked(true);
                break;
        }
        mEditText_Email.setText(agenteDeSaude.getEmail());
        mEditText_Data.setText(dateFormatDia.format(agenteDeSaude.getDataDeNascimento()));
        Glide.with(getBaseContext()).load(agenteDeSaude.getSelfie()).into(IVPreviewImage);
        mEditText_Telefone.setText(Integer.toString(agenteDeSaude.getTelefone()));
        mEditText_Senha.setText((agenteDeSaude.getSenha()));
        mEditText_Crm.setText(Integer.toString(agenteDeSaude.getRegistro()));
        mEditText_Especializacao1.setText(agenteDeSaude.getEspecializacao());
        mEditText_Especializacao2.setText(agenteDeSaude.getEspecializacao2());
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

                }
            }
        }
    }


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
        mEditText_Crm = findViewById(R.id.crm);
        mEditText_Especializacao1 = findViewById(R.id.especializacao1);
        mEditText_Especializacao2 = findViewById(R.id.especializacao2);
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