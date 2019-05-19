package com.pari.audiorecorder;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyAudios extends AppCompatActivity {

    private List<AudioPOJO> myAudio;
    private RecyclerViewAdapter myAdapter;
    private RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_audios);
        myRecyclerView = findViewById(R.id.recycler_view);

        getFiles();

        //Setting up the Recycler View
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new RecyclerViewAdapter(this,myAudio);
        myRecyclerView.setAdapter(myAdapter);
    }


    //getting all audio files from the path and setting it to POJOclass
    public void getFiles(){
        myAudio = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Music";
        //Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        if (files == null || files.length <= 0){
            Toast.makeText(getApplicationContext(),"There is no file in the given directory",Toast.LENGTH_SHORT).show();
        }else {
            for (int i = 0; i < files.length; i++) {
                //Log.d("Files", "FileName: " + files[i].getName());
                myAudio.add(new AudioPOJO(path + "/" + files[i].getName(), files[i].getName()));
                //Log.d("Files", "FileName: " + myAudio.get(i).getPath());
            }
        }

    }

}
