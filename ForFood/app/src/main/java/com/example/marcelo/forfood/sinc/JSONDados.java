package com.example.marcelo.forfood.sinc;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fernando on 27/09/2016.
 */
public class JSONDados {

    //URLs DOS RESTS A SEREM CONSUMIDOS
    public final static String URL_SERVICO1 = "localhost/rest1.php";
    public final static String URL_SERVICO2 = "http://...";

    public static String geraJsonUsuario(String login, String senha) {
        ArrayList<JSONObject> tabela = new ArrayList<JSONObject>();
        JSONObject registro = new JSONObject();
        //cria um registro primeiro
        try {
            registro.put("proLogin", login);
            registro.put("proSenha", senha);
        } catch (JSONException k) {
            Log.d("IFMG", "" + k.getMessage());
        }
        tabela.add(registro);        //adiciona registro à lista de registros

        //adiciona tabela
        JSONObject bd = new JSONObject();
        try {
            //ADICIONA O IDENTIFICADOR DA TABELA NO JSON
            bd.putOpt("login", (Object) tabela);
        } catch (JSONException u) {
        }

        String f = bd.toString();
        f = f.replace("\\", "");//gambiarra!!!
        f = f.replace(":\"[", ":[");
        f = f.replace("]\"}", "]}");
        //Toast.makeText(getApplication(),""+f,Toast.LENGTH_SHORT).show();
        Log.i("JSON_LOGIN: ", f);
        return f;
    }

    public static String geraJsonTeste(String n1) {
        ArrayList<JSONObject> tabela = new ArrayList<JSONObject>();
        JSONObject registro = new JSONObject();
        //cria um registro primeiro
        try {
            registro.put("enviou", n1+"");
        } catch (JSONException k) {
            Log.d("IFMG", "" + k.getMessage());
        }
        tabela.add(registro);        //adiciona registro à lista de registros

        //adiciona tabela
        JSONObject bd = new JSONObject();
        try {
            bd.putOpt("enviando", (Object) tabela);
        } catch (JSONException u) {
        }

        String f = bd.toString();
        f = f.replace("\\", "");//gambiarra!!!
        f = f.replace(":\"[", ":[");
        f = f.replace("]\"}", "]}");
        //Toast.makeText(getApplication(),""+f,Toast.LENGTH_SHORT).show();
        Log.i("[IFMG]", "JSON ca: " + f);
        return f;
    }

    public static String geraJsonTest2(int n1, int n2, ArrayList<String> dadosTeste) {
        ArrayList<JSONObject> numeros = new ArrayList<JSONObject>();
        JSONObject registro = new JSONObject();
        //cria um registro primeiro
        try {
            registro.put("n1", n1);
            registro.put("n2", n2);
        } catch (JSONException k) {
            Log.d("IFMG", "" + k.getMessage());
        }
        numeros.add(registro);        //adiciona registro à lista de registros
        //----------------------------------------------------------------------------------------
        //Adicionando os últimos dtcs encontrados e introduzidos no JSON de envio:
        ArrayList<JSONObject> tabelaTesteDados = new ArrayList<JSONObject>();
        JSONObject registroDadosTeste;
        //cria um registro primeiro
        for (String x : dadosTeste) {
            registroDadosTeste = new JSONObject();
            try {
                registroDadosTeste.put("dadosTeste", x);
            } catch (JSONException k) {
                Log.d("IFMG", "" + k.getMessage());
            }
            //adiciona registro à lista de registros
            tabelaTesteDados.add(registroDadosTeste);
        }

        //adiciona tabelas
        JSONObject bd = new JSONObject();
        try {
            bd.putOpt("numeros", (Object) numeros);
            bd.putOpt("testeDados", (Object) tabelaTesteDados);

        } catch (JSONException u) {
            u.printStackTrace();
        }

        String f = bd.toString();
        f = f.replace("\\", "");//gambiarra!!!
        f = f.replace(":\"[", ":[");
        f = f.replace("]\"}", "]}");
        f = f.replace("]\"", "]");
        f = f.replace(", {", ",{");
        //Toast.makeText(getApplication(),""+f,Toast.LENGTH_SHORT).show();
        Log.i("JSON_PREVISAO: ", f);
        return f;
    }



    public static String geraJsonLogin(String codigo, String name, String email) {
        ArrayList<JSONObject> dadosLogin = new ArrayList<JSONObject>();
        JSONObject registro = new JSONObject();
        //cria um registro primeiro
        try {
            registro.put("codigo", codigo);
            registro.put("name", name);
            registro.put("email", email);
        } catch (JSONException k) {
            Log.d("IFMG", "" + k.getMessage());
        }
        dadosLogin.add(registro);        //adiciona registro à lista de registros
        //----------------------------------------------------------------------------------------
        //Adicionando os últimos dtcs encontrados e introduzidos no JSON de envio:
        /*ArrayList<JSONObject> tabelaTesteDados = new ArrayList<JSONObject>();
        JSONObject registroDadosTeste;
        //cria um registro primeiro
        for (String x : dadosTeste) {
            registroDadosTeste = new JSONObject();
            try {
                // isere na coluna dadosTeste,
                // poderia colocar mais campos no JSON para enviar uma tabela
                registroDadosTeste.put("dadosTeste", x);
            } catch (JSONException k) {
                Log.d("IFMG", "" + k.getMessage());
            }
            //adiciona registro à lista de registros
            tabelaTesteDados.add(registroDadosTeste);
        }*/

        //adiciona tabelas
        JSONObject bd = new JSONObject();
        try {
            bd.putOpt("login", (Object) dadosLogin);
            //bd.putOpt("lista_dados", (Object) tabelaTesteDados);

        } catch (JSONException u) {
            u.printStackTrace();
        }

        String f = bd.toString();
        f = f.replace("\\", "");//gambiarra!!!
        f = f.replace(":\"[", ":[");
        f = f.replace("]\"}", "]}");
        f = f.replace("]\"", "]");
        f = f.replace(", {", ",{");
        Log.i("JSON_ENVIADO: ", f);
        return f;
    }




}
