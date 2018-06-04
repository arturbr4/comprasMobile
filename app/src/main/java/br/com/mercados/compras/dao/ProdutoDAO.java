package br.com.mercados.compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.mercados.app.App;
import br.com.mercados.compras.model.Produto;

public class ProdutoDAO{

    public static String createTable() {
        String sql = "CREATE TABLE " + Produto.TABLE +
                        "( " + Produto.COLUMN_Id + " INTEGER PRIMARY KEY, "+
                        Produto.COLUMN_CodigoBarra + " TEXT NOT NULL UNIQUE, "+
                        Produto.COLUMN_Nome +" NOT NULL, "+
                        Produto.COLUMN_Categoria  +" TEXT, "+
                        Produto.COLUMN_Quantidade + " INTEGER, "+
                        Produto.COLUMN_Unidade + " TEXT, "+
                        Produto.COLUMN_Foto + " TEXT, "+
                        Produto.COLUMN_Observacao +" OBSERVACAO TEXT)";
        return sql;
    }

    public void inserirProduto(Produto produto) {
        SQLiteDatabase db = App.GENERIC_DAO.getWritableDatabase();
        ContentValues contentProdutos = converterProdutoToContentValue(produto);

        db.insert(Produto.TABLE,null,contentProdutos);
        db.close();
    }

    public void updateProduto(Produto produto) {
        SQLiteDatabase db = App.GENERIC_DAO.getWritableDatabase();
        ContentValues contentProdutos = converterProdutoToContentValue(produto);

        db.update(Produto.TABLE,contentProdutos,Produto.COLUMN_Id + " =? ",new String[]{produto.getId().toString()});
        db.close();
    }

    private ContentValues converterProdutoToContentValue(Produto produto) {
        ContentValues contentProdutos = new ContentValues();
        contentProdutos.put(Produto.COLUMN_Id,produto.getId());
        contentProdutos.put(Produto.COLUMN_CodigoBarra,produto.getCodigoBarras());
        contentProdutos.put(Produto.COLUMN_Nome,produto.getNome());
        contentProdutos.put(Produto.COLUMN_Foto,produto.getCaminhoFoto());
        contentProdutos.put(Produto.COLUMN_Categoria,produto.getCategoria());
        contentProdutos.put(Produto.COLUMN_Quantidade,produto.getQuantidade());
        contentProdutos.put(Produto.COLUMN_Unidade,produto.getUnidade());

        //Verificar se e dessa forma que insere um Blob


        contentProdutos.put(Produto.COLUMN_Observacao,produto.getObservacao());
        return contentProdutos;
    }

    private Produto generateProduto(Cursor c) {
        Produto produto = new Produto();
        produto.setId(c.getLong(c.getColumnIndex(Produto.COLUMN_Id)));
        produto.setCodigoBarras(c.getString(c.getColumnIndex(Produto.COLUMN_CodigoBarra)));
        produto.setNome(c.getString(c.getColumnIndex(Produto.COLUMN_Nome)));
        produto.setCaminhoFoto(c.getString(c.getColumnIndex(Produto.COLUMN_Foto)));
        produto.setCategoria(c.getString(c.getColumnIndex(Produto.COLUMN_Categoria)));
        produto.setQuantidade(c.getLong(c.getColumnIndex(Produto.COLUMN_Quantidade)));
        produto.setUnidade(c.getString(c.getColumnIndex(Produto.COLUMN_Unidade)));
        produto.setObservacao(c.getString(c.getColumnIndex(Produto.COLUMN_Observacao)));
        return produto;
    }

    public void deletarProduto(Produto produto) {
        SQLiteDatabase db = App.GENERIC_DAO.getWritableDatabase();
        String[] paramns = {produto.getId().toString()};
        db.delete(Produto.TABLE,Produto.COLUMN_Id + " = ?",paramns);
    }

    public List<Produto> buscarProdutos() {
        List<Produto> listProduto = new ArrayList<>();
        String sql = "SELECT * FROM " + Produto.TABLE ;
        SQLiteDatabase db = App.GENERIC_DAO.getReadableDatabase();

        Cursor c = db.rawQuery(sql,null);

        while(c.moveToNext()){
            Produto produto = generateProduto(c);
            listProduto.add(produto);
        }
        c.close();
        db.close();

        return listProduto;
    }

    public List<Produto> buscarProdutoPorCodigoDeBarra(String codigoDeBarras) {
        List<Produto> listProduto = new ArrayList<>();
        String sql = "SELECT * FROM " + Produto.TABLE + " WHERE "+ Produto.COLUMN_CodigoBarra +" = ? ";
        SQLiteDatabase db = App.GENERIC_DAO.getReadableDatabase();

        Cursor c = db.rawQuery(sql,new String[]{codigoDeBarras});

        while(c.moveToNext()){
            Produto produto = generateProduto(c);
            listProduto.add(produto);
        }
        c.close();
        db.close();

        return listProduto;
    }
}