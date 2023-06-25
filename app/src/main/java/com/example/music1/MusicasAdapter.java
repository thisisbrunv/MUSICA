package com.example.music1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import com.example.music1.R;

//import br.ucs.android.bancodedadoslocal2022.model.Livro;

public class MusicasAdapter extends ArrayAdapter<Musica> {

    private final Context context;
    private final ArrayList<Musica> elementos;

    public MusicasAdapter(Context context, ArrayList<Musica> elementos) {
        super(context, R.layout.linha, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha, parent, false);
        TextView titulo = (TextView) rowView.findViewById(R.id.txtNome);
        //TextView ano = (TextView) rowView.findViewById(R.id.txtAno);
        TextView artista = (TextView) rowView.findViewById(R.id.txtArtista);
        titulo.setText(elementos.get(position).getNome());
        artista.setText(elementos.get(position).getArtista());
       // ano.setText(Integer.toString(elementos.get(position).getAno()));
        return rowView;
    }
}


