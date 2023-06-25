package com.example.music1;

public class playlisMaisTocadas extends Playlist {

    int numero_vezes;

    public playlisMaisTocadas(String nome, int numero_vezes) {
        super(nome);
        this.numero_vezes = numero_vezes;
    }

    public int getNumero_vezes() {
        return numero_vezes;
    }

    public void setNumero_vezes(int numero_vezes) {
        this.numero_vezes = numero_vezes;
    }
}
