package br.com.mercados.compras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.mercados.compras.dao.ProdutoDAO;
import br.com.mercados.compras.model.Produto;

public class ListaComprasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compras);

        ProdutoDAO dao = new ProdutoDAO(this);
        List<Produto> produtos = dao.buscarProdutos();

        ListView listaItens = findViewById(R.id.lista_compras_produtos);
        ArrayAdapter<Produto> stringArrayAdapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, produtos);
        listaItens.setAdapter(stringArrayAdapter);

        Button salvarProduto = findViewById(R.id.lista_compras_incluir_produto);
        salvarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentIrParaFormulario = new Intent(ListaComprasActivity.this, IncluirProdutoActivity.class);
                startActivity(intentIrParaFormulario);
            }
        });
    }
}