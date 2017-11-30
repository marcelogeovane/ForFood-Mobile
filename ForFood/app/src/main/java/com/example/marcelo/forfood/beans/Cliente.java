package com.example.marcelo.forfood.beans;

import java.io.Serializable;

/**
 * Created by marcelo on 04/06/17.
 */

public class Cliente implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;

    private boolean check;
    private double codigo;
    private String nome;
    private long telefone;
    private String email;
    private long cpf;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public double getCodigo() {
        return codigo;
    }

    public void setCodigo(double codigo) {
        this.codigo = codigo;
    }

    public boolean getCheck() {
        return check;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "check=" + check +
                ", codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", telefone=" + telefone +
                ", email='" + email + '\'' +
                ", cpf=" + cpf +
                '}';
    }
}
