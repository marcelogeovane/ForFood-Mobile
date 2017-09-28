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
    private String Status;
    private long cliente_codigo;
    private double valorTotal ;
    private Date data;
    private String endereço;


    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public long getCliente_codigo() {
        return cliente_codigo;
    }

    public void setCliente_codigo(long cliente_codigo) {
        this.cliente_codigo = cliente_codigo;
    }

    public double getValorTotal() { return valorTotal; }

    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public Date getData() { return data; }

    public void setData(Date data) { this.data = data; }

    public String getEndereço() { return endereço; }

    public void setEndereço(String endereço) { this.endereço = endereço; }

    @Override
    public String toString() {
        return "Pedido - " +
                "codigo=" + codigo +
                ", Status='" + Status + '\'' +
                ", cliente_codigo=" + cliente_codigo +
                ", valorTotal=" + valorTotal +
                ", data=" + data +
                ", endereço='" + endereço + '\'';
    }
}
