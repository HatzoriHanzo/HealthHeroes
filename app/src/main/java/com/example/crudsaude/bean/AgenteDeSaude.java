package com.example.crudsaude.bean;

import java.io.Serializable;
import java.util.Date;

import mobi.stos.podataka_lib.annotations.Column;
import mobi.stos.podataka_lib.annotations.Entity;
import mobi.stos.podataka_lib.annotations.PrimaryKey;

@Entity
public class AgenteDeSaude implements Serializable {
    @PrimaryKey
    private int id;

    @Column
    private String email;

    @Column
    private String senha;

    @Column
    private String nome;

    @Column
    private java.util.Date dataDeNascimento;

    @Column
    private int telefone;

    @Column
    private String genero;

    @Column
    private String profissao;

    @Column
    private int registro;

    @Column
    private String selfie;
    @Column
    private String especializacao;
    @Column
    private String especializacao2;

    @Override
    public String toString() {
        return "AgenteDeSaude{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", nome='" + nome + '\'' +
                ", dataDeNascimento=" + dataDeNascimento +
                ", telefone=" + telefone +
                ", genero='" + genero + '\'' +
                ", profissao='" + profissao + '\'' +
                ", registro=" + registro +
                ", selfie='" + selfie + '\'' +
                ", especializacao='" + especializacao + '\'' +
                ", especializacao2='" + especializacao2 + '\'' +
                '}';
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }

    public String getEspecializacao2() {
        return especializacao2;
    }

    public void setEspecializacao2(String especializacao2) {
        this.especializacao2 = especializacao2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public java.util.Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public int getRegistro() {
        return registro;
    }

    public void setRegistro(int registro) {
        this.registro = registro;
    }

    public String getSelfie() {
        return selfie;
    }

    public void setSelfie(String selfie) {
        this.selfie = selfie;
    }
}
