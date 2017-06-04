package com.example.fernando.teste_sqlite1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.fernando.teste_sqlite1.beans.Contato;
import com.example.fernando.teste_sqlite1.servicos.ContatoDB;

import java.util.List;

public class PrincipalActivity extends AppCompatActivity {
    ContatoDB bd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        bd = new ContatoDB(getBaseContext());

        atualizaCampoTexto();
    }

    public void onClickCadastrar(View v){
        EditText etNome =  (EditText)findViewById(R.id.etNome);
        EditText etTelefone =  (EditText)findViewById(R.id.etTelefone);

        Contato c = new Contato();
        c.setNome(etNome.getText() + "");
        c.setTelefone(etTelefone.getText() + "");

        bd.save(c);

        atualizaCampoTexto();

    }
    public void atualizaCampoTexto(){
        EditText lista =  (EditText)findViewById(R.id.etLista);


        //List<Contato> contatos = bd.findAll();
        List<Contato> contatos = bd.findBySql("SELECT conCodigo, conNome, conTelefone FROM contato;");

        String texto = "";
        for(int i = 0; i<contatos.size();i++){
            texto +=  contatos.get(i).toString()+"\n";
        }
        lista.setText(texto);
    }

    public void onClickDeletaTudo(View v){
        bd.execSQL("DELETE FROM contato WHERE conCodigo > 0;");
        atualizaCampoTexto();
    }
}
