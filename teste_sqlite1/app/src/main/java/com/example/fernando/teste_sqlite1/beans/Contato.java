package com.example.fernando.teste_sqlite1.beans;

import java.io.Serializable;

/**
 * Created by fernando on 13/05/2016.
 */
public class Contato implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;

    private long codigo;
    private String nome;
    private boolean check;
    private String telefone;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean getCheck() {
        return check;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Contato{" + "nome='" + nome + '\'' + ", fone='" + telefone + '\'' + '}';
    }
}
