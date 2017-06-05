package com.example.fernando.teste_sqlite1.beans;

import java.io.Serializable;

/**
 * Created by marcelo on 04/06/17.
 */

public class Pedido implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;

    private long codigo ;
    private String Status;
    private long cliente_codigo;

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

    @Override
    public String toString() {
        return "Pedido{" +
                "codigo=" + codigo +
                ", Status='" + Status + '\'' +
                ", cliente_codigo=" + cliente_codigo +
                '}';
    }
}
