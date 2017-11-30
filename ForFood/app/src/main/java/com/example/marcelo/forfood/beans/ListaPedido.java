package com.example.marcelo.forfood.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 29/11/2017.
 */

public class ListaPedido {

    private long codigo;
    private long codigoPrato;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public long getCodigoPrato() {
        return codigoPrato;
    }

    public void setCodigoPrato(long codigoPrato) {
        this.codigoPrato = codigoPrato;
    }

    @Override
    public String toString() {
        return "ListaPedido{" +
                "codigo=" + codigo +
                ", codigoPrato=" + codigoPrato +
                '}';
    }
}

