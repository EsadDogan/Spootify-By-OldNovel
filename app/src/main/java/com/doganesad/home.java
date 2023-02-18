package com.doganesad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;


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
    public  PlaylistCardRecViewAdapter adapterGlobals;
    public  PlaylistCardRecViewAdapter adapterSpotifyPlaylists;

    private ArrayList<Playlists> playlistsUniclyYours;
    private ArrayList<Playlists> playlistsBestOfArtists;
    private ArrayList<Playlists> playlistsGlobals;
    private ArrayList<Playlists> playlistsSpotifyPlaylists;

    private FirebaseAuth mAuth;
    private TextView txtGreeetings;

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

//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Get the current time of day
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        Log.d(TAG, "Hourofday:  "+timeOfDay);

       // Set the greeting based on the time of day
        String greeting;
        if (timeOfDay >= 4 && timeOfDay < 12) {
            greeting = "Good morning";
        } else if (timeOfDay >= 12 && timeOfDay < 18) {
            greeting = "Good afternoon";
        } else if(timeOfDay >= 18 && timeOfDay < 23) {
            greeting = "Good evening";
        }else {
            greeting = "Good night";
        }

        txtGreeetings = view.findViewById(R.id.txtGreeetings);
        // Setting the greeting text based on the time of day
        try {
            txtGreeetings.setText(greeting);
        }catch (Exception e){
            Log.d(TAG, "onViewCreated: "+e.getMessage());
        }











        //Playlist RecyViews

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);

        playlistsUniclyYours = new ArrayList<>();
        playlistsBestOfArtists = new ArrayList<>();
        playlistsGlobals = new ArrayList<>();
        playlistsSpotifyPlaylists = new ArrayList<>();
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

        // Moods recyclerview
        RecyclerView recyclerViewMoods = view.findViewById(R.id.homeRecViewMoods);
        recyclerViewMoods.setLayoutManager(layoutManager3);
        adapterGlobals = new PlaylistCardRecViewAdapter(getContext(),playlistsGlobals);
        recyclerViewMoods.setAdapter(adapterGlobals);

        // SpotifyPlayists recyclerview
        RecyclerView recyclerViewSpotifyPlayists = view.findViewById(R.id.homeRecViewSpotifyPlaylists);
        recyclerViewSpotifyPlayists.setLayoutManager(layoutManager4);
        adapterSpotifyPlaylists = new PlaylistCardRecViewAdapter(getContext(),playlistsSpotifyPlaylists);
        recyclerViewSpotifyPlayists.setAdapter(adapterSpotifyPlaylists);





    }

    private void insertCardDatas(){

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


        // 100%global recyclerview
        playlistsGlobals.add(new Playlists("Viral Hits","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_viralhits.jpeg?alt=media&token=0cc0829d-08ea-4e7e-b800-75914ebffc5d"));
        playlistsGlobals.add(new Playlists("Just Hits","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_justhits.jpeg?alt=media&token=27cf90a2-2224-426e-869f-7c457358dc75"));
        playlistsGlobals.add(new Playlists("Pop Sauce","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_popsouce.jpeg?alt=media&token=5c9ba958-bd00-45fe-9635-8901c99c0a69"));
        playlistsGlobals.add(new Playlists("Üçüncü Yeniler","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_ucuncuyeniler.jpeg?alt=media&token=dacdeb16-c23d-461c-b25d-d8e4f6d1a6ce"));
        playlistsGlobals.add(new Playlists("Akustik Kuşağı","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_akustikkusagi.jpeg?alt=media&token=015b0fd8-c7f7-4ddd-8d51-2dc0456a8d67"));


        // SpotifyPlAylists recyclerview
        playlistsSpotifyPlaylists.add(new Playlists("All Out 10's","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylists_allout10s.jpg?alt=media&token=e2085b56-5b21-4bed-b034-784bddab3d5d"));
        playlistsSpotifyPlaylists.add(new Playlists("Chill Hits","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylists_chillhits.jpeg?alt=media&token=7b81c280-aa9e-42dd-9ff2-f42fdcb58d01"));
        playlistsSpotifyPlaylists.add(new Playlists("Mega Hit Mix","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylists_megahitmix.jpeg?alt=media&token=5b319fce-eae0-4418-8afc-f798be000f56"));
        playlistsSpotifyPlaylists.add(new Playlists("Rap Caviar","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylists_rapcaviar.jpeg?alt=media&token=85001ddf-c023-4751-8cf3-5d0025251a76"));
        playlistsSpotifyPlaylists.add(new Playlists("Your Top Songs 2020","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_yourtopsongs.jpg?alt=media&token=b3f1d319-d0cd-4b0c-ba87-54d21a80792a"));

    }
}