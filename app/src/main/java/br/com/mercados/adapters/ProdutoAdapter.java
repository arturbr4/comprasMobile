package br.com.mercados.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.mercados.compras.R;
import br.com.mercados.compras.model.Produto;
import br.com.mercados.compras.util.Utils;

public class ProdutoAdapter extends BaseAdapter implements Filterable{

    private final Context context;
    private List<Produto> originalProdutos;
    private List<Produto> filtradoProdutos;
    private ContactsFilter contactsFilter;


    public ProdutoAdapter(Context context, List<Produto> produtos) {
        this.originalProdutos = produtos;
        this.filtradoProdutos = produtos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return filtradoProdutos.size();
    }

    @Override
    public Object getItem(int position) {
        return filtradoProdutos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filtradoProdutos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produto produto = filtradoProdutos.get(position);

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

    @Override
    public Filter getFilter() {
        if(contactsFilter == null)
            contactsFilter = new ContactsFilter();

        return contactsFilter;
    }


    private class ContactsFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if(constraint == null || constraint.length() == 0) {
                filterResults.values = originalProdutos;
                filterResults.count = originalProdutos.size();
            }
            else {
                List<Produto> produtosFiltrados = new ArrayList<>();

                for(Produto produto : originalProdutos){
                    if(produto.getNome().toUpperCase().contains(constraint.toString().toUpperCase()))
                        produtosFiltrados.add(produto);
                }
                filterResults.values = produtosFiltrados;
                filterResults.count = produtosFiltrados.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtradoProdutos = (List<Produto>) results.values;
            notifyDataSetChanged();
        }
    }

}
