package br.com.mercados.compras.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import br.com.mercados.adapters.ProdutoAdapter;
import br.com.mercados.compras.R;
import br.com.mercados.compras.dao.ProdutoDAO;
import br.com.mercados.compras.model.Produto;

public class ListaProdutosActivity extends AppCompatActivity {

    private ListView listaProdutos;
    private ProdutoAdapter adapter;
    private TextWatcher textWatcherImplemented;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        listaProdutos = findViewById(R.id.lista_produtos_produtos);
        Button salvarProduto = findViewById(R.id.lista_produtos_incluir_produto);


        listaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Produto produto = (Produto) listaProdutos.getItemAtPosition(position);

                Intent formularioUpdate = new Intent(ListaProdutosActivity.this,IncluirProdutoActivity.class);
                formularioUpdate.putExtra("produto",produto);
                startActivity(formularioUpdate);
            }
        });

        salvarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentIrParaFormulario = new Intent(ListaProdutosActivity.this, IncluirProdutoActivity.class); //BarCodeReaderActivity.class
                startActivity(intentIrParaFormulario);
            }
        });

        if(textWatcherImplemented == null)
            textWatcherImplemented = new TextWatcherImplemented();


        EditText filtrarProdutos = findViewById(R.id.lista_produtos_filtrar);
        filtrarProdutos.addTextChangedListener(textWatcherImplemented);
        
        registerForContextMenu(listaProdutos);
    }

    private void carregaLista() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> produtos = dao.buscarProdutos();

        //ArrayAdapter<Produto> stringArrayAdapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, produtos);
        adapter = new ProdutoAdapter(this,produtos);
        listaProdutos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem deletar = menu.add("Excluir");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Produto produto = (Produto) listaProdutos.getItemAtPosition(info.position);

                ProdutoDAO dao = new ProdutoDAO();
                dao.deletarProduto(produto);

                carregaLista();

                return false;
            }
        });
    }

    private class TextWatcherImplemented implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ListaProdutosActivity.this.adapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}