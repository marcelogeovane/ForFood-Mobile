package com.example.marcelo.forfood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marcelo.forfood.R;
import com.example.marcelo.forfood.beans.Pedido;
import com.example.marcelo.forfood.beans.Prato;

import java.util.ArrayList;
import java.util.List;

public class AdapterListViewPedido extends BaseAdapter {
    private List<Pedido> lista = new ArrayList<Pedido>();
    private Context context;

    public void setLista(List<Pedido> lista){
        this.lista = lista;
    }



    public AdapterListViewPedido(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pedido itemLista = lista.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_listview_item, parent, false);
        TextView t = (TextView) view.findViewById(R.id.tvNomePrato);
        t.setText(itemLista.toString());
        return view;
    }
}
