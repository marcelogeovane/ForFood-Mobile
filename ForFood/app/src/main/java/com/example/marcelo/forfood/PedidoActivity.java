package com.example.marcelo.forfood;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marcelo.forfood.beans.Pedido;
import com.example.marcelo.forfood.servicos.DataBase;
import com.example.marcelo.forfood.sinc.JSONDados;
import com.example.marcelo.forfood.sinc.JSONParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PedidoActivity extends AppCompatActivity {

    private ViewPager viewPager;//usado para fazer o evento com efeito ao clicar
    private DataBase db;
    private List<Pedido> pedido;
    private EditText etEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        db = new DataBase(getApplicationContext());

        pedido = new ArrayList<Pedido>();
        pedido = db.pedido_findAll();
        Log.d("[IFMG]", "ARRAYLIST ON CREATE!!!!!!!!!!!!!: " + pedido.toString());
        etEndereco = (EditText) findViewById(R.id.editTextEndereço);


        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));

        // Configura as Tabs
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Cardápio").setTabListener(new MyTabListener(viewPager, 0)));
        actionBar.addTab(actionBar.newTab().setText("Dados").setTabListener(new MyTabListener(viewPager, 1)));

        // Se o ViewPager troca de página, atualiza a Tab.
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int idx) {
                // Se fizer swipe no ViewPager, atualiza a tab
                actionBar.setSelectedNavigationItem(idx);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });


    }

    public void onClickFinaliza(View v){


        Log.d("[IFMG]", "ARRAYLIST ONCLIK: " + pedido.toString());
        int posicao = pedido.size() - 1;
        if (posicao > 0) {
            requisitaPost(JSONDados.geraJsonPedido(
                    pedido.get(posicao).getData(),
                    pedido.get(posicao).getValorTotal(),
                    pedido.get(posicao).getCliente_codigo(),
                    etEndereco.getText().toString(),
                    pedido.get(posicao).getCodigo()),
                    "https://forfood.000webhostapp.com/json3.php");
        }else if(posicao < 0){
            Toast.makeText(getApplicationContext(), "Seu pedido não foi registrado no banco de Dados local", Toast.LENGTH_SHORT).show();
        }else if (posicao == 0 ){
            requisitaPost(JSONDados.geraJsonPedido(
                    pedido.get(0).getData(),
                    pedido.get(0).getValorTotal(),
                    pedido.get(0).getCliente_codigo(),
                    etEndereco.getText().toString(),
                    pedido.get(0).getCodigo()),
                    "https://forfood.000webhostapp.com/json3.php");
        }


    }


















    public void requisitaPost(final String parametroJSON, final String URL_) {


        //thread obrigatória para realização da requisição pode ser usado com outras formas de thread
        new Thread(new Runnable() {
            public void run() {
                final JSONParser jsonParser = new JSONParser();
                JSONObject json = null;
                try {
                    //prepara parâmetros para serem enviados via método POST
                    HashMap<String, String> params = new HashMap<>();
                    params.put("dados", parametroJSON);

                    Log.d("[IFMG]", parametroJSON);
                    Log.d("[IFMG]", "JSON Envio Iniciando...");

                    //faz a requisição POST e retorna o que o webservice REST envoiu dentro de json
                    json = jsonParser.makeHttpRequest(URL_, "POST", params);

                    Log.d("[IFMG]", " JSON Envio Terminado...");

                    //Mostra no log e retorna o que o json retornou, caso não retornou nulo
                    if (json != null) {
                        Log.d("[IFMG]", json.toString());
                        //return json;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("[IFMG]", "finalizando baixar defeitos");

                //----------------------------------------------
                //PÓS DOWNLOAD
                //----------------------------------------------

                //teste para ferificar se o json chegou corretamente e foi interpretado
                if (json != null) {
                    //------------------------------------------------------------
                    //AQUI SE PEGA O JSON RETORNADO E TRATA O QUE DEVE SER TRATADO
                    //------------------------------------------------------------
                    //  final String resp = interpretaJSON_Campos(json);
                   runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("[IFMG]", " RESULTADOOO"+jsonParser.result());


                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Falha na conexão!!!", Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        }).start();
    }


}
