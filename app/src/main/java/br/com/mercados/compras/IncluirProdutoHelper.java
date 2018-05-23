package br.com.mercados.compras;

import android.widget.EditText;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

import br.com.mercados.compras.model.Produto;
import br.com.mercados.compras.util.Utils;

public class IncluirProdutoHelper {

    private final EditText nome;
    private final EditText quantidade;
    private final EditText unidade;
    private final EditText preco;
    private final EditText local;
    private final EditText data;
    private final EditText observacao;
    private final EditText categoria;

    public IncluirProdutoHelper(IncluirProdutoActivity activity){
        nome = activity.findViewById(R.id.incluir_produto_nome);
        quantidade = activity.findViewById(R.id.incluir_produto_quantidade);
        unidade = activity.findViewById(R.id.incluir_produto_unidade);
        preco = activity.findViewById(R.id.incluir_produto_preco);
        local = activity.findViewById(R.id.incluir_produto_local);
        data = activity.findViewById(R.id.incluir_produto_data);
        observacao = activity.findViewById(R.id.incluir_produto_observacao);
        categoria = activity.findViewById(R.id.incluir_produto_categoria);
    }

    public Produto retornaProduto(){
        //Validar quando for vazio antes de enviar pelo android

        Produto produto = new Produto();
        produto.setNome(nome.getText().toString());
        produto.setQuantidade(Long.valueOf(quantidade.getText().toString()));
        produto.setUnidade(unidade.getText().toString());
        produto.setPreco(Double.valueOf(preco.getText().toString()));
        produto.setLocal(local.getText().toString());
        produto.setData(Utils.convertStringToCalendar(data.getText().toString()));
        produto.setObservacao(observacao.getText().toString());
        produto.setCategoria(categoria.getText().toString());

        return produto;
    }



}
