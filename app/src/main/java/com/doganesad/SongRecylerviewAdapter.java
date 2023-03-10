package com.doganesad;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import kotlin.text.UStringsKt;

public class SongRecylerviewAdapter extends RecyclerView.Adapter<SongRecylerviewAdapter.MyViewHolder> {

    public final static String TAG = "adapter";
    Context context;
    ArrayList<Music> music;
    public static int currentMusicPosition;




    public SongRecylerviewAdapter(Context context, ArrayList<Music> music) {
        this.context = context;
        this.music = music;
    }

    @NonNull
    @Override
    public SongRecylerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // this is where you inflate your layout (giving a look to our rows)

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview,parent, false);

        return new SongRecylerviewAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SongRecylerviewAdapter.MyViewHolder holder, int position) {

        holder.txtSongName.setText(music.get(position).getSongName());
        holder.txtArtistName.setText(music.get(position).getArtistName());
        Glide.with(context).load(music.get(position).getcoverUrl()).into(holder.imageView);
        if (music.get(position).getMusicId() == -1){
            holder.btnLike.setVisibility(View.GONE);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    MainActivity.showMustGoOn(music.get(holder.getAdapterPosition()).getMusicUrl());
                    MainActivity.setCards(music.get(holder.getAdapterPosition()));
                    currentMusicPosition = holder.getAdapterPosition();
                    MainActivity.updateSeekBar(MainActivity.mediaPlayer);
                    // Glide.with(view).load(music.get(holder.getAdapterPosition()).getcoverUrl()).into(MainActivity.imageViewBC);
                    // Glide.with(view).load(music.get(holder.getAdapterPosition()).getcoverUrl()).into(MainActivity.imageViewLC);
                   // holder.txtSongName.setTextColor(Color.GREEN);

                }catch (Exception e){
                    Log.d(TAG, "onClick: error "+ e.getMessage());
                }


            }

        });







    }

    @Override
    public int getItemCount() {
        // Recyclerview just wants to know the number of items you want

        if (music.isEmpty()){return 0;}

        else {return music.size();}

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        // grabbing the views from our recyclerview.xml file
        // kinda like in the onCreate method

        ConstraintLayout layout;
        ImageView imageView;
        TextView txtSongName,txtArtistName;
        Button btnLike;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewRW);
            txtSongName = itemView.findViewById(R.id.txtSongNameRW);
            txtArtistName = itemView.findViewById(R.id.txtArtistNameRW);
            btnLike = itemView.findViewById(R.id.btnLikeRW);
            layout = itemView.findViewById(R.id.recyclerviewSearchParent);

        }
    }




//    public static void updateMusicStatus(ArrayList<Music> musics,Music newCurrentMusic){
//
//        try {
//            for (Music music: musics) {
//
//                if (Objects.equals(music.getMusicUrl(), newCurrentMusic.getMusicUrl())){
//
//                    currentMusic = newCurrentMusic;
//                    nextMusic = musics.get(musics.indexOf(newCurrentMusic)+1);
//                    previousMusic = musics.get(musics.indexOf(newCurrentMusic)-1);
//
//                }
//
//            }
//        }catch (Exception e){
//            Log.d(TAG, "updateMusicStatus: error" +e.getMessage());
//        }
//
//
//
//    }

}
