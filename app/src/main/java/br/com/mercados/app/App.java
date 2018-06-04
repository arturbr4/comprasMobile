package br.com.mercados.app;

import android.app.Application;
import android.content.Context;

import br.com.mercados.compras.dao.GenericDao;

public class App extends Application{

    public static Context CONTEXT;
    public static GenericDao GENERIC_DAO;


    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;
        GENERIC_DAO = new GenericDao();
    }
}
