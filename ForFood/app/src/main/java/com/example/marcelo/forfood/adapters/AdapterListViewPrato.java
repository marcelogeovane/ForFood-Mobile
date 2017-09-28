package com.example.marcelo.forfood.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marcelo.forfood.R;
import com.example.marcelo.forfood.beans.Prato;

import java.util.ArrayList;
import java.util.List;

public class AdapterListViewPrato extends BaseAdapter {
    private List<Prato> lista = new ArrayList<Prato>();
    private Context context;

    public void setLista(List<Prato> lista){
        this.lista = lista;
    }



    public AdapterListViewPrato(Context context) {
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
        Prato itemLista = lista.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_listview_item, parent, false);
        TextView t = (TextView) view.findViewById(R.id.tvNomePrato);
        TextView t1 = (TextView) view.findViewById(R.id.tvDescricaoPrato);
        TextView t2 = (TextView) view.findViewById(R.id.tvNomeEstabelecimento);
        t.setText(itemLista.getNome());
        t1.setText(itemLista.getDescricao());
        t2.setText("Preço: "+String.valueOf(itemLista.getPreco()));
        return view;
    }
}
