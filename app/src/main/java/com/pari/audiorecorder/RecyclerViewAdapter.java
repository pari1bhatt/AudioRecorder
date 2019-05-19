package com.pari.audiorecorder;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyiewHolder> {
    private Context context;
    private List<AudioPOJO> audioList;
    MediaPlayer mediaPlayer;
    boolean check;


    public RecyclerViewAdapter(Context context, List<AudioPOJO> audioList) {
        this.context = context;
        this.audioList = audioList;

    }

    class MyiewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        ImageView ivPlay,ivStop,ivPause;

        public MyiewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            ivPlay = itemView.findViewById(R.id.iv_play);
            ivStop = itemView.findViewById(R.id.iv_stop);
            ivPause = itemView.findViewById(R.id.iv_pause);


            ivPlay.setOnClickListener(this);
            ivStop.setOnClickListener(this);
            ivPause.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.iv_play:
                    int position = getAdapterPosition();
                    mediaPlayer = new MediaPlayer();

                        try {
                            mediaPlayer.setDataSource(audioList.get(position).getPath());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            Toast.makeText(context, "started", Toast.LENGTH_SHORT).show();

                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                                    mediaPlayer.release();
                                    mediaPlayer = null;
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    break;

                case R.id.iv_pause:
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        Toast.makeText(context, "Paused", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case R.id.iv_stop:
                      if (mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        ivPlay.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                        Toast.makeText(context,"Stopped",Toast.LENGTH_SHORT).show();
                        mediaPlayer = null;
                    }
                    break;
            }
        }
    }

    @NonNull
    @Override
    public MyiewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myView = LayoutInflater.from(context).inflate(R.layout.recycler_view_row,viewGroup,false);
        return new MyiewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyiewHolder myiewHolder,int i) {
        setUpData(myiewHolder,i);
        myiewHolder.tvName.setText(audioList.get(i).getName());

       /* if( mediaPlayer.isPlaying() ){
            myiewHolder.ivPlay.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);

        }else{
            myiewHolder.ivPlay.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);

        }*/

    }
    private void setUpData(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }
}
