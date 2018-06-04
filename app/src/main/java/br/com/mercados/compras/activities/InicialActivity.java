package br.com.mercados.compras.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.mercados.compras.R;

public class InicialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        findViewById(R.id.inicial_verificarPrecos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irParaVerificarCodigoBarras = new Intent(InicialActivity.this,BarCodeReaderActivity.class);
                startActivity(irParaVerificarCodigoBarras);
            }
        });

        findViewById(R.id.inicial_produtos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irParaListaProdutos = new Intent(InicialActivity.this,ListaProdutosActivity.class);
                startActivity(irParaListaProdutos);
            }
        });

        findViewById(R.id.inicial_incluirCompras).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irParaIncluirCompras = new Intent(InicialActivity.this, ListaComprasActivity.class);
                startActivity(irParaIncluirCompras);
            }
        });



    }
}
