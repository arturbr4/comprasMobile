package br.com.mercados.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.mercados.compras.R;
import br.com.mercados.compras.activities.ListaProdutosActivity;
import br.com.mercados.compras.model.Produto;
import br.com.mercados.compras.util.Utils;

public class ProdutoAdapter extends BaseAdapter{

    private final Context context;
    private List<Produto> produtos;

    public ProdutoAdapter(Context context, List<Produto> produtos) {
        this.produtos = produtos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produtos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produto produto = produtos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if(convertView == null)
            view = inflater.inflate(R.layout.list_item_produto, parent, false);

        TextView campoNome = view.findViewById(R.id.list_item_produto_nome);
        campoNome.setText(produto.getNome());

        TextView campoCategoria =view.findViewById(R.id.list_item_produto_categoria);
        campoCategoria.setText(produto.getCategoria());

        ImageView campoImagem = view.findViewById(R.id.list_item_produto_exibirFoto);

        Bitmap bitmap = Utils.tratarImagem(produto.getCaminhoFoto());
        if(bitmap != null)
            campoImagem.setImageBitmap(bitmap);

        return view;
    }
}
