package com.example.music1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//import br.ucs.android.bancodedadoslocal2022.model.Livro;

public class EditarMusicaActivity extends AppCompatActivity {

    private com.example.music1.BDSQLiteHelper bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_musica);
        Intent intent = getIntent();
        final int id = intent.getIntExtra("ID", 0);
        bd = new BDSQLiteHelper(this);
        Musica musica = bd.getMusica((int) id);
        final EditText nome = (EditText) findViewById(R.id.etNome);
        final EditText artista = (EditText) findViewById(R.id.etArtista);
       // final EditText ano = (EditText) findViewById(R.id.etAno);
        nome.setText(musica.getNome());
        artista.setText(musica.getArtista());
      //  ano.setText(String.valueOf(livro.getAno()));

        final Button alterar = (Button) findViewById(R.id.btnAlterar);
        alterar.setOnClickListener(v -> {
            Musica musica1 = new Musica();
            musica1.setId(id);
            musica1.setNome(nome.getText().toString());
            musica1.setArtista(artista.getText().toString());
          //  musica1.setAno(Integer.parseInt(ano.getText().toString()));
            bd.updateMusica(musica1);
            Intent intent1 = new Intent(EditarMusicaActivity.this, MainActivity.class);
            startActivity(intent1);
        });

        final Button remover = (Button) findViewById(R.id.btnRemover);
        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(EditarMusicaActivity.this)
                        .setTitle(R.string.confirmar_exclusao)
                        .setMessage(R.string.quer_mesmo_apagar)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Musica musica = new Musica();
                                musica.setId(id);
                                bd.deleteMusica(musica);
                                Intent intent = new Intent(EditarMusicaActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }
}