package com.doganesad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String  TAG = "HOME FRAGMENT";

    public  PlaylistCardRecViewAdapter adapterUniqlyYours;
    public  PlaylistCardRecViewAdapter adapterBestOfArtists;
    public ArrayList<Playlists> playlistsUniclyYours;
    public ArrayList<Playlists> playlistsBestOfArtists;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        playlistsUniclyYours = new ArrayList<>();
        playlistsBestOfArtists = new ArrayList<>();
        insertCardDatas();

        // Unicly yours recyclerview
        RecyclerView recyclerViewUniclyYours = view.findViewById(R.id.homeRecViewUniqelyYours);
        recyclerViewUniclyYours.setLayoutManager(layoutManager);
        adapterUniqlyYours = new PlaylistCardRecViewAdapter(getContext(),playlistsUniclyYours);
        recyclerViewUniclyYours.setAdapter(adapterUniqlyYours);

        // Best of artists recyclerview
        RecyclerView recyclerViewBestOfArtists = view.findViewById(R.id.homeRecViewBestOfArtists);
        recyclerViewBestOfArtists.setLayoutManager(layoutManager2);
        adapterBestOfArtists = new PlaylistCardRecViewAdapter(getContext(),playlistsBestOfArtists);
        recyclerViewBestOfArtists.setAdapter(adapterBestOfArtists);





    }

    public void insertCardDatas(){

        // Unicly yours recyclerview
        playlistsUniclyYours.add(new Playlists("Liked Songs","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_liked_songs.png?alt=media&token=02ce5b62-2224-4413-99b9-b09415118ba4"));
        playlistsUniclyYours.add(new Playlists("On Repeat","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_on_repeat.jpg?alt=media&token=b79f3795-e41e-4a73-ba73-8f74c55b7146"));
        playlistsUniclyYours.add(new Playlists("Time Capsule","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_time_capsule.jpg?alt=media&token=1207c6ac-06b5-467d-9241-1e741375461a"));
        playlistsUniclyYours.add(new Playlists("Repeat Rewind","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_repeat_rewind.jpg?alt=media&token=585aad9f-a18a-4fe7-ab47-c840dbc42ded"));


        // Best of artists recyclerview
        playlistsBestOfArtists.add(new Playlists("This is Yüzyüzeyken Konuşuruz","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_yyk2.jpg?alt=media&token=0c39f556-b296-43db-b035-4da7d5a736c3"));
        playlistsBestOfArtists.add(new Playlists("This is Dolu Kadehi Ters Tut","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_thisis_dktt.png?alt=media&token=0c9046e9-985f-4b4a-9def-9622b01d7ae9"));
        playlistsBestOfArtists.add(new Playlists("This is Sezen Aksu","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_thisis_sezenaksu.jpg?alt=media&token=bf352d63-e6b6-434a-80a2-2270589d3aa0"));
        playlistsBestOfArtists.add(new Playlists("This is Madrigal","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_thisis_madrigal.jpg?alt=media&token=6d6cd41b-cfce-4e61-a91b-445b9913a064"));
        playlistsBestOfArtists.add(new Playlists("This is Dua Lipa","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_thisis_dualipa.jpeg?alt=media&token=8acca474-db2a-477e-9867-0836b20d717c"));

    }
}