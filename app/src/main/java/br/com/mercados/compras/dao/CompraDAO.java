package br.com.mercados.compras.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.mercados.app.App;
import br.com.mercados.compras.model.Compra;
import br.com.mercados.compras.model.Produto;
import br.com.mercados.compras.util.Utils;

public class CompraDAO{

    public static String createTable() {
        //PRECO REAL, LOCAL TEXT, DATA DATE
        String sql = "CREATE TABLE " + Compra.TABLE + "(" +
                        Compra.COLUMN_Id + " INTEGER PRIMARY KEY, "+
                        Compra.COLUMN_IdProduto + " INTEGER NOT NULL, "+
                        Compra.COLUMN_Preco + " REAL, " +
                        Compra.COLUMN_Local + " TEXT, " +
                        Compra.COLUMN_Data + " DATE, "+
                        Compra.COLUMN_Liquidacao + " BOOLEAN, "+
                        Compra.COLUMN_Observacao + " TEXT)";
        return sql;
    }

    private Compra generateCompra(Cursor c) {
        Compra compra = new Compra();
        compra.setId(c.getLong(c.getColumnIndex(Compra.COLUMN_Id)));
        compra.setIdProduto(c.getLong(c.getColumnIndex(Compra.COLUMN_IdProduto)));
        compra.setPreco(c.getDouble(c.getColumnIndex(Compra.COLUMN_Preco)));
        compra.setLocal(c.getString(c.getColumnIndex(Compra.COLUMN_Local)));
        compra.setData(Utils.convertTimeMilisToCalendar(c.getLong(c.getColumnIndex(Compra.COLUMN_Data))));
        compra.setLiquidacao(Utils.convertIntToBoolean(c.getInt(c.getColumnIndex(Compra.COLUMN_Liquidacao))));
        compra.setObservacao(c.getString(c.getColumnIndex(Compra.COLUMN_Observacao)));
        return compra;
    }

    public void inserirCompra(Compra compra) {
        SQLiteDatabase db =  App.GENERIC_DAO.getWritableDatabase();

        ContentValues contentCompras = new ContentValues();
        contentCompras.put(Compra.COLUMN_Id,compra.getId());
        contentCompras.put(Compra.COLUMN_IdProduto,compra.getIdProduto());
        contentCompras.put(Compra.COLUMN_Preco,compra.getPreco());
        contentCompras.put(Compra.COLUMN_Local,compra.getLocal());
        contentCompras.put(Compra.COLUMN_Data,compra.getData().getTimeInMillis());
        contentCompras.put(Compra.COLUMN_Liquidacao,compra.getLiquidacao());
        contentCompras.put(Compra.COLUMN_Observacao,compra.getObservacao());

        db.insert(Compra.TABLE,null,contentCompras);
        db.close();
    }

    public List<Compra> buscarTodasCompras() {
        List<Compra> listCompras = new ArrayList<>();
        String sql = "SELECT * FROM "+ Compra.TABLE;
        SQLiteDatabase db = App.GENERIC_DAO.getReadableDatabase();

        Cursor c = db.rawQuery(sql,null);

        while(c.moveToNext()){
            Compra produto = generateCompra(c);
            listCompras.add(produto);
        }
        c.close();
        db.close();

        return listCompras;
    }

    public List<Compra> buscarCompraPorIdProduto(Long idProduto) {
        List<Compra> listCompras = new ArrayList<>();
        String sql = "SELECT * FROM " + Compra.TABLE +" WHERE " + Compra.COLUMN_IdProduto + " = ? ";
        SQLiteDatabase db = App.GENERIC_DAO.getReadableDatabase();

        Cursor c = db.rawQuery(sql,new String[]{idProduto.toString()});

        while(c.moveToNext()){
            Compra compra = generateCompra(c);
            listCompras.add(compra);
        }
        c.close();
        db.close();

        return listCompras;
    }

    public List<Produto> incluirComprasEmProduto(List<Produto> produtos) {
        List<Produto> produtosComCompras = new ArrayList<>();

        for(Produto produto : produtos){
            produto.setCompras(buscarCompraPorIdProduto(produto.getId()));
            produtosComCompras.add(produto);
        }

        return produtosComCompras;
    }

    public void deletarCompra(Compra compra) {
        SQLiteDatabase db = App.GENERIC_DAO.getWritableDatabase();
        String[] paramns = {compra.getId().toString()};
        db.delete(Compra.TABLE,Compra.COLUMN_Id + " = ?",paramns);
    }
}