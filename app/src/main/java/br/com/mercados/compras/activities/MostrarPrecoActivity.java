package br.com.mercados.compras.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import br.com.mercados.compras.R;
import br.com.mercados.compras.dao.CompraDAO;
import br.com.mercados.compras.dao.ProdutoDAO;
import br.com.mercados.compras.helpers.IncluirProdutoHelper;
import br.com.mercados.compras.helpers.MostrarPrecoHelper;
import br.com.mercados.compras.model.Produto;
import br.com.mercados.compras.util.ImageConverter;

public class MostrarPrecoActivity extends AppCompatActivity {

    private MostrarPrecoHelper mostrarPrecoHelper;
    private ProdutoDAO produtoDAO;
    private CompraDAO comprasDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO Alterar para realizar o carregamento da Lista no onResume, para dar o load apos a insercao de um novo preoc

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_preco);

        mostrarPrecoHelper = new MostrarPrecoHelper(this);
        produtoDAO = new ProdutoDAO();
        comprasDao = new CompraDAO();

        Intent intent = getIntent();
        String codigoDeBarras = intent.getStringExtra("codigoDeBarra");

        // TODO Alterar para ficar de forma mais legivel, talvez incluir o metodo incluirCompras no Objeto Produto
        List<Produto> produtos = produtoDAO.buscarProdutoPorCodigoDeBarra(codigoDeBarras);
        produtos = comprasDao.incluirComprasEmProduto(produtos);
        final Produto produto = produtos.get(0);

        Button botaoIncluirNovoPreco = findViewById(R.id.mostrar_preco_incluirNovoPreco);
        botaoIncluirNovoPreco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irParaIncluirCompras = new Intent(MostrarPrecoActivity.this,IncluirComprasActivity.class);
                irParaIncluirCompras.putExtra("codigoProduto",produto.getId());
                startActivity(irParaIncluirCompras);
                Toast.makeText(MostrarPrecoActivity.this,"CRIAR PAGINA PARA CADASTRAR NOVO PRECO",Toast.LENGTH_SHORT).show();
            }
        });

        if(!produtos.isEmpty()){
            mostrarPrecoHelper.preencherFormularioProduto(produto);
        }else{
            Intent moverParaIncluirProduto = new Intent(MostrarPrecoActivity.this,IncluirProdutoActivity.class);
            moverParaIncluirProduto.putExtra("codigoDeBarra",codigoDeBarras);
            startActivity(moverParaIncluirProduto);
            Toast.makeText(this,"Produto ainda nao cadastrado",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
