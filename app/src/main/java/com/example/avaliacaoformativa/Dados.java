package com.example.avaliacaoformativa;

public class Dados {

    public String chave;
    public String autenticacao;
    public String status;


    @Override
    public String toString(){
        return "Chave: " + chave +
                "\n\rAutenticação: " + autenticacao +
                "\n\rStatus " + status ;
    }

    public Dados(String chave, String autenticacao, String status) {
        this.chave = chave;
        this.autenticacao = autenticacao;
        this.status = status;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public void setAutenticacao(String autenticacao) {
        this.autenticacao = autenticacao;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}