package com.example.music1;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SongAdapter extends BaseAdapter {
        @Override
        public int getCount() {
                return musicas.size();
            }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //mostra o layout
        LinearLayout songLay = (LinearLayout)songInf.inflate
                (R.layout.musica, parent, false);
        //pega o titulo e o artista
        TextView musicaView = (TextView)songLay.findViewById(R.id.titulo_musica);
        TextView artistaView = (TextView)songLay.findViewById(R.id.artista_musica);
        //pega a musica e a posição
        Musica currMusica = musicas.get(position);
        //pega as strings dos titulos e artista
        musicaView.setText(currMusica.getNome());
        artistaView.setText(currMusica.getArtista());
        //posição tag
        songLay.setTag(position);
        return songLay;
    }

    private ArrayList<Musica> musicas;
    private LayoutInflater songInf;


    public SongAdapter(Context c, ArrayList<Musica> theMusicas){
        musicas=theMusicas;
        songInf=LayoutInflater.from(c);
    }

    }
