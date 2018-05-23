package br.com.mercados.compras.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

import br.com.mercados.compras.util.Utils;

public class Produto {

    private Long id;
    private String nome;
    private String categoria;
    private Long quantidade;
    private String unidade;
    private Double preco;
    private String local;
    private Calendar data;
    private String observacao;


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

    public Double getPreco() { return preco; }

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

    public void setData(Calendar data) { this.data = data; }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return this.nome + " - " + Utils.convertCalendarToStringWithPattern(this.data,"dd/MM/yyyy");
    }
}
