package br.com.mercados.compras.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.mercados.app.App;
import br.com.mercados.compras.model.Compra;
import br.com.mercados.compras.model.Produto;

public class GenericDao extends SQLiteOpenHelper {

    public static final Integer DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "historicoPrecos";

    public GenericDao() {
        super(App.CONTEXT, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProdutoDAO.createTable());
        db.execSQL(CompraDAO.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Produto.TABLE) ;
        db.execSQL("DROP TABLE IF EXISTS "+ Compra.TABLE);

        onCreate(db);
    }
}
