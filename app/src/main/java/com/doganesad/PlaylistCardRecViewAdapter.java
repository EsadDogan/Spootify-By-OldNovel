package com.doganesad;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class PlaylistCardRecViewAdapter extends RecyclerView.Adapter<PlaylistCardRecViewAdapter.MyViewHolder>{

    public final static String TAG = "adapterPllaylistCard";
    Context context;
    ArrayList<Playlists> playlists;
    public static Playlists playlistID;

    public PlaylistCardRecViewAdapter(Context context, ArrayList<Playlists> playlists) {
        this.context = context;
        this.playlists = playlists;
    }


    @NonNull
    @Override
    public PlaylistCardRecViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.playlistcardrecyview,parent, false);

        return new PlaylistCardRecViewAdapter.MyViewHolder(view);
    }



    @Override
    public int getItemCount() {
        if (playlists.isEmpty()){
            return 0;
        }else {
            return playlists.size();
        }
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            holder.textView.setText(playlists.get(position).getText());
            Glide.with(context).load(playlists.get(position).getImageUrl()).into(holder.imageView);
        }catch (Exception e){
            Log.d(TAG, "onBindViewHolder: Error"+e.getMessage());
        }


        holder.parent.setOnClickListener(view -> {

            playlistID = playlists.get(position);

            AppCompatActivity activity = (AppCompatActivity) view.getContext();


            FragmentManager manager = activity.getSupportFragmentManager();
            manager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.myFrameLay,new PlaylistFragment(),PlaylistFragment.TAG).addToBackStack(null).commit();


        });

    }




    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final ShapeableImageView imageView;
        private final TextView textView;
        private final ConstraintLayout parent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardRecyViewimageView);
            textView = itemView.findViewById(R.id.cardRecyText);
            parent= itemView.findViewById(R.id.parentrecviewplaylist);


        }

    }
}
