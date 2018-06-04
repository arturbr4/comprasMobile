package br.com.mercados.compras.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.mercados.compras.R;
import br.com.mercados.compras.dao.CompraDAO;
import br.com.mercados.compras.dao.ProdutoDAO;
import br.com.mercados.compras.model.Compra;
import br.com.mercados.compras.model.Produto;

public class ListaComprasActivity extends AppCompatActivity {

    private ListView listaCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compras);

        listaCompras = findViewById(R.id.lista_compras_compras);
        Button salvarCompra = findViewById(R.id.lista_compras_incluir_compra);

        listaCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Compra compra = (Compra) listaCompras.getItemAtPosition(position);

                Intent formularioUpdate = new Intent(ListaComprasActivity.this,IncluirComprasActivity.class);
                formularioUpdate.putExtra("compra",compra);
                startActivity(formularioUpdate);

            }
        });

        salvarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentIrParaFormulario = new Intent(ListaComprasActivity.this, IncluirComprasActivity.class); //BarCodeReaderActivity.class
                startActivity(intentIrParaFormulario);
            }
        });
        
        registerForContextMenu(listaCompras);
    }

    private void carregaLista() {
        CompraDAO dao = new CompraDAO();
        List<Compra> compras = dao.buscarTodasCompras();

        ArrayAdapter<Compra> stringArrayAdapter = new ArrayAdapter<Compra>(this, android.R.layout.simple_list_item_1, compras);
        listaCompras.setAdapter(stringArrayAdapter);
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
                Compra compra = (Compra) listaCompras.getItemAtPosition(info.position);
                CompraDAO dao = new CompraDAO();
                dao.deletarCompra(compra);

                carregaLista();

                return false;
            }
        });
    }
}