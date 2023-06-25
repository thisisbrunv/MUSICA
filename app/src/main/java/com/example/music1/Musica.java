package com.example.music1;

import android.widget.ImageView;

public class Musica {

    int id;

    //private String albumArt;
    private String nome;

    //private Emocao;

    int counter_musica;
   // private Album album;
    private String artista;

    public Musica(int id, String nome, String artista) {
        this.id = id;
        //this.albumArt = albumArt;
        this.nome = nome;
        this.artista = artista;
        //this.album = album;
    }

    public Musica() {

    }

    public int getID(){
        return id;
    }
   // public String getAlbumArt(){return albumArt;}
    public String getNome(){return nome;}
    public String getArtista(){return artista;}
  //  public Album getAlbum(){return album;}


    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCounter_musica(int counter_musica) {
        this.counter_musica = counter_musica;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }


    public int getId() {
        return id;
    }
}
