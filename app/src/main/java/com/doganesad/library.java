package com.doganesad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link library#newInstance} factory method to
 * create an instance of this fragment.
 */
public class library extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String  TAG = "LIBRARY FRAGMENT";

    public PlaylistCardRecViewAdapter adapterYourLibrary;

    private ArrayList<Playlists> playlistYourLibrary;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public library() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment library.
     */
    // TODO: Rename and change types and number of parameters
    public static library newInstance(String param1, String param2) {
        library fragment = new library();
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
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize the recyview and playlist
        RecyclerView recyclerView = view.findViewById(R.id.recyviewYourLibrary);
        playlistYourLibrary = new ArrayList<>();
        insertCardDatas();

        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapterYourLibrary = new PlaylistCardRecViewAdapter(getContext(),MainActivity.playlists);
        recyclerView.setAdapter(adapterYourLibrary);


    }

    private void insertCardDatas(){

//        playlistYourLibrary.add(new Playlists("Liked Songs","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_liked_songs.png?alt=media&token=02ce5b62-2224-4413-99b9-b09415118ba4"));
//        playlistYourLibrary.add(new Playlists("This is Yüzyüzeyken Konuşuruz","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_yyk2.jpg?alt=media&token=0c39f556-b296-43db-b035-4da7d5a736c3"));
//        playlistYourLibrary.add(new Playlists("Viral Hits","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_viralhits.jpeg?alt=media&token=0cc0829d-08ea-4e7e-b800-75914ebffc5d"));
//        playlistYourLibrary.add(new Playlists("All Out 10's","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylists_allout10s.jpg?alt=media&token=e2085b56-5b21-4bed-b034-784bddab3d5d"));
//        playlistYourLibrary.add(new Playlists("Pop Sauce","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_popsouce.jpeg?alt=media&token=5c9ba958-bd00-45fe-9635-8901c99c0a69"));
//        playlistYourLibrary.add(new Playlists("Üçüncü Yeniler","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_ucuncuyeniler.jpeg?alt=media&token=dacdeb16-c23d-461c-b25d-d8e4f6d1a6ce"));
//        playlistYourLibrary.add(new Playlists("Akustik Kuşağı","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_akustikkusagi.jpeg?alt=media&token=015b0fd8-c7f7-4ddd-8d51-2dc0456a8d67"));
//        playlistYourLibrary.add(new Playlists("This is Sezen Aksu","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_thisis_sezenaksu.jpg?alt=media&token=bf352d63-e6b6-434a-80a2-2270589d3aa0"));
//        playlistYourLibrary.add(new Playlists("This is Madrigal","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_thisis_madrigal.jpg?alt=media&token=6d6cd41b-cfce-4e61-a91b-445b9913a064"));
//        playlistYourLibrary.add(new Playlists("This is Dua Lipa","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_thisis_dualipa.jpeg?alt=media&token=8acca474-db2a-477e-9867-0836b20d717c"));
//        playlistYourLibrary.add(new Playlists("Time Capsule","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_time_capsule.jpg?alt=media&token=1207c6ac-06b5-467d-9241-1e741375461a"));
//        playlistYourLibrary.add(new Playlists("Repeat Rewind","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_repeat_rewind.jpg?alt=media&token=585aad9f-a18a-4fe7-ab47-c840dbc42ded"));





    }


}