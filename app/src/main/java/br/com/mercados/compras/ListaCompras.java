package br.com.mercados.compras;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaCompras extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compras);

        String[] itens = {"Arroz","Feijao","Batata","Cenoura"};
        ListView listaItens = findViewById(R.id.lista_compras);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);
        listaItens.setAdapter(stringArrayAdapter);



    }
}
