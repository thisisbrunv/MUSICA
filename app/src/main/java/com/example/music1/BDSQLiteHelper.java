package com.example.music1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import com.example.music1.Musica;


public class BDSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "musicasDB";
    private static final String TABELA_MUSICAS = "musicas";
    private static final String ID = "id";
    private static final String TITULO = "titulo";
    private static final String ARTISTA = "artista";
   // private static final String ANO = "ano";
    private static final String[] COLUNAS = {ID, TITULO, ARTISTA};

    public BDSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE musicas ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "titulo TEXT,"+
                "artista TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS musicas");
        this.onCreate(db);
    }

    public void addMusica(Musica musica) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITULO, musica.getNome());
        values.put(ARTISTA, musica.getArtista());
       // values.put(ANO, new Integer(livro.getAno()));
        db.insert(TABELA_MUSICAS, null, values);
        db.close();
    }

    public Musica getMusica(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_MUSICAS, // a. tabela
                COLUNAS, // b. colunas
                " id = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            return null;
        } else {
            cursor.moveToFirst();
            Musica musica = cursorToMusica(cursor);
            return musica;
        }
    }

    private Musica cursorToMusica(Cursor cursor) {
        Musica musica = new Musica();
        musica.setId(Integer.parseInt(cursor.getString(0)));
        musica.setNome(cursor.getString(1));
        musica.setArtista(cursor.getString(2));
        return musica;
    }

    public ArrayList<Musica> getAllMusicas() {
        ArrayList<Musica> listaMusicas = new ArrayList<Musica>();
        String query = "SELECT * FROM " + TABELA_MUSICAS + " ORDER BY " + TITULO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Musica musica = cursorToMusica(cursor);
                listaMusicas.add(musica);
            } while (cursor.moveToNext());
        }
        return listaMusicas;
    }

    public int updateMusica(Musica musica) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITULO, musica.getNome());
        values.put(ARTISTA, musica.getArtista());
        //values.put(ANO, new Integer(livro.getAno()));
        int i = db.update(TABELA_MUSICAS, //tabela
                values, // valores
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(musica.getID()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }

    public int deleteMusica(Musica musica) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_MUSICAS, //tabela
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(musica.getID()) });
        db.close();
        return i; // número de linhas excluídas
    }
}

