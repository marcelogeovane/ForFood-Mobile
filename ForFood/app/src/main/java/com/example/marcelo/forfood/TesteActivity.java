package com.example.marcelo.forfood;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marcelo.forfood.sinc.JSONDados;
import com.example.marcelo.forfood.sinc.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;



public class TesteActivity extends AppCompatActivity {

    private EditText etN1, etN2, etResp;
    private String n1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        etN1 = (EditText) findViewById(R.id.etN1);
        etN2 = (EditText) findViewById(R.id.etN2);
        etResp = (EditText) findViewById(R.id.etResp);
        n1 = "1" ;

        etN1.setText("1");

        requisitaPost(
                JSONDados.geraJsonTeste(
                        n1),
                "https://jsonforfood1.000webhostapp.com/json1.php"
        );

    }

    ProgressDialog pDialog;

    public void onCLick(View v) {
        //chama o metodo de requisição passando o json como parametro e a URL do webservice
        requisitaPost(
                JSONDados.geraJsonTeste(
                      etN1.getText().toString()),
                "https://jsonforfood1.000webhostapp.com/json1.php"
        );
    }

    public void requisitaPost(final String parametroJSON, final String URL_) {


        //thread obrigatória para realização da requisição pode ser usado com outras formas de thread
        new Thread(new Runnable() {
            public void run() {
                JSONParser jsonParser = new JSONParser();
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
                    final String resp = interpretaJSON_Aritimetica(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            etResp.setText(resp);
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

    /**
     * Método criado para receber, interpretar o obj json e retornar uma string formatada do mesmo
     *
     * @param json
     * @return string formatada
     */
    public String interpretaJSON_Aritimetica(JSONObject json) {
        String texto = "";
        try {
            JSONArray linhas = null;
            //Printando na string os elementos identificados nela
            try {
                linhas = (JSONArray) json.get("pedido");//pega vetor do json recebido
                if (linhas.length() > 0) {//verifica we exite algum registro recebido do servidor
                    for (int i = 0; i < linhas.length(); i++) {
                        JSONObject linha = (JSONObject) linhas.get(i);
                        texto += "pedCodigo: " + linha.get("pedCodigo")+", ";
                        texto += "pedStatus: " + linha.getString("pedStatus") + ", ";
                        texto += "Cliente_cliCodigo: " + linha.getString("Cliente_cliCodigo")+ ".\n";
                        Log.d("[IFMG]", "resultado: " + texto);
                    }
                }
            } catch (Exception c) {
                c.printStackTrace();
                Log.d("[IFMG]", "Erro: " + c.getMessage());
            }
        } catch (Exception e) {//JSONException e) {
            e.printStackTrace();
        }
        return texto;
    }

    public static ProgressDialog gerarDialogIndeterminado(String mensagem, Context activityContexto) {
        ProgressDialog pDialog = new ProgressDialog(activityContexto);
        pDialog.setMessage(mensagem);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        //pDialog.show();
        return pDialog;
    }
}
