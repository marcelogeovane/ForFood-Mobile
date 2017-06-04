package com.example.fernando.teste_sqlite1.servicos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.fernando.teste_sqlite1.beans.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoDB extends SQLiteOpenHelper {
    private static final String TAG = "sql";

    // Nome do banco
    private static final String NOME_BANCO = "forfood";
    private static final String TABELA1 = "cliente";
    private static final String TABELA2 = "prato";
    private static final String TABELA3 = "pedido";
    private static final int VERSAO_BANCO = 1;

    public ContatoDB(Context context) {
        // context, nome do banco, factory, versão
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a Tabela "+TABELA1+"...");
        db.execSQL("create table if not exists " + TABELA2 + " (" +
                "cliCpf integer primary key autoincrement," +
                "cliNome text, " +
                "cliTelefone integer," +
                "cliEndereco text,"+
                "cliEmail text,"+
                "cliSenha text"+
                ");");
        Log.d(TAG, "Tabela "+TABELA3+" criada com sucesso.");
        Log.d(TAG, "Criando a Tabela "+TABELA2+"...");
        db.execSQL("create table if not exists " + TABELA2 + " (" +
                "praCodigo integer primary key autoincrement," +
                "praNome text, " +
                "praPreco integer," +
                "praDescricao,"+
                "praTempo"+
                ");");
        Log.d(TAG, "Tabela "+TABELA3+" criada com sucesso.");
        Log.d(TAG, "Criando a Tabela "+TABELA3+"...");
        db.execSQL("create table if not exists " + TABELA3 + " (" +
                "pedCodigo integer primary key autoincrement," +
                "ped_praCodigo integer," +
                "ped_cliCodigo integer" +
                ");");
        Log.d(TAG, "Tabela "+TABELA3+" criada com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Caso mude a versão do banco de dados, podemos executar um SQL aqui
        if (oldVersion == 1 && newVersion == 2) {
            // Execute o script para atualizar a versão...
        }
    }

    // Insere um novo CONTATO, ou atualiza se já existe.
    public long save(Contato c) {
        long id = c.getCodigo();
        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("conNome", c.getNome());
            values.put("conTelefone", c.getTelefone());

            if (id != 0) {//SE O ID É DIFERENTE DE 0 ATUALIZA,

                String _id = String.valueOf(c.getCodigo());
                String[] whereArgs = new String[]{_id};

                // update contato set values = ... where _id=?
                int count = db.update(TABELA, values, "conCodigo=?", whereArgs);

                return count;
            } else { // SE O ID FOR 0, SIGNIFICA QUE NÃO TEM ID, ASSIM VAI INSERIR O DADO
                // insert into contato values (...)
                id = db.insert(TABELA, "", values);
                return id;
            }
        } finally {
            db.close();
        }
    }

    // Deleta o CONTATO
    public int delete(Contato c) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from contato where _id=?
            int count = db.delete(TABELA, "conCodigo=?", new String[]{String.valueOf(c.getCodigo())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os contatos
    public List<Contato> findAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from contato
            Cursor c = db.query(TABELA, null, null, null, null, null, null, null);

            return toList(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public List<Contato> findBySql(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql,null);
            List<Contato> contatos = new ArrayList<Contato>();

            if (c.moveToFirst()) {
                do {
                    Contato contato = new Contato();
                    contatos.add(contato);

                    // recupera os atributos de contato
                    contato.setCodigo(c.getLong(c.getColumnIndex("conCodigo")));
                    contato.setNome(c.getString(c.getColumnIndex("conNome")));
                    contato.setTelefone(c.getString(c.getColumnIndex("conTelefone")));
                } while (c.moveToNext());
            }
            return contatos;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Contato> toList(Cursor c) {
        List<Contato> contatos = new ArrayList<Contato>();

        if (c.moveToFirst()) {
            do {
                Contato contato = new Contato();
                contatos.add(contato);

                // recupera os atributos de contatos
                contato.setCodigo(c.getLong(c.getColumnIndex("conCodigo")));
                contato.setNome(c.getString(c.getColumnIndex("conNome")));
                contato.setTelefone(c.getString(c.getColumnIndex("conTelefone")));
            } while (c.moveToNext());
        }
        return contatos;
    }

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