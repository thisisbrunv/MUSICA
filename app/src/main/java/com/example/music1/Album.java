package com.example.music1;

public class Album {

   private Artista Artista;
    String nome;
    String capa;
    String ano;
    String genero;
    long id;


    public Album(String nome, String nome1, String capa, String ano, String genero, long id) {
        this.nome = nome1;
        this.capa = capa;
        this.ano = ano;
        this.genero = genero;
        this.id = id;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}


