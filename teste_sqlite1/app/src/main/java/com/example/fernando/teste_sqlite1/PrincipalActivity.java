package com.example.fernando.teste_sqlite1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.fernando.teste_sqlite1.beans.Cliente;
import com.example.fernando.teste_sqlite1.beans.Pedido;
import com.example.fernando.teste_sqlite1.beans.Pedido_Prato;
import com.example.fernando.teste_sqlite1.beans.Prato;
import com.example.fernando.teste_sqlite1.servicos.DataBase;

import java.util.List;

public class PrincipalActivity extends AppCompatActivity {
    DataBase bd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        bd = new DataBase(getBaseContext());

        atualizaCampoTexto();
    }

    public void onClickCadastrar(View v){
        EditText etNome =  (EditText)findViewById(R.id.etNome);
        EditText etTelefone =  (EditText)findViewById(R.id.etTelefone);



        Cliente c = new Cliente();
        c.setNome(etNome.getText().toString());
        c.setTelefone(999);
        c.setCpf(000);
        c.setEndereco(etTelefone.getText()+"");
        c.setSenha("teste");
        c.setEmail("teste");
        bd.saveCliente(c);



        Prato p = new Prato();
        p.setTempo(1);
        p.setPreco(1);
        p.setDescricao(etTelefone.getText()+"");
        p.setNome(etNome.getText()+"");
        bd.savePrato(p);

        Pedido ped = new Pedido();
        ped.setCliente_codigo(c.getCodigo());
        ped.setStatus("Realizado");
        bd.savePedido(ped);

        Pedido_Prato pedPra = new Pedido_Prato();
        pedPra.setPedidoCodigo(ped.getCodigo());
        pedPra.setPratoCodigo(p.getCodigo());
        bd.savePedidoPrato(pedPra);


        atualizaCampoTexto();

    }
    public void atualizaCampoTexto(){
        EditText lista =  (EditText)findViewById(R.id.etLista);


        List<Prato> contatos = bd.Prato_findAll();
        List<Cliente> clientes = bd.findAll();
        List<Pedido> pedidos = bd.pedido_findAll();
        List<Pedido_Prato> pedidosPratos = bd.pedidoPrato_findAll();
        //List<Cliente> contatos = bd.findBySql("SELECT cliCodigo, cliNome, cliTelefone FROM cliente;");

        String texto = "";
        String texto2 = "";
        String texto3 = "";
        String texto4 = "";
        for(int i = 0; i< contatos.size();i++){
            texto +=  pedidosPratos.get(i).toString()+"\n";

        }
        lista.setText(texto);
    }

    public void onClickDeletaTudo(View v){
        bd.execSQL("DELETE FROM cliente WHERE cliCodigo > 0;");
        atualizaCampoTexto();
    }
}
