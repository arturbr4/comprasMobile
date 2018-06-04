package br.com.mercados.compras.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.mercados.compras.R;
import br.com.mercados.compras.activities.IncluirProdutoActivity;
import br.com.mercados.compras.activities.MostrarPrecoActivity;
import br.com.mercados.compras.model.Compra;
import br.com.mercados.compras.model.Produto;
import br.com.mercados.compras.util.Utils;

public class MostrarPrecoHelper {

    private final TextView nome;
    private final TextView quantidade;
    //private final TextView unidade;
    private final TextView ultimoPreco;
    private final TextView menorPreco;
    private final TextView dataMenorPreco;
    private final TextView dataUltimoPreco;
    private final TextView categoria;
    private final ImageView foto;

    private Produto produto;

    public MostrarPrecoHelper(MostrarPrecoActivity activity){
        //codigoBarra = activity.findViewById(R.id.mostrar_preco_codigoBarra);
        nome = activity.findViewById(R.id.mostrar_preco_nome);
        categoria = activity.findViewById(R.id.mostrar_preco_categoria);
        quantidade = activity.findViewById(R.id.mostrar_preco_quantidade);
        foto = activity.findViewById(R.id.mostrar_preco_exibirFoto);
        //unidade = activity.findViewById(R.id.mostrar_preco_unidade);

        ultimoPreco = activity.findViewById(R.id.mostrar_preco_ultimoPreco);
        dataUltimoPreco = activity.findViewById(R.id.mostrar_preco_dataUltimoPreco);
        menorPreco = activity.findViewById(R.id.mostrar_preco_menorPreco);
        dataMenorPreco = activity.findViewById(R.id.mostrar_preco_dataMenorPreco);

        //observacao = activity.findViewById(R.id.incluir_produto_observacao);
        //
        //local = activity.findViewById(R.id.incluir_produto_local);
        produto = new Produto();
    }

    public void preencherFormularioProduto(Produto produto) {
        //codigoBarra.setText(produto.getCodigoBarras().toString());
        nome.setText(produto.getNome());
        categoria.setText(produto.getCategoria());
        quantidade.setText(produto.getQuantidade().toString() + " " +produto.getUnidade());
        foto.setImageBitmap(Utils.tratarImagem(produto.getCaminhoFoto()));

        Compra compraMenorPreco = retornaCompraMenorPreco(produto);
        menorPreco.setText(Utils.formataDoubleEConverterParaString(compraMenorPreco.getPreco()));
        dataMenorPreco.setText(Utils.convertCalendarToStringWithPattern(compraMenorPreco.getData(),"dd/MM/yyyy"));

        Compra compraUltimoPreco = retornaCompraUltimoPreco(produto);
        ultimoPreco.setText(Utils.formataDoubleEConverterParaString(compraUltimoPreco.getPreco()));
        dataUltimoPreco.setText(Utils.convertCalendarToStringWithPattern(compraUltimoPreco.getData(),"dd/MM/yyyy"));

        this.produto = produto;
    }

    private Compra retornaCompraMenorPreco(Produto produto) {
        
        Compra compraComMenorValor = null;

        for(Compra compraAComparar : produto.getCompras()) {
            if (compraComMenorValor == null || (compraAComparar.getPreco() < compraComMenorValor.getPreco()))
                compraComMenorValor = compraAComparar;
        }
        return compraComMenorValor;
    }

    private Compra retornaCompraUltimoPreco(Produto produto) {
        Compra compraComDataMaisRecente = null;

        for(Compra compraAComparar : produto.getCompras()) {
            if (compraComDataMaisRecente == null || (compraComDataMaisRecente.getData().compareTo(compraAComparar.getData()) < 0))
                compraComDataMaisRecente = compraAComparar;
        }
        return compraComDataMaisRecente;
    }
}
