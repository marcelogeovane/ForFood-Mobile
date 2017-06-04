package com.example.fernando.teste_sqlite1.servicos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.fernando.teste_sqlite1.beans.Cliente;
import com.example.fernando.teste_sqlite1.beans.Prato;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final String TAG = "sql";

    // Nome do banco
    private static final String NOME_BANCO = "forfood";
    private static final String TABELA1 = "cliente";
    private static final String TABELA2 = "prato";
    private static final String TABELA3 = "pedido";
    private static final String TABELA4 = "PEDIDO_PRATO";
    private static final int VERSAO_BANCO = 1;

    public DataBase(Context context) {
        // context, nome do banco, factory, versão
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a Tabela " + TABELA1 + "...");
        db.execSQL("create table if not exists " + TABELA2 + " (" +
                "cliCodigo integer primary key autoincrement," +
                "cliCpf integer," +
                "cliNome text," +
                "cliTelefone integer," +
                "cliEndereco text," +
                "cliEmail text," +
                "cliSenha text" +
                ");");
        Log.d(TAG, "Tabela " + TABELA3 + " criada com sucesso.");
        Log.d(TAG, "Criando a Tabela " + TABELA2 + "...");
        db.execSQL("create table if not exists " + TABELA2 + " (" +
                "praCodigo integer primary key autoincrement," +
                "praNome text, " +
                "praPreco integer," +
                "praDescricao," +
                "praTempo" +
                ");");
        Log.d(TAG, "Tabela " + TABELA3 + " criada com sucesso.");
        Log.d(TAG, "Criando a Tabela " + TABELA3 + "...");
        db.execSQL("create table if not exists " + TABELA3 + " (" +
                "pedCodigo integer primary key autoincrement," +
                "cliente_cliCodigo integer" +
                ");");
        Log.d(TAG, "Tabela " + TABELA3 + " criada com sucesso.");
        Log.d(TAG, "Criando a Tabela " + TABELA4 + "...");
        db.execSQL("create table if not exists " + TABELA4 + " (" +
                "pedido_pedCodigo integer," +
                "prato_praCodigo integer" +
                ");");
        Log.d(TAG, "Tabela " + TABELA4 + " criada com sucesso.");
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
    public long saveCliente(Cliente c) {
        long id = c.getCodigo();
        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("cliNome", c.getNome());
            values.put("cliCpf", c.getCpf());
            values.put("cliTelefone", c.getTelefone());
            values.put("cliEndereco", c.getEndereco());
            values.put("cliEmail", c.getEmail());
            values.put("cliSenha", c.getSenha());
            if (id != 0) {//SE O ID É DIFERENTE DE 0 ATUALIZA,

                String _id = String.valueOf(c.getCodigo());
                String[] whereArgs = new String[]{_id};

                // update contato set values = ... where _id=?
                int count = db.update(TABELA1, values, "cliCodigo=?", whereArgs);

                return count;
            } else { // SE O ID FOR 0, SIGNIFICA QUE NÃO TEM ID, ASSIM VAI INSERIR O DADO
                // insert into contato values (...)
                id = db.insert(TABELA1, "", values);
                return id;
            }
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
    public List<Cliente> findAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from cliente
            Cursor c = db.query(TABELA1, null, null, null, null, null, null, null);

            return toList(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public List<Cliente> findBySql(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql, null);
            List<Cliente> clientes = new ArrayList<Cliente>();

            if (c.moveToFirst()) {
                do {
                    Cliente cliente = new Cliente();
                    clientes.add(cliente);

                    // recupera os atributos de contatos
                    cliente.setCodigo(c.getLong(c.getColumnIndex("cliCodigo")));
                    cliente.setCpf(c.getLong(c.getColumnIndex("cliCpf")));
                    cliente.setNome(c.getString(c.getColumnIndex("cliNome")));
                    cliente.setTelefone(c.getLong(c.getColumnIndex("cliTelefone")));
                    cliente.setEndereco(c.getString(c.getColumnIndex("cliEndereco")));
                    cliente.setEmail(c.getString(c.getColumnIndex("cliEmail")));
                    cliente.setSenha(c.getString(c.getColumnIndex("cliSenha")));
                } while (c.moveToNext());
            }
            return clientes;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Cliente> toList(Cursor c) {
        List<Cliente> clientes = new ArrayList<Cliente>();

        if (c.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                clientes.add(cliente);

                // recupera os atributos de contatos
                cliente.setCodigo(c.getLong(c.getColumnIndex("cliCodigo")));
                cliente.setCpf(c.getLong(c.getColumnIndex("cliCpf")));
                cliente.setNome(c.getString(c.getColumnIndex("cliNome")));
                cliente.setTelefone(c.getLong(c.getColumnIndex("cliTelefone")));
                cliente.setEndereco(c.getString(c.getColumnIndex("cliEndereco")));
                cliente.setEmail(c.getString(c.getColumnIndex("cliEmail")));
                cliente.setSenha(c.getString(c.getColumnIndex("cliSenha")));
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
            values.put("praNome", p.getNome());
            values.put("praDescricao", p.getDescricao());
            values.put("praPreco", p.getPreco());
            values.put("praTempo", p.getTempo());

            if (id != 0) {//SE O ID É DIFERENTE DE 0 ATUALIZA,

                String _id = String.valueOf(p.getCodigo());
                String[] whereArgs = new String[]{_id};

                // update contato set values = ... where _id=?
                int count = db.update(TABELA2, values, "cliCodigo=?", whereArgs);

                return count;
            } else { // SE O ID FOR 0, SIGNIFICA QUE NÃO TEM ID, ASSIM VAI INSERIR O DADO
                // insert into contato values (...)
                id = db.insert(TABELA2, "", values);
                return id;
            }
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
    public List<Prato> Prato_findAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from cliente
            Cursor c = db.query(TABELA1, null, null, null, null, null, null, null);

            return toList(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public List<Cliente> Prato_findBySql(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql, null);
            List<Cliente> clientes = new ArrayList<Cliente>();

            if (c.moveToFirst()) {
                do {
                    Cliente cliente = new Cliente();
                    clientes.add(cliente);

                    // recupera os atributos de contatos
                    cliente.setCodigo(c.getLong(c.getColumnIndex("conCodigo")));
                    cliente.setNome(c.getString(c.getColumnIndex("conNome")));
                } while (c.moveToNext());
            }
            return clientes;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Prato> Prato_toList(Cursor c) {
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
