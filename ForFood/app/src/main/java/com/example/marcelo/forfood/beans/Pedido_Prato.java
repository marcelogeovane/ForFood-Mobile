package com.example.fernando.teste_sqlite1.beans;

import java.io.Serializable;

/**
 * Created by marcelo on 04/06/17.
 */

public class Pedido_Prato implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;


    private long pratoCodigo;
    private long pedidoCodigo;

    public long getPratoCodigo() {
        return pratoCodigo;
    }

    public void setPratoCodigo(long pratoCodigo) {
        this.pratoCodigo = pratoCodigo;
    }

    public long getPedidoCodigo() {
        return pedidoCodigo;
    }

    public void setPedidoCodigo(long pedidoCodigo) {
        this.pedidoCodigo = pedidoCodigo;
    }

    @Override
    public String toString() {
        return "Pedido_Prato{" +
                "pratoCodigo=" + pratoCodigo +
                ", pedidoCodigo=" + pedidoCodigo +
                '}';
    }
}
