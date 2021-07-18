package com.example.crudsaude.dao;

import android.content.Context;

import com.example.crudsaude.bean.AgenteDeSaude;

import mobi.stos.podataka_lib.repository.AbstractRepository;

public class AgenteDeSaudeDao extends AbstractRepository<AgenteDeSaude> {
    public AgenteDeSaudeDao(Context context) {
        super(context, AgenteDeSaude.class);
    }
}
