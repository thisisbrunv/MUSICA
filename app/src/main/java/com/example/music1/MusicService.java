package com.example.music1;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import java.util.ArrayList;
import android.content.ContentUris;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.PowerManager;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import java.util.ArrayList;


public class MusicService extends Service implements
MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    SeekBar tempo, volume;
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    private String songTitle= "";
    private static final int NOTIFY_ID=1;
    private boolean shuffle=false;
    private Random rand;


    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }
        //media player
        private MediaPlayer player;
        //lista musicas
        private ArrayList<Musica> musicas;
        //posição atual
        private int musicaPosn;


    private AudioManager manager;

    private final IBinder musicBind = new MusicBinder();

        public void onCreate(){
            super.onCreate();
            //SeekBar tempo = (SeekBar)findViewById(R.id.tempo);
           // SeekBar volume = (SeekBar)findViewById(R.id.volume);
//inicializa a posição
            musicaPosn=0;
//cria o player
            player = new MediaPlayer();
           // player = MediaPlayer.create(getApplicationContext(), );
            initMusicPlayer();
            rand=new Random();
            getPosn();

            manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


          //  int maxV = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
          //  int curV = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
           // volume.setMax(maxV);
            //volume.setProgress(curV);

            /*volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
            });*/
        }


  /* tempo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                player.seekTo(progress);
                tempo.setProgress(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        }*/

    public MediaPlayer getPlayer() {
        return player;
    }

    public SeekBar getSeekBarTempo(){
            return tempo;
        }

    public SeekBar getSeekBarVolume(){
        return volume;
    }

    public void setShuffle(){
        if(shuffle) shuffle=false;
        else shuffle=true;
    }
    public void playNext(){
        if(shuffle){
            int newSong = musicaPosn;
            while(newSong==musicaPosn){
                newSong=rand.nextInt(musicas.size());
            }
            musicaPosn=newSong;
        }
        else{
            musicaPosn++;
            if(musicaPosn>=musicas.size()) musicaPosn=0;
        }
        playSong();
    }
        public void initMusicPlayer(){
            //configurações do player
            player.setWakeMode(getApplicationContext(),
                    PowerManager.PARTIAL_WAKE_LOCK);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(this);
            player.setOnCompletionListener(this);
            player.setOnErrorListener(this);
        }

        public void setList(ArrayList<Musica> theMusicas){
            musicas=theMusicas;
        }
    @Override
    public void onCompletion(MediaPlayer mp) {
        if(player.getCurrentPosition()>0){
            mp.reset();
            playNext();
        }
    }
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //start playback
        mp.start();
       /* Intent notIntent = new Intent(this, MainActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
                notIntent, 0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.play)
                .setTicker(songTitle)
                .setOngoing(true)
                .setContentTitle("Playing").setContentText(songTitle);
        Notification not = builder.build();
        startForeground(NOTIFY_ID, not);*/
    }

    public class MusicBinder extends Binder {
            MusicService getService() {
                return MusicService.this;
            }
        }

    public void setSong(int songIndex){
        musicaPosn=songIndex;
    }

    public void playSong(){
        //toca a musica
        player.reset();
        //pega a musica
        Musica playSong = musicas.get(musicaPosn);

        songTitle=playSong.getNome();
//pega a id
        long currSong = playSong.getID();
//pega o caminho
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);
        try{
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        player.prepareAsync();

    }
    public int getPosn(){
        return player.getCurrentPosition();
    }
    public int getDur(){
        return player.getDuration();
    }
    public boolean isPng(){
        return player.isPlaying();
    }
    public void pausePlayer(){
        player.pause();
    }
    public void seek(int posn){
        player.seekTo(posn);
    }
    public void go(){
        player.start();
    }
    public void playPrev(){
        musicaPosn--;
        if(musicaPosn<0) musicaPosn=musicas.size()-1;
        playSong();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    }

