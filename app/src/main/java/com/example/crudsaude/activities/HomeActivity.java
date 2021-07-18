package com.example.crudsaude.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;

import android.widget.Button;
import android.widget.ImageView;

import android.widget.Toast;

import com.example.crudsaude.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private Button btn_cadastrar, btn_listar;
    private ImageView iv_Logout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadViews();
        btn_cadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
        });
        btn_listar.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, ListarActivity.class);
            startActivity(intent1);
        });
        iv_Logout.setOnClickListener(v -> {
            Toast.makeText(this, "Você está sendo deslogado....", Toast.LENGTH_LONG).show();
            mAuth.signOut();
            Handler handler = new Handler();
            handler.postDelayed(() -> startActivity(new Intent(this, LoginActivity.class)), 2000);
        });

    }

    private void loadViews() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Você não está logado... Redirecionando..", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        btn_listar = findViewById(R.id.btn_listar);
        iv_Logout = findViewById(R.id.logout);

    }
}