package com.example.crudsaude.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.crudsaude.R;
import com.example.crudsaude.bean.AgenteDeSaude;
import com.example.crudsaude.bo.AgenteDeSaudeBo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import mobi.stos.podataka_lib.exception.NoPrimaryKeyFoundException;
import mobi.stos.podataka_lib.exception.NoPrimaryKeyValueFoundException;

public class AgenteDeSaudeAdapter extends ArrayAdapter<AgenteDeSaude> {

    public AgenteDeSaudeAdapter(Context context, List<AgenteDeSaude> list) {
        super(context, 0, list);
    }


    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.adapter_agente_de_saude, parent, false);
        AgenteDeSaude agenteDeSaude = getItem(i);

        DateFormat dateFormatDia = new SimpleDateFormat("dd/MM/yyyy");
        TextView nome = root.findViewById(R.id.edtText_nome);
        TextView profissao = root.findViewById(R.id.edtText_profissao);
        TextView email = root.findViewById(R.id.edtText_email);
        TextView genero = root.findViewById(R.id.edtText_genero);
        TextView dataNascimento = root.findViewById(R.id.edtText_dataNasci);
        TextView crm = root.findViewById(R.id.crm);
        ImageView ivDelete = root.findViewById(R.id.delete);
        ImageView foto = root.findViewById(R.id.photo);

        nome.setText("Nome: " + agenteDeSaude.getNome());
        profissao.setText("Profissão: " + agenteDeSaude.getProfissao());
        email.setText("E-mail: " + agenteDeSaude.getEmail());
        genero.setText("Gênero: " + agenteDeSaude.getGenero());
        dataNascimento.setText("Data de Nascimento:"+dateFormatDia.format(agenteDeSaude.getDataDeNascimento()));
        crm.setText(("CRM: "+Integer.toString(agenteDeSaude.getRegistro())));
        Glide.with(getContext()).load(agenteDeSaude.getSelfie()).into(foto);

        ivDelete.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Deletar?");
            builder.setMessage("Quer mesmo deletar o Agente de Saúde ?");
            builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deletar(agenteDeSaude);
                }
            });
            builder.setNegativeButton("Não", null);
            builder.create().show();

        });


        return root;
    }

    private void deletar(AgenteDeSaude agenteDeSaude) {
        AgenteDeSaudeBo agenteDeSaudeBo = new AgenteDeSaudeBo(getContext());
        try {
            agenteDeSaudeBo.delete(agenteDeSaude);
        } catch (NoPrimaryKeyFoundException | NoPrimaryKeyValueFoundException e) {
            e.printStackTrace();
        }
        Toast.makeText(getContext(), "Deletado com Sucesso!", Toast.LENGTH_SHORT).show();
        remove(agenteDeSaude);
        notifyDataSetChanged();
    }

}


