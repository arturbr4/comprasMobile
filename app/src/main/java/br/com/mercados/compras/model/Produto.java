package br.com.mercados.compras.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import br.com.mercados.compras.util.Utils;

public class Produto implements Serializable{

    public static final String TABLE = "PRODUTOS";
    // Labels Table Columns names
    public static final String COLUMN_Id = "ID";
    public static final String COLUMN_CodigoBarra = "CODIGO_BARRAS";
    public static final String COLUMN_Nome = "NOME";
    public static final String COLUMN_Categoria = "CATEGORIA";
    public static final String COLUMN_Quantidade = "QUANTIDADE";
    public static final String COLUMN_Unidade = "UNIDADE";
    public static final String COLUMN_Foto = "CAMINHO_FOTO";
    public static final String COLUMN_Observacao = "OBSERVACAO";


    private Long id;
    private String codigoBarras;
    private String nome;
    private String categoria;
    private Long quantidade;
    private String unidade;
    private String caminhoFoto;
    private String observacao;
    private List<Compra> compras;

    //TODO quantidade deve ser limitado, drop-down


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getCaminhoFoto() { return caminhoFoto; }

    public void setCaminhoFoto(String caminhoFoto) { this.caminhoFoto = caminhoFoto; }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getCodigoBarras() { return codigoBarras; }

    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public List<Compra> getCompras() { return compras; }

    public void setCompras(List<Compra> compras) { this.compras = compras; }

    @Override
    public String toString() {
        return this.nome;
    }
}
