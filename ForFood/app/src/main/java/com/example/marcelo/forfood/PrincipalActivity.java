package com.example.marcelo.forfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marcelo.forfood.adapters.AdapterListViewPrato;
import com.example.marcelo.forfood.beans.Cliente;
import com.example.marcelo.forfood.beans.Pedido;
import com.example.marcelo.forfood.beans.Prato;
import com.example.marcelo.forfood.servicos.DataBase;
import com.example.marcelo.forfood.sinc.JSONDados;
import com.example.marcelo.forfood.sinc.JSONParser;
import com.example.marcelo.forfood.view.fragments.CardapioFragment;
import com.example.marcelo.forfood.view.fragments.ListaPedidosFragment;
import com.example.marcelo.forfood.view.fragments.WelcomeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private int codigo = -1;
    private String nome;
    private DataBase db ;
    private String n1;
    private ProgressDialog dialog;
    private List<Prato> lista;
    private List<Pedido> lista1;
    private String codigo1;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //fixa o layout vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        db = new DataBase(getApplicationContext());
        List<Cliente> arrAY = new ArrayList<Cliente>();
        arrAY = db.findAllCliente();
        if (!arrAY.isEmpty()){
            for (int i = 0; i < arrAY.size() ; i++) {
                codigo1 = String.valueOf(arrAY.get(i).getCodigo());
            }
        }

        dialog = ProgressDialog.show(this, "Login", "Validando seus dados... Aguarde.", false, true);
        lista = db.prato_findAll();
        lista1 = db.pedido_findAll();
        if(lista.isEmpty() && lista1.isEmpty()) {
            Log.d("[IFMG]", "ENTROU NO IF DO VIEW CREATE!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            requisitaPost(
                    JSONDados.geraJsonTeste(
                            codigo1),
                    "https://forfood.000webhostapp.com/json1.php"
            );
        }else {
            //desliga o dialog
            dialog.dismiss();
            //seta o fragment inicial
            replaceFragment(new WelcomeFragment());
        }



    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        replaceFragment(new WelcomeFragment());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //evento do menu  no  canto superiror direito
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_sobre):
                Toast.makeText(getApplicationContext(), "Teste!", Toast.LENGTH_LONG).show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_cardapio:
                CardapioFragment op1= new CardapioFragment();
                replaceFragment(op1);
                //Toast.makeText(getApplicationContext(), "Teste1!", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_pedidos:
                ListaPedidosFragment op2= new ListaPedidosFragment();
                replaceFragment(op2);
                //Toast.makeText(getApplicationContext(), "Teste2!", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_realizarPedido:
                Intent intent = new Intent(this, PedidoActivity.class);
                startActivity(intent);

                break;
            case R.id.nav_sair:
                List<Cliente> cliente = new ArrayList<Cliente>();
                cliente = db.findAllCliente();
                for (int i = 0; i <  cliente.size() ; i++) {
                   db.deleteCliente(cliente.get(i));
                }
                Intent i = new Intent(this, LoginActivity.class);
                i.putExtra("sair","saindo");
                startActivity(i);

                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragments, fragment, "TAG").addToBackStack(null).commit();
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
                    final String resp1 = interpretaJSON_Aritimetica1(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //desliga o dialog
                            dialog.dismiss();
                            //seta o fragment inicial
                            replaceFragment(new WelcomeFragment());
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
                linhas = (JSONArray) json.get("prato");//pega vetor do json recebido
                if (linhas.length() > 0) {//verifica we exite algum registro recebido do servidor
                    for (int i = 0; i < linhas.length(); i++) {
                        JSONObject linha = (JSONObject) linhas.get(i);
                        Prato p = new Prato();
                        p.setCodigo(Long.parseLong(linha.getString("praCodigo")));
                        p.setNome(linha.getString("praNome"));
                        p.setDescricao(linha.getString("praDescricao"));
                        p.setPreco(Double.parseDouble(linha.getString("praPreco")));
                        p.setTempo(Long.parseLong(linha.getString("praTempo")));
                        Log.d("[IFMG]", "resultado: " + p.toString());
                        db.savePrato(p);
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

    public String interpretaJSON_Aritimetica1(JSONObject json) {
        String texto = "";
        try {
            JSONArray linhas = null;
            //Printando na string os elementos identificados nela
            try {
                linhas = (JSONArray) json.get("pedido");//pega vetor do json recebido
                if (linhas.length() > 0) {//verifica we exite algum registro recebido do servidor
                    for (int i = 0; i < linhas.length(); i++) {
                        JSONObject linha = (JSONObject) linhas.get(i);
                        Pedido p = new Pedido();
                        p.setCodigo(Long.parseLong(linha.getString("pedCodigo")));
                        p.setCliente_codigo(Double.parseDouble(linha.getString("Cliente_cliCodigo")));
                        p.setEndereço(linha.getString("pedEndereco"));
                        p.setValorTotal(Double.parseDouble(linha.getString("pedValor")));
                        Log.d("[IFMG]", "resultadoFRAGMENT: " + p.toString());
                        db.savePedido(p);
                    }
                }else {
                    Log.d("[IFMG]", "JSON VAZIO!!!!!!!!!!!!! ");
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


}
