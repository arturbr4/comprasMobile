package br.com.mercados.compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.List;

import br.com.mercados.compras.model.Produto;
import br.com.mercados.compras.util.Utils;

public class ProdutoDAO extends SQLiteOpenHelper{

    public static final Integer VERSION_DATABASE = 1;

    public ProdutoDAO(Context context) {
        super(context, "produtos", null, VERSION_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE PRODUTOS(ID INTEGER PRIMARY KEY, NOME TEXT NOT NULL, CATEGORIA TEXT, QUANTIDADE INTEGER, UNIDADE TEXT, PRECO REAL, LOCAL TEXT, DATA DATE, OBSERVACAO TEXT)"; //CRIAR AS TABELAS NECESSARIAS PARA O PROJETO
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS PRODUTOS";
        db.execSQL(sql);
        onCreate(db);
    }

    public void inserirProduto(Produto produto) {
        SQLiteDatabase db =  getWritableDatabase();

        ContentValues contentProdutos = new ContentValues();
        contentProdutos.put("ID",produto.getId());
        contentProdutos.put("NOME",produto.getNome());
        contentProdutos.put("CATEGORIA",produto.getCategoria());
        contentProdutos.put("QUANTIDADE",produto.getQuantidade());
        contentProdutos.put("UNIDADE",produto.getUnidade());
        contentProdutos.put("PRECO",produto.getPreco());
        contentProdutos.put("LOCAL",produto.getLocal());
        contentProdutos.put("DATA",produto.getData().getTimeInMillis());
        contentProdutos.put("OBSERVACAO",produto.getObservacao());

        db.insert("PRODUTOS",null,contentProdutos);
    }

    public List<Produto> buscarProdutos() {
        List<Produto> listProduto = new ArrayList<>();
        String sql = "SELECT * FROM PRODUTOS";
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(sql,null);

        while(c.moveToNext()){
            Produto produto = new Produto();
            produto.setId(c.getLong(c.getColumnIndex("ID")));
            produto.setNome(c.getString(c.getColumnIndex("NOME")));
            produto.setCategoria(c.getString(c.getColumnIndex("CATEGORIA")));
            produto.setQuantidade(c.getLong(c.getColumnIndex("QUANTIDADE")));
            produto.setUnidade(c.getString(c.getColumnIndex("UNIDADE")));
            produto.setPreco(c.getDouble(c.getColumnIndex("PRECO")));
            produto.setLocal(c.getString(c.getColumnIndex("LOCAL")));
            produto.setData(Utils.convertTimeMilisToCalendar(c.getLong(c.getColumnIndex("DATA"))));
            produto.setObservacao(c.getString(c.getColumnIndex("OBSERVACAO")));

            listProduto.add(produto);
        }
        c.close();

        return listProduto;
    }
}