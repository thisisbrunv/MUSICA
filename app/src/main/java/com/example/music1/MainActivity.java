package com.example.music1;

import static android.app.job.JobInfo.PRIORITY_LOW;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.music1.MusicService.MusicBinder;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;



import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.music1.R;
import com.example.music1.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;


    // private ArrayList<Musica> listaMusicas;
    // private ListView musicaView;

    //private MusicService musicSrv;
    //  private Intent playIntent;
    // private boolean musicBound=false;
    // private MusicController controller;
    //  private boolean paused=false, playbackPaused=false;

    Button gotoArtists;
    Button gotoPlaylists;
    Button gotoAlbums;
    Button gotoMusics;
    Button gotoBanco;

    // public ArrayList<Musica> getListaMusicas() {
    //    return listaMusicas;
    //}

    private void pedirPermissoes() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    interfaceMusics.getListaMusicas();
                } else {
                    Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        pedirPermissoes();
        gotoArtists = findViewById(R.id.gotoArtists);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                 */
                                               Intent intent = new Intent(MainActivity.this, MusicaActivity.class);
                                               startActivity(intent);
                                           }
                                       });


       /* gotoArtists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, interfaceArtistas.class);
                startActivity(intent);
            }
        });*/

        gotoPlaylists =

                findViewById(R.id.gotoPlaylists);

        gotoPlaylists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, interfacePlaylists.class);
                startActivity(intent);
            }
        });

        gotoAlbums =

                findViewById(R.id.gotoAlbums);

        gotoAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, interfaceAlbums.class);
                startActivity(intent);
            }
        });

        gotoMusics = findViewById(R.id.gotoMusics);

        gotoMusics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, interfaceMusics.class);
                startActivity(intent);
            }


        });

        gotoBanco = findViewById(R.id.gotoBanco);

        gotoBanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditarMusicaActivity.class);
                startActivity(intent);
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
     /*   horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });*/
       // musicaView = (ListView) findViewById(R.id.lista_musicas);
       // listaMusicas = getSongList();
      //  Collections.sort(listaMusicas, new Comparator<Musica>() {
       //     public int compare(Musica a, Musica b) {
         //       return a.getNome().compareTo(b.getNome());
        //    }
      //  });
      //  SongAdapter songAdt = new SongAdapter(this, listaMusicas);
      //  musicaView.setAdapter(songAdt);
      //  setController();

     /*  manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // chamando as id

        play = findViewById(R.id.play);
        volta = findViewById(R.id.volta);
        next = findViewById(R.id.next);
        nomeMusica = findViewById(R.id.nomeMusica);
        // capa = findViewById(R.id.capa);
        tempo = findViewById(R.id.tempo);
        volume = findViewById(R.id.volume);
        repeat = findViewById(R.id.repeat);
        random = findViewById(R.id.random);

        // barrinha do volume

        int maxV = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curV = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volume.setMax(maxV);
        volume.setProgress(curV);

        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                manager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        tempo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    player.seekTo(progress);
                    tempo.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/


   // @Override
   // protected void onStart() {
   //     super.onStart();
    //    if(playIntent==null){
    //        playIntent = new Intent(this, MusicService.class);
     //       bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
      //      startService(playIntent);
     //   }
  //  }

    //connect to the service
/* private ServiceConnection musicConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder)service;
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
                long thisId = musicCursor.getLong(colunaId);
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

 */

