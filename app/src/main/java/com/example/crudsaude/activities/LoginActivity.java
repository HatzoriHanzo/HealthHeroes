package com.example.crudsaude.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.crudsaude.R;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private TextInputLayout etLogin, etPassword;
    private Button btnLogin, btnRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        btnRegister.setOnClickListener(v -> startActivity(new Intent(this, CadastroActivity.class)));
        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = etLogin.getEditText().getText().toString().trim();
        String password = etPassword.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            etLogin.setError("Preencha o campo de email!");
            etLogin.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError("Preencha o campo de email!");
            etPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login Realizado com Sucesso!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Erro ao registrar:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initViews() {
        mAuth = FirebaseAuth.getInstance();
        etLogin = findViewById(R.id.editText_Login);
        etPassword = findViewById(R.id.editTxt_Password);
        btnLogin = findViewById(R.id.btn_Login);
        btnRegister = findViewById(R.id.btn_registrar);
    }


}