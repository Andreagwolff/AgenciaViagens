package com.minhaagenciadeviagens.agenciaviagens.Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Pacote implements Serializable {

    private String local;
    private String imagem;
    private int dias;
    private BigDecimal preco;

    public Pacote(String local, String imagem, int dias, BigDecimal preco) {
        this.local = local;
        this.imagem = imagem;
        this.dias = dias;
        this.preco = preco;
    }

    public Pacote() {
    }

    public String getLocal() {
        return local;
    }

    public String getImagem() {
        return imagem;
    }

    public int getDias() {
        return dias;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}