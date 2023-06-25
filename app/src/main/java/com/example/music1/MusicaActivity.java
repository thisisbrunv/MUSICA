package com.example.music1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.example.music1.R;
import com.example.music1.BDSQLiteHelper;

//import br.ucs.android.bancodedadoslocal2022.model.Livro;

public class MusicaActivity extends AppCompatActivity {

    private BDSQLiteHelper bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musica);
        bd = new BDSQLiteHelper(this);
        final EditText nome = (EditText) findViewById(R.id.edNome);
        final EditText artista = (EditText) findViewById(R.id.edArtista);
       // final EditText ano = (EditText) findViewById(R.id.edAno);
        Button novo = (Button) findViewById(R.id.btnAdd);
        novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Musica musica = new Musica();
                musica.setNome(nome.getText().toString());
                musica.setArtista(artista.getText().toString());
               // livro.setAno(Integer.parseInt(ano.getText().toString()));
                bd.addMusica(musica);
                Intent intent = new Intent(MusicaActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
