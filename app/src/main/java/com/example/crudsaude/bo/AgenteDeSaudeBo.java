package com.example.crudsaude.bo;

import android.content.Context;

import com.example.crudsaude.bean.AgenteDeSaude;
import com.example.crudsaude.dao.AgenteDeSaudeDao;

import mobi.stos.podataka_lib.interfaces.IOperations;
import mobi.stos.podataka_lib.service.AbstractService;

public class AgenteDeSaudeBo extends AbstractService<AgenteDeSaude> {
    private AgenteDeSaudeDao agenteDeSaudeDao;

    public AgenteDeSaudeBo(Context context) {
        super();
        this.agenteDeSaudeDao = new AgenteDeSaudeDao(context);
    }

    @Override
    protected IOperations<AgenteDeSaude> getDao() { return agenteDeSaudeDao; }
}
