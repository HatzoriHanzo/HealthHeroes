package com.example.crudsaude.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crudsaude.R;
import com.example.crudsaude.adapter.AgenteDeSaudeAdapter;
import com.example.crudsaude.bean.AgenteDeSaude;
import com.example.crudsaude.bo.AgenteDeSaudeBo;
import com.google.firebase.auth.FirebaseAuth;


public class ListarActivity extends AppCompatActivity {
    private ListView listView;
    private ImageView iv_Logout;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        initViews();
        Toast.makeText(this, "Toque no item que deseja editar.", Toast.LENGTH_LONG).show();
        iv_Logout.setOnClickListener(v -> {
            Toast.makeText(this, "Você está sendo deslogado....", Toast.LENGTH_LONG).show();
            mAuth.signOut();
            Handler handler = new Handler();
            handler.postDelayed(() -> startActivity(new Intent(this, LoginActivity.class)), 2000);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        listarCadastros();

    }

    private void listarCadastros() {

        AgenteDeSaudeAdapter agenteDeSaudeAdapter = new AgenteDeSaudeAdapter(this, new AgenteDeSaudeBo(ListarActivity.this).list());
        listView.setDivider(null);
        listView.setAdapter(agenteDeSaudeAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            AgenteDeSaude agenteDeSaude = agenteDeSaudeAdapter.getItem(position);
            Intent intent = new Intent(ListarActivity.this, EditarDadosActivity.class);
            intent.putExtra("AGENTEID", agenteDeSaude);
            startActivity(intent);
        });


    }

    private void initViews() {
        iv_Logout = findViewById(R.id.logout);
        listView = findViewById(R.id.list);
    }
}