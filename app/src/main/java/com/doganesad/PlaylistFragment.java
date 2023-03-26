package com.doganesad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.xml.transform.dom.DOMLocator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaylistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "PLAYLIST FRAGMENT";

    ImageView imageView;
    TextView txtPlaylistName;
    RecyclerView recyclerView;
    public static ArrayList<Music> musicArrayList = new ArrayList<>();
    SongRecylerviewAdapter adapter;
    private Button btnBack;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlaylistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaylistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaylistFragment newInstance(String param1, String param2) {
        PlaylistFragment fragment = new PlaylistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recviewSongsOfPlaylistFragment);
        imageView = view.findViewById(R.id.imageViewPlaylistFragment);
        txtPlaylistName = view.findViewById(R.id.txtPlaylistNamePlaylistFragment);
        btnBack = view.findViewById(R.id.btnbackPlaylist);
        //playlistName= PlaylistCardRecViewAdapter.playlistName;
        musicArrayList = new ArrayList<>();

        getDataFromFirebase();
        getReadyPage();

        recyclerView.setOnClickListener(view12 -> {
            MainActivity.musics.equals(musicArrayList);
        });


        btnBack.setOnClickListener(view1 -> {

            FragmentManager manager = getParentFragmentManager();
            manager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.myFrameLay,new home(),home.TAG).addToBackStack(null).commit();
        });


    }



    public void getReadyPage(){

        Glide.with(this.getContext()).load(PlaylistCardRecViewAdapter.playlistID.getImageUrl()).into(imageView);
        txtPlaylistName.setText(PlaylistCardRecViewAdapter.playlistID.getText());

    }





    public void getDataFromFirebase(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Playlists").document(PlaylistCardRecViewAdapter.playlistID.getPlaylistID()).collection("Songs").get().addOnCompleteListener(task -> {

            if (task.isSuccessful()){
                for (QueryDocumentSnapshot dc: task.getResult() ) {

                    String songName = dc.getString("songName");
                    String artistName = dc.getString("artistName");
                    String coverUrl = dc.getString("coverUrl");
                    String songUrl = dc.getString("songUrl");
                    int musicId = Integer.parseInt(dc.getId());
                    Log.d(TAG, "getDataFromFirebase: "+ musicId);

                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    musicArrayList.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    Log.d(TAG, "getDataFromFirebase: songsuccess"+musicArrayList.get(0).getSongName());


                }

                adapter = new SongRecylerviewAdapter(getContext(),musicArrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setNestedScrollingEnabled(false);
                adapter.notifyDataSetChanged();
            }
        });


    }
}