package com.example.marcelo.forfood.view.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marcelo.forfood.R;
import com.example.marcelo.forfood.adapters.AdapterListViewPrato;
import com.example.marcelo.forfood.beans.ListaPedido;
import com.example.marcelo.forfood.beans.Prato;
import com.example.marcelo.forfood.servicos.DataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaPratoFragment extends Fragment {
    private ListView listView;
    private List<Prato> lista;
    private ArrayList<Integer> pratos;
    private int codigo = -1;
    private String nome;
    DataBase db ;
    private String n1;
    ListaPedido listaPedido ;



    public ListaPratoFragment() {
        // Required empty public constructor
    }

    public void setFiltroEstabelecimento(int estCodigo, String estNome) {
        this.codigo = estCodigo;

    }

    private AdapterListViewPrato salv;   //adaptador, declarado global para aparecer no método
    // de atualização do listView

    private int cod;                        //valor inteiro declarad global para ser atribuido
    // quando o usuário clicar em algum item do listview e
    // será usado no Alerta para deletar elementos da lista

    private String s;                       //valor string declarad global para ser atribuido
    // quando o usuário clicar em algum item do listview e
    // será usado no Alerta para deletar elementos da lista



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //extraio o objeto view para trabalhar com demais componentes no fragment
        View view = inflater.inflate(R.layout.fragment_opcao3, container, false);

        // identifica ListView
        listView = (ListView) view.findViewById(R.id.listViewPratos2);

        //evento de click na listagem de pratos
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialogSelList(position);
            }
        });



        pratos = new ArrayList<Integer>();



        db = new DataBase(getContext());
        lista = db.prato_findAll();

        listaPedido = new ListaPedido();

        return view;
    }



    public void onResume() {
        super.onResume();
        criaLista();
    }

    private void criaLista() {
        //criando o adapter customizado
        salv = new AdapterListViewPrato(getContext());
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
        builder.setMessage("Deseja Adicionar ao Carrinho?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //bdVeiculoUnidade.deleteVeiculoUnidade(veiculoUnidadeList.get(position).getVeiPlaca());
                Toast.makeText(listView.getContext(), "Indice: : " + position, Toast.LENGTH_SHORT).show();
                listaPedido.setCodigoPrato(Long.parseLong(String.valueOf(position)));
                db.saveListaPedido(listaPedido);


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


    public void continuar(){
        FinalizarPedidoFragment fragment = new FinalizarPedidoFragment();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("pratos", pratos);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_fragments, fragment ).commit();
    }



}
