package com.minhaagenciadeviagens.agenciaviagens.Model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int idUsuario, isCliente;
    private String nome, endereco, cidade, uf, dataNacimento,telefone,email,senha;

    public Usuario() {
    }

    public Usuario(int idUsuario, int isCliente, String nome, String endereco, String cidade, String uf, String dataNacimento, String telefone, String email, String senha) {
        this.idUsuario = idUsuario;
        this.isCliente = isCliente;
        this.nome = nome;
        this.endereco = endereco;
        this.cidade = cidade;
        this.uf = uf;
        this.dataNacimento = dataNacimento;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(int isCliente, String nome, String endereco, String cidade, String uf, String dataNacimento, String telefone, String email, String senha) {
        this.isCliente = isCliente;
        this.nome = nome;
        this.endereco = endereco;
        this.cidade = cidade;
        this.uf = uf;
        this.dataNacimento = dataNacimento;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIsCliente() {
        return isCliente;
    }

    public void setIsCliente(int isCliente) {
        this.isCliente = isCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getDataNacimento() {
        return dataNacimento;
    }

    public void setDataNacimento(String dataNacimento) {
        this.dataNacimento = dataNacimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
