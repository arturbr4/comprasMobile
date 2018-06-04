package br.com.mercados.compras.model;

import java.io.Serializable;
import java.util.Calendar;

import br.com.mercados.compras.util.Utils;

public class Compra implements Serializable{

    public static final String TABLE = "COMPRAS";
    // Labels Table Columns names
    public static final String COLUMN_Id = "ID";
    public static final String COLUMN_IdProduto = "IDPRODUTO";
    public static final String COLUMN_Preco = "PRECO";
    public static final String COLUMN_Local = "LOCAL";
    public static final String COLUMN_Data = "DATA";
    public static final String COLUMN_Liquidacao = "LIQUIDACAO";
    public static final String COLUMN_Observacao = "OBSERVACAO";

    private Long id;
    private Long idProduto;
    private Double preco;
    private String local;
    private Calendar data;
    private Boolean liquidacao;
    private String observacao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Boolean getLiquidacao() {
        return liquidacao;
    }

    public void setLiquidacao(Boolean liquidacao) {
        this.liquidacao = liquidacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "preco=" + preco +
                ", local='" + local + '\'' +
                ", data=" + Utils.convertCalendarToStringWithPattern(data,"dd/MM/yyyy") +
                ", liquidacao=" + liquidacao +
                '}';
    }
}
