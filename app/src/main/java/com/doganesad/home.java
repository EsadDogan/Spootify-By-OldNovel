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
import android.widget.Switch;
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




        // Unicly yours recyclerview
        RecyclerView recyclerViewUniclyYours = view.findViewById(R.id.homeRecViewUniqelyYours);
        recyclerViewUniclyYours.setLayoutManager(layoutManager);
        adapterUniqlyYours = new PlaylistCardRecViewAdapter(getContext(),MainActivity.playlistsUniclyYours);
        recyclerViewUniclyYours.setAdapter(adapterUniqlyYours);

        // Best of artists recyclerview
        RecyclerView recyclerViewBestOfArtists = view.findViewById(R.id.homeRecViewBestOfArtists);
        recyclerViewBestOfArtists.setLayoutManager(layoutManager2);
        adapterBestOfArtists = new PlaylistCardRecViewAdapter(getContext(),MainActivity.playlistsBestOfArtists);
        recyclerViewBestOfArtists.setAdapter(adapterBestOfArtists);

        // Moods recyclerview
        RecyclerView recyclerViewMoods = view.findViewById(R.id.homeRecViewMoods);
        recyclerViewMoods.setLayoutManager(layoutManager3);
        adapterGlobals = new PlaylistCardRecViewAdapter(getContext(),MainActivity.playlistsGlobals);
        recyclerViewMoods.setAdapter(adapterGlobals);

        // SpotifyPlayists recyclerview
        RecyclerView recyclerViewSpotifyPlayists = view.findViewById(R.id.homeRecViewSpotifyPlaylists);
        recyclerViewSpotifyPlayists.setLayoutManager(layoutManager4);
        adapterSpotifyPlaylists = new PlaylistCardRecViewAdapter(getContext(),MainActivity.playlistsSpotifyPlaylists);
        recyclerViewSpotifyPlayists.setAdapter(adapterSpotifyPlaylists);


        adapterGlobals.notifyDataSetChanged();
        adapterBestOfArtists.notifyDataSetChanged();
        adapterSpotifyPlaylists.notifyDataSetChanged();
        adapterUniqlyYours.notifyDataSetChanged();




    }


}