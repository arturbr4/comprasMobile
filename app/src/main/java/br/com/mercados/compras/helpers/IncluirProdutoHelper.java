package br.com.mercados.compras.helpers;

import android.graphics.Bitmap;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import br.com.mercados.compras.activities.IncluirProdutoActivity;
import br.com.mercados.compras.R;
import br.com.mercados.compras.model.Produto;
import br.com.mercados.compras.util.Utils;

public class IncluirProdutoHelper {

    private final TextInputLayout codigoBarraTextInput;
    private final TextInputLayout nomeTextInput;
    private final TextInputLayout quantidadeTextInput;
    private final TextInputLayout unidadeTextInput;
    private final TextInputLayout observacaoTextInput;
    private final TextInputLayout categoriaTextInput;

    private final EditText codigoBarra;
    private final EditText nome;
    private final EditText quantidade;
    private final EditText unidade;
    private final EditText observacao;
    private final EditText categoria;
    private final ImageView foto;
    private final IncluirProdutoActivity activity;
    private Produto produto;

    public IncluirProdutoHelper(IncluirProdutoActivity activity){

        codigoBarraTextInput = activity.findViewById(R.id.incluir_produto_codigoBarraWrapper);
        nomeTextInput  = activity.findViewById(R.id.incluir_produto_nomeWrapper);
        quantidadeTextInput  = activity.findViewById(R.id.incluir_produto_quantidadeWrapper);
        unidadeTextInput  = activity.findViewById(R.id.incluir_produto_unidadeWrapper);
        observacaoTextInput  = activity.findViewById(R.id.incluir_produto_observacaoWrapper);
        categoriaTextInput  = activity.findViewById(R.id.incluir_produto_categoriaWrapper);

        codigoBarra = activity.findViewById(R.id.incluir_produto_codigoBarra);
        nome = activity.findViewById(R.id.incluir_produto_nome);
        foto = activity.findViewById(R.id.incluir_produto_exibirFoto);
        quantidade = activity.findViewById(R.id.incluir_produto_quantidade);
        unidade = activity.findViewById(R.id.incluir_produto_unidade);
        observacao = activity.findViewById(R.id.incluir_produto_observacao);
        categoria = activity.findViewById(R.id.incluir_produto_categoria);

        produto = new Produto();
        this.activity = activity;
    }

    public Produto popularInformacoesNoObjetoProduto(){
        //Validar quando for vazio antes de enviar pelo android
        produto.setCodigoBarras(codigoBarra.getText().toString());
        produto.setNome(nome.getText().toString());
        produto.setCategoria(categoria.getText().toString());
        produto.setUnidade(unidade.getText().toString());
        produto.setObservacao(observacao.getText().toString());

        if(foto.getTag() != null)
            produto.setCaminhoFoto(foto.getTag().toString());

        if (!quantidade.getText().toString().isEmpty())
            produto.setQuantidade(Long.valueOf(quantidade.getText().toString()));


        return produto;
    }

    public void preencherFormularioProduto(Produto produto) {
        codigoBarra.setText(produto.getCodigoBarras().toString());
        nome.setText(produto.getNome());
        quantidade.setText(produto.getQuantidade().toString());
        unidade.setText(produto.getUnidade());
        observacao.setText(produto.getObservacao());
        categoria.setText(produto.getCategoria());

        Bitmap bitmap = Utils.tratarImagem(produto.getCaminhoFoto());
        if(bitmap != null)
            foto.setImageBitmap(bitmap);
        
        this.produto = produto;
    }

    public Boolean formularioEhValido(){
        //TODO pensar em melhorar esse metodo
        //TODO Validar de outras formas os campos

        List<Boolean> isValid = new ArrayList<>();

        isValid.add(!ehVazio(codigoBarra));
        imprimirMensagem(ehVazio(codigoBarra), R.string.campoVazio, codigoBarraTextInput);

        isValid.add(!ehVazio(nome));
        imprimirMensagem(ehVazio(nome), R.string.campoVazio, nomeTextInput);

        isValid.add(!ehVazio(quantidade));
        imprimirMensagem(ehVazio(quantidade), R.string.campoVazio, quantidadeTextInput);

        isValid.add(!ehVazio(unidade));
        imprimirMensagem(ehVazio(unidade), R.string.campoVazio, unidadeTextInput);

        isValid.add(!ehVazio(categoria));
        imprimirMensagem(ehVazio(categoria), R.string.campoVazio, categoriaTextInput);

        return !isValid.contains(false);
    }

    public static boolean ehVazio(EditText editText) {
        if (editText.getText().toString().isEmpty())
            return true;
        else
            return false;
    }

    private void imprimirMensagem(boolean isValid, int idMensagem, TextInputLayout textInput) {
        if(isValid)
            textInput.setError(activity.getString(idMensagem));
        else
            textInput.setErrorEnabled(false);
    }

    public void carregarImagem(String caminhoFoto){
        foto.setImageBitmap(Utils.tratarImagem(caminhoFoto));
        foto.setTag(caminhoFoto);
    }




}
