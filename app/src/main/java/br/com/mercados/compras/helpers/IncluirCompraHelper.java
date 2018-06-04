package br.com.mercados.compras.helpers;

import android.widget.EditText;

import br.com.mercados.compras.R;
import br.com.mercados.compras.activities.IncluirComprasActivity;
import br.com.mercados.compras.model.Compra;
import br.com.mercados.compras.util.Utils;

public class IncluirCompraHelper {

    private final EditText preco;
    private final EditText local;
    private final EditText data;
    //private final EditText observacao;
    //private final EditText produtoID;
    private Compra compra;

    public IncluirCompraHelper(IncluirComprasActivity activity){
        //idProduto = activity.findViewById(R.id.incluir_compra_codigoBarra);

        preco = activity.findViewById(R.id.incluir_compra_preco);
        local = activity.findViewById(R.id.incluir_compra_local);
        data = activity.findViewById(R.id.incluir_compra_data);
       //observacao = activity.findViewById(R.id.incluir_compra_observacao);

        //produtoID = activity.findViewById(R.id.incluir_compra_produtoID);

        compra = new Compra();
    }

    public Compra retornaCompra(){
        //Validar quando for vazio antes de enviar pelo android
        //compra.setIdProduto(Long.valueOf(codigoBarra.getText().toString()));

        //compra.setLiquidacao(nome.getText().toString());
        //compra.setIdProduto(Long.valueOf(produtoID.getText().toString()));
        compra.setData(Utils.convertStringToCalendar(data.getText().toString()));
        compra.setLocal(local.getText().toString());
        compra.setPreco(Double.valueOf(preco.getText().toString()));
        //compra.setObservacao(observacao.getText().toString());

        return compra;
    }


    public void preencherFormularioCompra(Compra compra) {

        //TODO Incluir Campos Liquidacao e IDProduto
        //TODO Verificar se todos os celular usam o mesmo pattern com "."

        data.setText(Utils.convertCalendarToStringWithPattern(compra.getData(),"dd.MM.yyyy"));
        preco.setText(compra.getPreco().toString());
        local.setText(compra.getLocal());
        //observacao.setText(compra.getObservacao());

        this.compra = compra;
    }


}
