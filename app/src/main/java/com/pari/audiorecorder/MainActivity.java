package com.pari.audiorecorder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnStart, btnStop,btnAudioList;
    MediaRecorder mediaRecorder;
    String outputFile;
    private static final String LOG_TAG = "AudioRecording";
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        btnAudioList = findViewById(R.id.btn_audio_list);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnAudioList.setOnClickListener(this);
        btnStop.setEnabled(false);


        //Creating music Directory if not exists
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Music";
        File file = new File(path);
        if (!file.exists())
            file.mkdir();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_start:
                //checking permissions before recording starts
                if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) ||
                        (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                        (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                    return;
                }else {
                    mediaRecorder = new MediaRecorder();
                    outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/Recording"+ System.currentTimeMillis() +".3gp";
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    mediaRecorder.setOutputFile(outputFile);

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    btnStop.setEnabled(true);
                    btnStart.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                }
                break;


            case R.id.btn_stop:
                mediaRecorder.stop();
                //mediaRecorder.reset();
                mediaRecorder.release();
                mediaRecorder = null;
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Audio Recorder stopped", Toast.LENGTH_LONG).show();
                break;


            case R.id.btn_audio_list:
                Intent intent = new Intent(MainActivity.this,MyAudios.class);
                startActivity(intent);
                break;
        }
    }
}
