package com.example.autiobook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.autiobook.models.Audio;

import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";
    TextView tvName;
    ImageView btnPlay;
    SeekBar seekBar;

    Audio audioBook;
    MediaPlayer mediaPlayer;
    Runnable runnable;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = findViewById(R.id.tvName);
        btnPlay = findViewById(R.id.btnPlay);
        seekBar = findViewById(R.id.seekBar);

        //get Audio object
        audioBook = Parcels.unwrap(getIntent().getParcelableExtra("audioBook"));

        //set title
        tvName.setText(audioBook.getName());

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                btnPlay.setImageResource(R.drawable.ic);
                playSong();
            }
        });

        Uri uri = Uri.fromFile(audioBook.getFile());
        mediaPlayer = mediaPlayer.create(getApplicationContext(), uri);
        handler = new Handler();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void playSong(){
//        Uri uri = Uri.fromFile(audioBook.getFile());
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mediaPlayer.reset();

        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration());
        updateSeekBar();

//        try {
//            mediaPlayer.setDataSource(DetailActivity.this, uri);
//        }catch (Exception e){e.printStackTrace();}
    }

    private void updateSeekBar() {
        //set seek bar to new pos
        int currPos = mediaPlayer.getCurrentPosition();
        seekBar.setProgress(currPos);

        //fun to call updateSeekBar again
        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekBar();
            }
        };
        //call after 1000ms
        handler.postDelayed(runnable, 1000);
    }
}