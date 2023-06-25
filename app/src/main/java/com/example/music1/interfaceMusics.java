package com.example.music1;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class interfaceMusics extends Activity implements MediaController.MediaPlayerControl {

    private static ArrayList<Musica> listaMusicas;

    private static ArrayList<Artista> listaArtistas;

    Button gotoMenu;
    private ListView musicaView;

    private int songPosition=0;
    private String songTitle;
    private   String songArtist;
    private  String songImagePath;
    private long  currSong;

    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    private MusicController controller;
    private boolean paused=false, playbackPaused=false;

    public static ArrayList<Musica> getListaMusicas() {
        return listaMusicas;
    }
   public static ArrayList<Artista> getListaArtistas() {
        return listaArtistas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_musics);
        musicaView = (ListView) findViewById(R.id.lista_musicas);
        listaMusicas = getSongList();
        listaArtistas = getArtistList();
        gotoMenu = findViewById(R.id.gotoMenu);
        Collections.sort(listaMusicas, new Comparator<Musica>() {
            public int compare(Musica a, Musica b) {
                return a.getNome().compareTo(b.getNome());
            }
        });
        Collections.sort(listaArtistas, new Comparator<Artista>() {
            public int compare(Artista a, Artista b) {
                return a.getNome().compareTo(b.getNome());
            }
        });
        SongAdapter songAdt = new SongAdapter(this, listaMusicas);
        musicaView.setAdapter(songAdt);
        setController();
        gotoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(interfaceMusics.this, MainActivity.class);
                startActivity(intent);
            }
        });
        }

    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(listaMusicas);
            musicBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };
    @Override
    protected void onPause(){
        super.onPause();
        paused=true;
        //play.setImageResource(R.drawable.pause);
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused=false;
        }
        //  play.setImageResource(R.drawable.play);
    }
    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
        // play.setImageResource(R.drawable.stop);
    }
    private void playNext(){
        musicSrv.playNext();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
        //  play.setImageResource(R.drawable.forward);
    }

    private void setController(){
        //set the controller up
        controller = new MusicController(this);
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });
        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.lista_musicas));
        controller.setEnabled(true);
    }
    public ArrayList<Musica> getSongList() {
        ArrayList<Musica>lista = new ArrayList<>();
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){

            int colunaNome = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int colunaId = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int colunaArtista = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int colunaAlbumArt = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Albums.ALBUM_ART);

            //add musicas na lista
            do {
                int thisId = musicCursor.getInt(colunaId);
                //String thisAlbumArt = musicCursor.getString(colunaAlbumArt);
                String thisTitle = musicCursor.getString(colunaNome);
                String thisArtist = musicCursor.getString(colunaArtista);
                //colocar info album
                lista.add(new Musica(thisId, thisTitle, thisArtist)); // colocar info album
            }
            while (musicCursor.moveToNext());
        }
        return lista;

    }

    public ArrayList<Artista> getArtistList() {
        ArrayList<Artista>listaArt = new ArrayList<>();
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            int colunaArtista = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            //add artista na lista
            do {
                String thisArtist = musicCursor.getString(colunaArtista);
                //colocar info album
                listaArt.add(new Artista(thisArtist));
            }
            while (musicCursor.moveToNext());
        }
        return listaArt;
    }



    private Bitmap getAlbumImage(String path) {
        android.media.MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        byte[] data = mmr.getEmbeddedPicture();
        if (data != null) return BitmapFactory.decodeByteArray(data, 0, data.length);
        return null;
    }



    public void songPicked(View view){
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //menu item selected
        switch (item.getItemId()) {
            case R.id.action_shuffle:
                musicSrv.setShuffle();
                break;
            case R.id.action_end:
                stopService(playIntent);
                musicSrv=null;
                System.exit(0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv=null;
        super.onDestroy();
    }

    private void playPrev(){
        musicSrv.playPrev();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
        //   play.setImageResource(R.drawable.rewind);

    }

    @Override
    public boolean canPause() {
        return true;
    }
    @Override
    public boolean canSeekBackward() {
        return true;
    }
    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if(musicSrv!=null &&  musicBound && musicSrv.isPng())
            return musicSrv.getPosn();
        else return 0;
    }
    @Override
    public int getDuration() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
            return musicSrv.getDur();
        else return 0;
    }
    @Override
    public boolean isPlaying() {
        if(musicSrv!=null && musicBound)
            return musicSrv.isPng();
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public void pause() {
        playbackPaused=true;
        musicSrv.pausePlayer();
    }
    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);
    }
    @Override
    public void start() {
        musicSrv.go();
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}