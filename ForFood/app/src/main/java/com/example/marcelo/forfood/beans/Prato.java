package com.example.marcelo.forfood.beans;

import java.io.Serializable;

/**
 * Created by marcelo on 04/06/17.
 */

public class Prato implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;

    private long codigo ;
    private String nome;
    private String descricao;
    private double preco;
    private long tempo;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public long getTempo() {
        return tempo;
    }

    public void setTempo(long tempo) {
        this.tempo = tempo;
    }

    @Override
    public String toString() {
        return "Prato{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", tempo=" + tempo +
                '}';
    }
}
