package com.example.marcelo.forfood.beans;

import java.io.Serializable;

/**
 * Created by marcelo on 04/06/17.
 */

public class Cliente implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;

    private boolean check;
    private long codigo;
    private String nome;
    private long telefone;
    private String endereco;
    private String senha;
    private String email;
    private long cpf;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
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

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
                ", endereco='" + endereco + '\'' +
                ", senha='" + senha + '\'' +
                ", email='" + email + '\'' +
                ", cpf=" + cpf +
                '}';
    }
}
