package com.example.marcelo.forfood.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcelo.forfood.PrincipalActivity;
import com.example.marcelo.forfood.R;
import com.example.marcelo.forfood.beans.Cliente;
import com.example.marcelo.forfood.beans.ListaPedido;
import com.example.marcelo.forfood.beans.Pedido;
import com.example.marcelo.forfood.beans.Prato;
import com.example.marcelo.forfood.servicos.DataBase;
import com.example.marcelo.forfood.sinc.JSONDados;
import com.example.marcelo.forfood.sinc.JSONParser;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class FinalizarPedidoFragment extends Fragment {

    private List<ListaPedido> listaPedidos;
    private List<ListaPedido> listaJSON;
    private List<Prato> pratos;
    private List<Cliente> cliente;
    private List<Pedido> pedido ;
    private DataBase db ;
    private EditText etEndereco;
    private TextView tvValor;
    private double preco;
    private SimpleDateFormat dateFormat;
    private Date data;
    private Date data_atual;
    private String data_completa;
    private Calendar cal;
    private Button finaliza;
    private double preco1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finalizar_pedido, container, false);

        etEndereco = (EditText) view.findViewById(R.id.editTextEndereço);
        tvValor = (TextView) view.findViewById(R.id.textViewValor);
        finaliza = (Button)view.findViewById(R.id.buttonFinalizar);

        db = new DataBase(getContext());
        pedido = new ArrayList<Pedido>();
        listaPedidos = new ArrayList<ListaPedido>();
        listaPedidos = db.ListaPedido_findAll();
        listaJSON = new ArrayList<ListaPedido>();
        pratos = db.prato_findAll();
        preco = 0;
        Log.d("[IFMG]", "ANTES DO FOR Preco: " + preco);
        for (ListaPedido listapedido1: listaPedidos) {
            for (Prato pratos1 : pratos) {
                if (pratos1.getCodigo() == listapedido1.getCodigoPrato()) {
                    listaJSON.add(listapedido1);
                    db.deleteListPedido(listapedido1);
                    Log.d("[IFMG]", "DENTRO DO IF Codigo: " + listapedido1.getCodigoPrato());
                    preco = preco + pratos1.getPreco();
                    Log.d("[IFMG]", "Preco: " + preco);
                    if (preco != 0){
                        preco1 = preco1 + preco;
                    }
                    Log.d("[IFMG]", "Preco1: " + preco1);

                } else {

                }


            }

            listaPedidos = null;
            preco=0;
            Log.d("[IFMG]", "FORA DO IF DENTRO DO FOR Preco: " + preco);
//            if (!listaPedidos.isEmpty() && listaPedidos == null){
              //  Log.d("[IFMG]", "FORA DO IF DENTRO DO FOR ListaPedido: " + listaPedidos.toString());
            //}

        }



        cliente = db.findAllCliente();

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        data = new Date();
        cal = Calendar.getInstance();
        cal.setTime(data);
        data_atual = cal.getTime();
        data_completa = dateFormat.format(data_atual);
        // Inflate the layout for this fragment

            Log.d("[IFMG]", "Preco1: " + preco1);

            Pedido p = new Pedido();
            p.setEndereço(String.valueOf(etEndereco.getText().toString()));
            p.setValorTotal(preco1);
            p.setCliente_codigo(cliente.get(0).getCodigo());
            p.setData(data_completa);
            p.setCodigo(1);
            Log.d("[IFMG]", "Pedido: " + p.toString());
            db.savePedido(p);
            tvValor.setText(String.valueOf(preco1).toString());




        return  view;
    }

}
