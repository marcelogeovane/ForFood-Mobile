package com.example.marcelo.forfood.servicos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.marcelo.forfood.beans.Cliente;
import com.example.marcelo.forfood.beans.ListaPedido;
import com.example.marcelo.forfood.beans.Pedido;
import com.example.marcelo.forfood.beans.Pedido_Prato;
import com.example.marcelo.forfood.beans.Prato;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final String TAG = "sql";

    // Nome do banco
    private static final String NOME_BANCO = "forfood";
    private static final String TABELA1 = "cliente";
    private static final String TABELA2 = "prato";
    private static final String TABELA3 = "pedido";
    private static final String TABELA4 = "pedido_prato";
    private static final String TABELA5 = "lista_pedido";
    private static final String TABELA6 = "pedido_tempo";
    private static final int VERSAO_BANCO = 1;

    public DataBase(Context context) {
        // context, nome do banco, factory, versão
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a Tabela " + TABELA1 + "...");
        db.execSQL("create table if not exists " + TABELA1 + " (" +
                "cliCodigo double primary key," +
                "cliCpf integer," +
                "cliNome text," +
                "cliTelefone integer," +
                "cliEmail text" +
                ");");
        Log.d(TAG, "Tabela " + TABELA1 + " criada com sucesso.");
        //////////////////////////////////////////////////////////////
        Log.d(TAG, "Criando a Tabela " + TABELA2 + "...");
        db.execSQL("create table if not exists " + TABELA2 + " (" +
                "praCodigo integer primary key," +
                "praNome text, " +
                "praPreco integer," +
                "praDescricao text," +
                "praTempo integer" +
                ");");
        Log.d(TAG, "Tabela " + TABELA2 + " criada com sucesso.");
        /////////////////////////////////////////////////////////////////////////////
        Log.d(TAG, "Criando a Tabela " + TABELA3 + "...");
        db.execSQL("create table if not exists " + TABELA3 + " (" +
                "pedCodigo integer primary key autoincrement," +
                "pedValor double," +
                "pedData text,"+
                "pedEndereco text,"+
                "cliente_cliCodigo integer" +
                ");");
        Log.d(TAG, "Tabela " + TABELA3 + " criada com sucesso.");
        ////////////////////////////////////////////////////////////////////////
        Log.d(TAG, "Criando a Tabela " + TABELA4 + "...");
        db.execSQL("create table if not exists " + TABELA4 + " (" +
                "pedido_pedCodigo integer," +
                "prato_praCodigo integer" +
                ");");
        Log.d(TAG, "Tabela " + TABELA4 + " criada com sucesso.");
        ////////////////////////////////////////////////////////////////////////
        Log.d(TAG, "Criando a Tabela " + TABELA5 + "...");
        db.execSQL("create table if not exists " + TABELA5 + " (" +
                "lisCodigo integer primary key autoincrement," +
                "lisPraCodigo integer" +
                ");");
        Log.d(TAG, "Tabela " + TABELA5 + " criada com sucesso.");
        /////////////////////////////////////////////////////////////////////////////
        Log.d(TAG, "Criando a Tabela " + TABELA6 + "...");
        db.execSQL("create table if not exists " + TABELA6 + " (" +
                "pedCodigo integer primary key autoincrement," +
                "pedValor double," +
                "pedData text,"+
                "pedEndereco text,"+
                "cliente_cliCodigo integer" +
                ");");
        Log.d(TAG, "Tabela " + TABELA6 + " criada com sucesso.");
        ////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Caso mude a versão do banco de dados, podemos executar um SQL aqui
        if (oldVersion == 1 && newVersion == 2) {
            // Execute o script para atualizar a versão...
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Insere um novo CONTATO, ou atualiza se já existe.
    public double saveCliente(Cliente c) {
        double id = c.getCodigo();
        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("cliCodigo", c.getCodigo());
            values.put("cliNome", c.getNome());
            values.put("cliCpf", c.getCpf());
            values.put("cliTelefone", c.getTelefone());
            values.put("cliEmail", c.getEmail());
            // SE O ID FOR 0, SIGNIFICA QUE NÃO TEM ID, ASSIM VAI INSERIR O DADO
                // insert into contato values (...)
                id = db.insert(TABELA1, "", values);
                return id;

        } finally {
            db.close();
        }
    }

    // Deleta o CONTATO
    public int deleteCliente(Cliente c) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from contato where _id=?
            int count = db.delete(TABELA1, "cliCodigo=?", new String[]{String.valueOf(c.getCodigo())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os contatos
    public List<Cliente> findAllCliente() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from cliente
            Cursor c = db.query(TABELA1, null, null, null, null, null, null, null);

            return toListCliente(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public List<Cliente> findBySqlCliente(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql, null);
            List<Cliente> clientes = new ArrayList<Cliente>();

            if (c.moveToFirst()) {
                do {
                    Cliente cliente = new Cliente();
                    clientes.add(cliente);

                    // recupera os atributos de contatos
                    cliente.setCodigo(c.getDouble(c.getColumnIndex("cliCodigo")));
                    cliente.setCpf(c.getLong(c.getColumnIndex("cliCpf")));
                    cliente.setNome(c.getString(c.getColumnIndex("cliNome")));
                    cliente.setTelefone(c.getLong(c.getColumnIndex("cliTelefone")));
                    cliente.setEmail(c.getString(c.getColumnIndex("cliEmail")));
                } while (c.moveToNext());
            }
            return clientes;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Cliente> toListCliente(Cursor c) {
        List<Cliente> clientes = new ArrayList<Cliente>();

        if (c.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                clientes.add(cliente);

                // recupera os atributos de contatos
                cliente.setCodigo(c.getDouble(c.getColumnIndex("cliCodigo")));
                cliente.setCpf(c.getLong(c.getColumnIndex("cliCpf")));
                cliente.setNome(c.getString(c.getColumnIndex("cliNome")));
                cliente.setTelefone(c.getLong(c.getColumnIndex("cliTelefone")));
                cliente.setEmail(c.getString(c.getColumnIndex("cliEmail")));
            } while (c.moveToNext());
        }
        return clientes;
    }


    //---------------------------------------------------------------------------------------------------------------------

    public long savePrato(Prato p) {
        long id = p.getCodigo();
        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("praCodigo", p.getCodigo());
            values.put("praNome", p.getNome());
            values.put("praDescricao", p.getDescricao());
            values.put("praPreco", p.getPreco());
            values.put("praTempo", p.getTempo());

            // SE O ID FOR 0, SIGNIFICA QUE NÃO TEM ID, ASSIM VAI INSERIR O DADO
                // insert into contato values (...)
                id = db.insert(TABELA2, "", values);
                return id;

        } finally {
            db.close();
        }
    }

    // Deleta o CONTATO
    public int deletePrato(Prato p) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from contato where _id=?
            int count = db.delete(TABELA2, "praCodigo=?", new String[]{String.valueOf(p.getCodigo())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os contatos
    public List<Prato> prato_findAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from cliente
            Cursor c = db.query(TABELA2, null, null, null, null, null, null, null);

            return prato_toList(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public List<Prato> prato_findBySql(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql, null);
            List<Prato> clientes = new ArrayList<Prato>();

            if (c.moveToFirst()) {
                do {
                    Prato prato = new Prato();
                    clientes.add(prato);

                    // recupera os atributos de contatos
                    prato.setCodigo(c.getLong(c.getColumnIndex("praCodigo")));
                    prato.setNome(c.getString(c.getColumnIndex("praNome")));
                    prato.setDescricao(c.getString(c.getColumnIndex("praDescricao")));
                    prato.setPreco(c.getDouble(c.getColumnIndex("praPreco")));
                    prato.setTempo(c.getLong(c.getColumnIndex("praTempo")));
                } while (c.moveToNext());
            }
            return clientes;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Prato> prato_toList(Cursor c) {
        List<Prato> pratos = new ArrayList<Prato>();

        if (c.moveToFirst()) {
            do {
                Prato prato = new Prato();
                pratos.add(prato);

                // recupera os atributos de contatos
                prato.setCodigo(c.getLong(c.getColumnIndex("praCodigo")));
                prato.setNome(c.getString(c.getColumnIndex("praNome")));
                prato.setDescricao(c.getString(c.getColumnIndex("praDescricao")));
                prato.setPreco(c.getDouble(c.getColumnIndex("praPreco")));
                prato.setTempo(c.getLong(c.getColumnIndex("praTempo")));
            } while (c.moveToNext());
        }
        return pratos;
    }


//---------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Insere um novo CONTATO, ou atualiza se já existe.
    public long savePedido(Pedido p) {
        long id = p.getCodigo();
        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("pedValor", p.getValorTotal());
            values.put("pedData",p.getData());
            values.put("pedEndereco", p.getEndereço());
            values.put("cliente_cliCodigo", p.getCliente_codigo());
            Log.d("[IFMG]","DATA BASE PEDIDO: "+ p.toString());

                id = db.insert(TABELA3, "", values);
                return id;

        } finally {
            db.close();
        }
    }

    // Deleta o CONTATO
    public int deletePedido(Pedido p) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from contato where _id=?
            int count = db.delete(TABELA3, "pedCodigo=?", new String[]{String.valueOf(p.getCodigo())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os contatos
    public List<Pedido> pedido_findAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from cliente
            Cursor c = db.query(TABELA3, null, null, null, null, null, null, null);

            return pedido_toList(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public List<Pedido> pedido_findBySql(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql, null);
            List<Pedido> pedidos = new ArrayList<Pedido>();

            if (c.moveToFirst()) {
                do {
                    Pedido pedido = new Pedido();
                    pedidos.add(pedido);

                    // recupera os atributos de contatos
                    pedido.setCodigo(c.getLong(c.getColumnIndex("pedCodigo")));
                    pedido.setCliente_codigo(c.getLong(c.getColumnIndex("cliente_cliCodigo")));
                    pedido.setValorTotal(c.getDouble(c.getColumnIndex("pedValor")));
                    pedido.setEndereço(c.getString(c.getColumnIndex("pedEndereco")));
                    pedido.setData(c.getString(c.getColumnIndex("pedData")));
                } while (c.moveToNext());
            }
            return pedidos;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Pedido> pedido_toList(Cursor c) {
        List<Pedido> pedidos = new ArrayList<Pedido>();

        if (c.moveToFirst()) {
            do {
                Pedido pedido = new Pedido();
                pedidos.add(pedido);

                // recupera os atributos de contatos
                pedido.setCodigo(c.getLong(c.getColumnIndex("pedCodigo")));
                pedido.setCliente_codigo(c.getLong(c.getColumnIndex("cliente_cliCodigo")));
                pedido.setValorTotal(c.getDouble(c.getColumnIndex("pedValor")));
                pedido.setEndereço(c.getString(c.getColumnIndex("pedEndereco")));
                pedido.setData(c.getString(c.getColumnIndex("pedData")));

            } while (c.moveToNext());
        }
        return pedidos;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Insere um novo CONTATO, ou atualiza se já existe.
    public long savePedidoPrato(Pedido_Prato p) {
        long id = p.getPedidoCodigo();
        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("prato_praCodigo", p.getPratoCodigo());
            values.put("pedido_pedCodigo", p.getPedidoCodigo());

            if (id != 0) {//SE O ID É DIFERENTE DE 0 ATUALIZA,

                String _id = String.valueOf(p.getPedidoCodigo());
                String[] whereArgs = new String[]{_id};

                // update contato set values = ... where _id=?
                int count = db.update(TABELA4, values, "pedCodigo=?", whereArgs);

                return count;
            } else { // SE O ID FOR 0, SIGNIFICA QUE NÃO TEM ID, ASSIM VAI INSERIR O DADO
                // insert into contato values (...)
                id = db.insert(TABELA4, "", values);
                return id;
            }
        } finally {
            db.close();
        }
    }

    // Deleta o CONTATO
    public int deletePedidoPrato(Pedido p) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from contato where _id=?
            int count = db.delete(TABELA4, "pedCodigo=?", new String[]{String.valueOf(p.getCodigo())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os contatos
    public List<Pedido_Prato> pedidoPrato_findAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from cliente
            Cursor c = db.query(TABELA4, null, null, null, null, null, null, null);

            return pedidoPrato_toList(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public List<Pedido_Prato> pedidoPrato_findBySql(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql, null);
            List<Pedido_Prato> pedidosPratos = new ArrayList<Pedido_Prato>();

            if (c.moveToFirst()) {
                do {
                    Pedido_Prato pedido_prato = new Pedido_Prato();
                    pedidosPratos.add(pedido_prato);

                    // recupera os atributos de contatos
                    pedido_prato.setPedidoCodigo(c.getLong(c.getColumnIndex("pedido_pedCodigo")));
                    pedido_prato.setPratoCodigo(c.getLong(c.getColumnIndex("prato_praCodigo")));

                } while (c.moveToNext());
            }
            return pedidosPratos;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Pedido_Prato> pedidoPrato_toList(Cursor c) {
        List<Pedido_Prato> pedidos = new ArrayList<Pedido_Prato>();

        if (c.moveToFirst()) {
            do {
                Pedido_Prato pedido_prato = new Pedido_Prato();
                pedidos.add(pedido_prato);

                // recupera os atributos de contatos
                pedido_prato.setPedidoCodigo(c.getLong(c.getColumnIndex("pedido_pedCodigo")));
                pedido_prato.setPratoCodigo(c.getLong(c.getColumnIndex("prato_praCodigo")));


            } while (c.moveToNext());
        }
        return pedidos;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Insere um novo CONTATO, ou atualiza se já existe.
    public long saveListaPedido(ListaPedido p) {
        long id = p.getCodigo();
        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("lisPraCodigo", p.getCodigoPrato());

            if (id != 0) {//SE O ID É DIFERENTE DE 0 ATUALIZA,

                String _id = String.valueOf(p.getCodigo());
                String[] whereArgs = new String[]{_id};

                // update contato set values = ... where _id=?
                int count = db.update(TABELA5, values, "lisCodigo=?", whereArgs);

                return count;
            } else { // SE O ID FOR 0, SIGNIFICA QUE NÃO TEM ID, ASSIM VAI INSERIR O DADO
                // insert into contato values (...)
                id = db.insert(TABELA5, "", values);
                return id;
            }
        } finally {
            db.close();
        }
    }

    // Deleta o CONTATO
    public int deleteListPedido(ListaPedido p) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from contato where _id=?
            int count = db.delete(TABELA5, "lisCodigo=?", new String[]{String.valueOf(p.getCodigo())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os contatos
    public List<ListaPedido> ListaPedido_findAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from cliente
            Cursor c = db.query(TABELA5, null, null, null, null, null, null, null);

            return ListaPedido_toList(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public List<ListaPedido> ListaPedido_findBySql(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql, null);
            List<ListaPedido> pedidosPratos = new ArrayList<ListaPedido>();

            if (c.moveToFirst()) {
                do {
                    ListaPedido pedido_prato = new ListaPedido();
                    pedidosPratos.add(pedido_prato);

                    // recupera os atributos de contatos
                    pedido_prato.setCodigo(c.getLong(c.getColumnIndex("lisCodigo")));
                    pedido_prato.setCodigoPrato(c.getLong(c.getColumnIndex("lisPraCodigo")));

                } while (c.moveToNext());
            }
            return pedidosPratos;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<ListaPedido> ListaPedido_toList(Cursor c) {
        List<ListaPedido> pedidos = new ArrayList<ListaPedido>();

        if (c.moveToFirst()) {
            do {
                ListaPedido pedido_prato = new ListaPedido();
                pedidos.add(pedido_prato);

                // recupera os atributos de contatos
                pedido_prato.setCodigo(c.getLong(c.getColumnIndex("lisCodigo")));
                pedido_prato.setCodigoPrato(c.getLong(c.getColumnIndex("lisPraCodigo")));


            } while (c.moveToNext());
        }
        return pedidos;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------




    //---------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Insere um novo CONTATO, ou atualiza se já existe.
    public long savePedidoTemp(Pedido p) {
        long id = p.getCodigo();
        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("pedValor", p.getValorTotal());
            values.put("pedData",p.getData());
            values.put("pedEndereco", p.getEndereço());
            values.put("cliente_cliCodigo", p.getCliente_codigo());
            Log.d("[IFMG]","DATA BASE PEDIDO: "+ p.toString());

            id = db.insert(TABELA6, "", values);
            return id;

        } finally {
            db.close();
        }
    }

    // Deleta o CONTATO
    public int deletePedidoTemp(Pedido p) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from contato where _id=?
            int count = db.delete(TABELA3, "pedCodigo=?", new String[]{String.valueOf(p.getCodigo())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os contatos
    public List<Pedido> pedidoTemp_findAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from cliente
            Cursor c = db.query(TABELA3, null, null, null, null, null, null, null);

            return pedido_toList(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public List<Pedido> pedidoTemp_findBySql(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql, null);
            List<Pedido> pedidos = new ArrayList<Pedido>();

            if (c.moveToFirst()) {
                do {
                    Pedido pedido = new Pedido();
                    pedidos.add(pedido);

                    // recupera os atributos de contatos
                    pedido.setCodigo(c.getLong(c.getColumnIndex("pedCodigo")));
                    pedido.setCliente_codigo(c.getLong(c.getColumnIndex("cliente_cliCodigo")));
                    pedido.setValorTotal(c.getDouble(c.getColumnIndex("pedValor")));
                    pedido.setEndereço(c.getString(c.getColumnIndex("pedEndereco")));
                    pedido.setData(c.getString(c.getColumnIndex("pedData")));
                } while (c.moveToNext());
            }
            return pedidos;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Pedido> pedidoTemp_toList(Cursor c) {
        List<Pedido> pedidos = new ArrayList<Pedido>();

        if (c.moveToFirst()) {
            do {
                Pedido pedido = new Pedido();
                pedidos.add(pedido);

                // recupera os atributos de contatos
                pedido.setCodigo(c.getLong(c.getColumnIndex("pedCodigo")));
                pedido.setCliente_codigo(c.getLong(c.getColumnIndex("cliente_cliCodigo")));
                pedido.setValorTotal(c.getDouble(c.getColumnIndex("pedValor")));
                pedido.setEndereço(c.getString(c.getColumnIndex("pedEndereco")));
                pedido.setData(c.getString(c.getColumnIndex("pedData")));

            } while (c.moveToNext());
        }
        return pedidos;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------





    // Executa um SQL
    public void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        } finally {
            db.close();
        }
    }

}
