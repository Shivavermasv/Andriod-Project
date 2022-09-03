package com.example.mediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Button song;
    SeekBar bar;
    private SurfaceView videoview;
    private MediaPlayer myplayer;
    private MediaPlayer myplayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaPlayer myplayer=MediaPlayer.create(this,R.raw.ektarfa);
        song=findViewById(R.id.songbt);
        bar=findViewById(R.id.seekBar);
        myplayer.setOnPreparedListener(mp -> {
            bar.setMax(myplayer.getDuration());
            myplayer.start();
        });
        videoview=findViewById(R.id.surfaceView);
        myplayer2=MediaPlayer.create(this,R.raw.divu);
        myplayer2.setOnPreparedListener(mp -> {
            //bar.setMax(myplayer2.getDuration());
            myplayer2.start();
            song.setText("PAUSE");
        });
        videoview.setKeepScreenOn(true);
        SurfaceHolder holder=videoview.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                myplayer2.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    myplayer.seekTo(progress);
                    myplayer2.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myplayer.isPlaying()){
                    myplayer.pause();
                    myplayer2.pause();
                    Toast.makeText(MainActivity.this, "PAUSING", Toast.LENGTH_SHORT).show();
                    song.setText("PLAY");
                }
                else{
                    myplayer.start();
                    myplayer2.start();
                    Toast.makeText(MainActivity.this, "PLAYING", Toast.LENGTH_SHORT).show();
                    song.setText("PAUSE");
                }
            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                bar.setProgress(myplayer.getCurrentPosition());
            }
        },0,500);

    }

}