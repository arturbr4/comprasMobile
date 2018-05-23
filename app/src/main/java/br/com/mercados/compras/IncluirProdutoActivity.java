package br.com.mercados.compras;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.mercados.compras.dao.ProdutoDAO;
import br.com.mercados.compras.model.Produto;

public class IncluirProdutoActivity extends AppCompatActivity {

    private IncluirProdutoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_produto);
        helper = new IncluirProdutoHelper(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_incluir_produto,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.menu_incluir_produto_salvar:
                Produto produto = helper.retornaProduto();
                ProdutoDAO dao = new ProdutoDAO(this);
                dao.inserirProduto(produto);
                dao.close();
                Toast.makeText(IncluirProdutoActivity.this, produto.getNome() + " Salvo", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
