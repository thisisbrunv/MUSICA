package com.example.music1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class interfaceArtistas extends AppCompatActivity {

    ListView lista;
    private static ArrayList<Artista> listaArtistas;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_artistas);

      //  lista = findViewById(R.id.artistas);
       // listaArtistas = interfaceMusics.getListaArtistas();

        }
    }
