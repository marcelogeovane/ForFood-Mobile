package com.example.marcelo.forfood.view.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marcelo.forfood.R;
import com.example.marcelo.forfood.adapters.AdapterListViewPedido;
import com.example.marcelo.forfood.adapters.AdapterListViewPrato;
import com.example.marcelo.forfood.beans.Pedido;
import com.example.marcelo.forfood.beans.Prato;
import com.example.marcelo.forfood.servicos.DataBase;
import com.example.marcelo.forfood.sinc.JSONDados;
import com.example.marcelo.forfood.sinc.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaPedidosFragment extends Fragment {

    private ListView listView;
    List<Pedido> lista;
    private int codigo = -1;
    private String nome;
    DataBase db ;
    private String n1;

    public ListaPedidosFragment() {
        // Required empty public constructor
    }

    private AdapterListViewPedido salv;   //adaptador, declarado global para aparecer no método
    // de atualização do listView

    private int cod;                        //valor inteiro declarad global para ser atribuido
    // quando o usuário clicar em algum item do listview e
    // será usado no Alerta para deletar elementos da lista

    private String s;                       //valor string declarad global para ser atribuido
    // quando o usuário clicar em algum item do listview e
    // será usado no Alerta para deletar elementos da lista

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //extraio o objeto view para trabalhar com demais componentes no fragment
        View view = inflater.inflate(R.layout.fragment_opcao2, container, false);

        listView = (ListView) view.findViewById(R.id.listViewPedido);

        //evento de click na listagem de pratos
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialogSelList(position);
            }
        });

        db = new DataBase(getContext());
        n1 = "1";


        return view;
    }




    public void onResume() {
        super.onResume();
        lista = db.pedido_findAll();
        Log.d("[IFMG]","ON RESUME "+ db.pedido_findAll());
        if(lista.isEmpty()) {
            Log.d("[IFMG]", "ENTROU NO IF DO VIEW CREATE!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            requisitaPost(
                    JSONDados.geraJsonTeste(
                            n1),
                    "https://jsonforfood1.000webhostapp.com/json1.php"
            );
        }
        criaLista();
    }

    private void criaLista() {
        //criando o adapter customizado
        salv = new AdapterListViewPedido(getContext());
        salv.setLista(lista);
        //setando o adapter customizado ao list
        listView.setAdapter(salv);
    }

    private AlertDialog alerta;

    private void AlertDialogSelList(final int position) {

        //final BdVeiculoUnidade bdVeiculoUnidade = new BdVeiculoUnidade(getContext());

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //define o titulo
        builder.setTitle("Comprar");
        //define a mensagem
        builder.setMessage("Deseja bla bla bla?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //bdVeiculoUnidade.deleteVeiculoUnidade(veiculoUnidadeList.get(position).getVeiPlaca());
                Toast.makeText(listView.getContext(), "Indice: : " + position, Toast.LENGTH_SHORT).show();
                // Recriar a lista atualizando-a
                //criaLista();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                        }
                    });

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Falha na conexão!!!", Toast.LENGTH_LONG).show();
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
                        Pedido p = new Pedido();
                        p.setCodigo(Long.parseLong(linha.getString("pedCodigo")));
                        p.setStatus(linha.getString("pedStatus"));
                        p.setCliente_codigo(Long.parseLong(linha.getString("Cliente_cliCodigo")));
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

    public static ProgressDialog gerarDialogIndeterminado(String mensagem, Context activityContexto) {
        ProgressDialog pDialog = new ProgressDialog(activityContexto);
        pDialog.setMessage(mensagem);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        //pDialog.show();
        return pDialog;
    }

}

