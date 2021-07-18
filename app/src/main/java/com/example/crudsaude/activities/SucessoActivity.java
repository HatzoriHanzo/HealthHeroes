package com.example.crudsaude.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import com.example.crudsaude.R;
import com.example.crudsaude.bean.AgenteDeSaude;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SucessoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso);

        Handler handler = new Handler();
        handler.postDelayed(() -> startActivity(new Intent(this, HomeActivity.class)), 3000);


    }
}