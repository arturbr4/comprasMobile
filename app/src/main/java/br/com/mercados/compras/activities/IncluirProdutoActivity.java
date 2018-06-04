package br.com.mercados.compras.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import br.com.mercados.compras.R;
import br.com.mercados.compras.dao.ProdutoDAO;
import br.com.mercados.compras.helpers.IncluirProdutoHelper;
import br.com.mercados.compras.model.Produto;

import static android.support.v4.content.FileProvider.getUriForFile;

public class IncluirProdutoActivity extends AppCompatActivity {

    private static final int ID_TIRAR_FOTO = 1;
    private EditText codigoDeBarrasEdit;
    private Button incluirFotoButton;

    private IncluirProdutoHelper helper;
    private Intent intent;

    private String caminhoFoto;

    private void inicializarVariaveisActivity(){
        codigoDeBarrasEdit = findViewById(R.id.incluir_produto_codigoBarra);
        incluirFotoButton = findViewById(R.id.incluir_produto_incluirFoto);

        helper = new IncluirProdutoHelper(this);
        intent = getIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_produto);

        inicializarVariaveisActivity();

        Produto produto = (Produto) intent.getSerializableExtra("produto");

        if(produto != null)
            helper.preencherFormularioProduto(produto);

        String codigoDeBarras = (String) intent.getSerializableExtra("codigoDeBarra");

        if(codigoDeBarras != null)
            codigoDeBarrasEdit.setText(codigoDeBarras);

        incluirFotoButton();
    }

    private void incluirFotoButton(){
        incluirFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null)+ "/" + System.currentTimeMillis() +".jpg";
                Uri caminhoArmazenar = getUriForFile(IncluirProdutoActivity.this, "br.com.mercados", new File(caminhoFoto));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, caminhoArmazenar);
                startActivityForResult(intent,ID_TIRAR_FOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case ID_TIRAR_FOTO:
                    helper.carregarImagem(caminhoFoto);
                 break;
            }
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
                incluirProdutoButton();
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void incluirProdutoButton() {
        Produto produto = helper.popularInformacoesNoObjetoProduto();

        if(helper.formularioEhValido()) {
            ProdutoDAO dao = new ProdutoDAO();
            if (produto.getId() == null)
                dao.inserirProduto(produto);
            else
                dao.updateProduto(produto);
            Toast.makeText(IncluirProdutoActivity.this, produto.getNome() + " Salvo", Toast.LENGTH_SHORT).show();
            finish();
        }
        Toast.makeText(IncluirProdutoActivity.this, "Formulario nao foi corretamente preenchido", Toast.LENGTH_SHORT).show();
    }
}
