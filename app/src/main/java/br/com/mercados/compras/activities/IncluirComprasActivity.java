package br.com.mercados.compras.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import br.com.mercados.compras.R;
import br.com.mercados.compras.dao.CompraDAO;
import br.com.mercados.compras.dao.ProdutoDAO;
import br.com.mercados.compras.helpers.IncluirCompraHelper;
import br.com.mercados.compras.helpers.IncluirProdutoHelper;
import br.com.mercados.compras.model.Compra;
import br.com.mercados.compras.model.Produto;

public class IncluirComprasActivity extends AppCompatActivity {

    private IncluirCompraHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_compra);
        helper = new IncluirCompraHelper(this);

        Intent intent = getIntent();
        Compra compra = (Compra) intent.getSerializableExtra("compra");
        if(compra != null){
            helper.preencherFormularioCompra(compra);
        }

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
//        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.incluir_compra_autoComplete);
//        textView.setAdapter(adapter);
    }

    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_incluir_compra,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_incluir_compra_salvar:
                Compra compra = helper.retornaCompra();
                CompraDAO dao = new CompraDAO();
                dao.inserirCompra(compra);
                Toast.makeText(IncluirComprasActivity.this, "Compra Salva", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
