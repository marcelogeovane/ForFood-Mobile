package com.example.marcelo.forfood.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marcelo on 04/06/17.
 */

public class Pedido implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;

    private long codigo ;
    private double cliente_codigo;
    private double valorTotal ;
    private String data;
    private String endereço;


    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }


    public double getCliente_codigo() {
        return cliente_codigo;
    }

    public void setCliente_codigo(double cliente_codigo) {
        this.cliente_codigo = cliente_codigo;
    }

    public double getValorTotal() { return valorTotal; }

    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEndereço() { return endereço; }

    public void setEndereço(String endereço) { this.endereço = endereço; }

    @Override
    public String toString() {
        return "Pedido - " +
                "codigo=" + codigo +
                ", cliente_codigo=" + cliente_codigo +
                ", valorTotal=" + valorTotal +
                ", data=" + data +
                ", endereço='" + endereço + '\'';
    }
}
