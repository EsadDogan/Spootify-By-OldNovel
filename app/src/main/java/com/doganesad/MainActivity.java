package com.doganesad;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "main activity";
    public static CardView littleCard,bigCard;
    public BottomNavigationView bottomNavigationView;
    public static  Button btndownArrowBigcard,btnLittlecardPlayPause ;
    public Button btnplayNextSongBC,btnplayPreviousSongBC;
    public View viewReplay,viewShuffle,btnLike;
    public static Button btnBigCardPlayPause;
    public static boolean replay = false;
    public static boolean shuffle = false;
    public static boolean like = false;
    public static boolean isDataDownloaded = false;
    FragmentManager manager;
    public static ArrayList<Music> musics;
    public static ArrayList<Music> shuffleMusic;
    public static ArrayList<Playlists> playlists;
    public static MediaPlayer mediaPlayer =new MediaPlayer();
    public static TextView txtSongNameLittleCard,txtSongNameBigCard,txtArtistNameLittleCard,txtArtistNameBigCard;
    public static ImageView imageViewBC,imageViewLC;
    public static FrameLayout frameLayout;
    public static SeekBar seekBar;
    public static TextView textClock;
    static Context context;
    private GifImageView progressBar;

    public static ArrayList<Playlists> playlistsUniclyYours;
    public static ArrayList<Playlists> playlistsBestOfArtists;
    public static ArrayList<Playlists> playlistsGlobals;
    public static ArrayList<Playlists> playlistsSpotifyPlaylists;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction()
                .replace(R.id.myFrameLay, new loading(), loading.TAG)
                .addToBackStack(null)
                .commit();


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        progressBar = findViewById(R.id.progressbarMain);
        context = MainActivity.this;
        frameLayout = findViewById(R.id.myFrameLay);
//        int SPLASH_TIME_OUT = 3;
//         new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                bottomNavigationView.setVisibility(View.GONE);
//                manager = getSupportFragmentManager();
//                manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                manager.beginTransaction()
//                        .replace(R.id.myFrameLay, new loading(), loading.TAG)
//                        .addToBackStack(null)
//                        .commit();
//
//
//            }
//        }, 3);




        // go to hame fragment when its first time opened
        playlists = new ArrayList<>();






        littleCard = findViewById(R.id.littleCard);
        bigCard = findViewById(R.id.BigCard);
        btndownArrowBigcard =findViewById(R.id.downButtonBigCard);
        btnLittlecardPlayPause=findViewById(R.id.btnPlayPauseLittleCard);
        btnBigCardPlayPause =findViewById(R.id.btnPlayPauseBigCard);
        btnLike = findViewById(R.id.btnLikeBigCard);
        viewReplay = findViewById(R.id.btnReplay);
        viewShuffle = findViewById(R.id.btnShufflePlay);
        playlistsUniclyYours = new ArrayList<>();
        playlistsBestOfArtists = new ArrayList<>();
        playlistsGlobals = new ArrayList<>();
        playlistsSpotifyPlaylists = new ArrayList<>();

        seekBar = findViewById(R.id.seekBarBigCard);

        //inside cards
        txtArtistNameLittleCard =findViewById(R.id.txtArtistNameLittleCard);
        txtArtistNameBigCard = findViewById(R.id.txtArtistNameBigCard);
        txtSongNameBigCard = findViewById(R.id.txtSongNameBigCard);
        txtSongNameLittleCard = findViewById(R.id.txtSongNameLittleCard);
        imageViewBC = findViewById(R.id.imageViewBigCard);
        imageViewLC = findViewById(R.id.imageViewLittleCard);
        btnplayNextSongBC = findViewById(R.id.btnNextSong);
        btnplayPreviousSongBC = findViewById(R.id.btnPrevSong);
        textClock = findViewById(R.id.textclock);

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);






        musics = new ArrayList<>();
        shuffleMusic = new ArrayList<>();


        try {
            getPlaylistFromDatabase();
            getDataFromFirebase();

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.myFrameLay, new home())
                            .commitAllowingStateLoss();


                }
            };
            timer.schedule(timerTask,2000);

        }catch (Exception e){
            Log.d(TAG, "onCreate: error" + e.getMessage());
        }








        btnLittlecardPlayPause.setOnClickListener(view -> playPause());

        btnBigCardPlayPause.setOnClickListener(view -> playPause());




        viewReplay.setOnClickListener(view -> {
            if (!replay){
                viewReplay.setBackgroundResource(R.drawable.replay);
                replay = true;
            }
            else if (replay){
                viewReplay.setBackgroundResource(R.drawable.replaywhite);
                replay =false;
            }
        });

        viewShuffle.setOnClickListener(view -> {
            if (!shuffle){
                viewShuffle.setBackgroundResource(R.drawable.shufflegreen);
                shuffle = true;
            }
            else if (shuffle){
                viewShuffle.setBackgroundResource(R.drawable.shufflewhite);
                shuffle =false;
            }
        });

        btnLike.setOnClickListener(view -> {
            if (like){
                btnLike.setBackgroundResource(R.drawable.like_icon);
                like = false;
            }else {
                btnLike.setBackgroundResource(R.drawable.likeicongreen);
                like = true;
            }
        });

        // TODO: 3.02.2023 make revision for this part
        
        btnplayNextSongBC.setOnClickListener(view -> {

            try {
                if (!shuffle){
                    playNextSong(musics.get(SongRecylerviewAdapter.currentMusicPosition + 1));
                    if (SongRecylerviewAdapter.currentMusicPosition < musics.size() -3 ) {
                        SongRecylerviewAdapter.currentMusicPosition += 1;
                    }
                }else {
                    playshuffle();
                }

                //diactivation of replay
                viewReplay.setBackgroundResource(R.drawable.replaywhite);
                replay =false;


            } catch (Exception e) {
                Log.d(TAG, "onCreate: error probably out of bound" + e.getMessage());
               }

        });

        btnplayPreviousSongBC.setOnClickListener(view -> {

            try {
                if (!shuffle){
                    playPreviousSong(musics.get(SongRecylerviewAdapter.currentMusicPosition-1));
                    if (SongRecylerviewAdapter.currentMusicPosition > 0) {
                        SongRecylerviewAdapter.currentMusicPosition -= 1;
                    }
                }else {
                    playshuffle();
                }

                //diactivation of replay
                viewReplay.setBackgroundResource(R.drawable.replaywhite);
                replay =false;


            }catch (Exception e){
                Log.d(TAG, "onCreate: error probably out of bound " + e.getMessage());
            }

        });

        // TODO: 3.02.2023 enable repeat button
        // TODO: 12.02.2023 add repeat part









        littleCard.setOnClickListener(view -> {

            littleCard.setVisibility(View.INVISIBLE);
            frameLayout.setVisibility(View.INVISIBLE);
            bigCard.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.INVISIBLE);

        });

        btndownArrowBigcard.setOnClickListener(view -> {

            bigCard.setVisibility(View.INVISIBLE);
            littleCard.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.VISIBLE);
        });


        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:

                   goHome();

                    break;

                case R.id.search:

                   goSearch();

                    break;


                case R.id.library:


                    goLibrary();
                    break;

                default:break;
            }


            return true;
        });



        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int totalDuration = mediaPlayer.getDuration();
                    int seekPos = (int) (((float) progress / 100) * totalDuration);
                    mediaPlayer.seekTo(seekPos);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });




    }

    private  void goHome(){


        manager = getSupportFragmentManager();
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction()
                .replace(R.id.myFrameLay, new home(), home.TAG)
                .addToBackStack(null)
                .commit();

    }

    private void goSearch(){

        manager = getSupportFragmentManager();
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction()
                .replace(R.id.myFrameLay, new search(), search.TAG)
                .addToBackStack(null)
                .commit();

    }


    private void goLibrary(){


        manager = getSupportFragmentManager();
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction()
                .replace(R.id.myFrameLay, new library(), library.TAG)
                .addToBackStack(null)
                .commit();

    }



    public void playPause(){

        try {
            if (mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                btnBigCardPlayPause.setBackgroundResource(R.drawable.playcirclebtn);
                btnLittlecardPlayPause.setBackgroundResource(R.drawable.playarrowbtn);
            }
            else {
                mediaPlayer.start();
                btnBigCardPlayPause.setBackgroundResource(R.drawable.circlepausebutton);
                btnLittlecardPlayPause.setBackgroundResource(R.drawable.pausearrowbtn);
            }
        }catch (Exception e){
            Log.d(TAG, "playPause: "+e.getMessage());
        }


    }

    public static void setCards(Music music){

        try{

            //little card
            txtSongNameLittleCard.setText(music.getSongName());
            txtArtistNameLittleCard.setText(music.getArtistName());
            btnLittlecardPlayPause.setBackgroundResource(R.drawable.pausearrowbtn);
            Glide.with(frameLayout).load(music.getcoverUrl()).into(imageViewLC);



            //Big Card
            txtSongNameBigCard.setText(music.getSongName());
            txtArtistNameBigCard.setText(music.getArtistName());
            btnBigCardPlayPause.setBackgroundResource(R.drawable.circlepausebutton);
            Glide.with(frameLayout).load(music.getcoverUrl()).into(imageViewBC);
        }catch (Exception e ){
            Log.d(TAG, "setCards: " + e.getMessage());
        }






    }


    public void getPlaylistFromDatabase(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Playlists").orderBy("playlistID").get().addOnCompleteListener(task -> {

            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()){

                    String coverURL = document.getString("coverURL");
                    String playlistName= document.getString("playlistName");
                    String playlistID= document.getString("playlistID");
                    int recViewID = document.getDouble("recViewID").intValue();

                    Log.d(TAG, "getDataFromFirebase: "+ playlistName);

                    playlists.add(new Playlists(playlistName,coverURL,playlistID,recViewID));

                }
                Log.d(TAG, "onSuccess: veri geldi");


                insertCardDatas();
                isDataDownloaded = true;
                bottomNavigationView.setVisibility(View.VISIBLE);


            }else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }


        });


    }

    public void insertCardDatas(){



        for (Playlists playlist : MainActivity.playlists) {

            switch(playlist.getRecViewID()){
                case 1 : playlistsUniclyYours.add(playlist); break;
                case 2: playlistsBestOfArtists.add(playlist); break;
                case 3: playlistsGlobals.add(playlist); break;
                case 4: playlistsSpotifyPlaylists.add(playlist); break;
                default: break;
            }

        }





        //**MANUAL TESTING**

//        // Unicly yours recyclerview
//        playlistsUniclyYours.add(new Playlists("Liked Songs","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_liked_songs.png?alt=media&token=02ce5b62-2224-4413-99b9-b09415118ba4"));
//        playlistsUniclyYours.add(new Playlists("On Repeat","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_on_repeat.jpg?alt=media&token=b79f3795-e41e-4a73-ba73-8f74c55b7146"));
//        playlistsUniclyYours.add(new Playlists("Time Capsule","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_time_capsule.jpg?alt=media&token=1207c6ac-06b5-467d-9241-1e741375461a"));
//        playlistsUniclyYours.add(new Playlists("Repeat Rewind","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_repeat_rewind.jpg?alt=media&token=585aad9f-a18a-4fe7-ab47-c840dbc42ded"));
//
//
//        // Best of artists recyclerview
//        playlistsBestOfArtists.add(new Playlists("This is Yüzyüzeyken Konuşuruz","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_yyk2.jpg?alt=media&token=0c39f556-b296-43db-b035-4da7d5a736c3"));
//        playlistsBestOfArtists.add(new Playlists("This is Dolu Kadehi Ters Tut","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_thisis_dktt.png?alt=media&token=0c9046e9-985f-4b4a-9def-9622b01d7ae9"));
//        playlistsBestOfArtists.add(new Playlists("This is Sezen Aksu","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_thisis_sezenaksu.jpg?alt=media&token=bf352d63-e6b6-434a-80a2-2270589d3aa0"));
//        playlistsBestOfArtists.add(new Playlists("This is Madrigal","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_thisis_madrigal.jpg?alt=media&token=6d6cd41b-cfce-4e61-a91b-445b9913a064"));
//        playlistsBestOfArtists.add(new Playlists("This is Dua Lipa","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_thisis_dualipa.jpeg?alt=media&token=8acca474-db2a-477e-9867-0836b20d717c"));
//
//
//        // 100%global recyclerview
//        playlistsGlobals.add(new Playlists("Viral Hits","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_viralhits.jpeg?alt=media&token=0cc0829d-08ea-4e7e-b800-75914ebffc5d"));
//        playlistsGlobals.add(new Playlists("Just Hits","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_justhits.jpeg?alt=media&token=27cf90a2-2224-426e-869f-7c457358dc75"));
//        playlistsGlobals.add(new Playlists("Pop Sauce","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_popsouce.jpeg?alt=media&token=5c9ba958-bd00-45fe-9635-8901c99c0a69"));
//        playlistsGlobals.add(new Playlists("Üçüncü Yeniler","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_ucuncuyeniler.jpeg?alt=media&token=dacdeb16-c23d-461c-b25d-d8e4f6d1a6ce"));
//        playlistsGlobals.add(new Playlists("Akustik Kuşağı","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_akustikkusagi.jpeg?alt=media&token=015b0fd8-c7f7-4ddd-8d51-2dc0456a8d67"));
//
//
//        // SpotifyPlAylists recyclerview
//        playlistsSpotifyPlaylists.add(new Playlists("All Out 10's","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylists_allout10s.jpg?alt=media&token=e2085b56-5b21-4bed-b034-784bddab3d5d"));
//        playlistsSpotifyPlaylists.add(new Playlists("Chill Hits","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylists_chillhits.jpeg?alt=media&token=7b81c280-aa9e-42dd-9ff2-f42fdcb58d01"));
//        playlistsSpotifyPlaylists.add(new Playlists("Mega Hit Mix","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylists_megahitmix.jpeg?alt=media&token=5b319fce-eae0-4418-8afc-f798be000f56"));
//        playlistsSpotifyPlaylists.add(new Playlists("Rap Caviar","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylists_rapcaviar.jpeg?alt=media&token=85001ddf-c023-4751-8cf3-5d0025251a76"));
//        playlistsSpotifyPlaylists.add(new Playlists("Your Top Songs 2020","https://firebasestorage.googleapis.com/v0/b/spootify-by-oldnovel.appspot.com/o/playlists%2Fplaylist_yourtopsongs.jpg?alt=media&token=b3f1d319-d0cd-4b0c-ba87-54d21a80792a"));

    }

    public void getDataFromFirebase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Songs").orderBy("songName").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot dc : task.getResult()){

                    String songName = dc.getString("songName");
                    String artistName = dc.getString("artistName");
                    String coverUrl = dc.getString("coverUrl");
                    String songUrl = dc.getString("songUrl");
                    int musicId = Integer.parseInt(dc.getId());
                    Log.d(TAG, "getDataFromFirebase: "+ musicId);

                    musics.add(new Music(songName,artistName,songUrl,coverUrl,musicId));
                    shuffleMusic.add(new Music(songName,artistName,songUrl,coverUrl,musicId));


                }

                musics.add(new Music("","","","",-1));
                musics.add(new Music("","","","",-1));
                Log.d(TAG, "onSuccess: veri geldi");

            }else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }

        });




    }

    public void playNextSong(Music music){

        if (music != null){

            try {

                if (music.getMusicId() > 0)
                {

                        MainActivity.mediaPlayer.reset();
                        MainActivity.mediaPlayer.release();
                        MainActivity.mediaPlayer = null;

                        mediaPlayer = MediaPlayer.create(this, Uri.parse(music.getMusicUrl()));
                        mediaPlayer.start();
                        setCards(music);
                        littleCard.setVisibility(View.VISIBLE);
                        updateSeekBar(mediaPlayer);


                }else {
                    // TODO: 3.02.2023 show snackbar
                }


            }catch (Exception e){
                Log.d(TAG, "playNextSong: error" + e.getMessage());
            }

        }


    }

    public void playPreviousSong(Music music){



            if (music != null){

                if (music.getMusicId() >0){

                try {
                        MainActivity.mediaPlayer.reset();
                        MainActivity.mediaPlayer.release();
                        MainActivity.mediaPlayer = null;

                        mediaPlayer = MediaPlayer.create(this, Uri.parse(music.getMusicUrl()));
                        mediaPlayer.start();
                        setCards(music);
                        littleCard.setVisibility(View.VISIBLE);
                        updateSeekBar(mediaPlayer);

                }catch (Exception e){
                    Log.d(TAG, "playNextSong: error" + e.getMessage());
                }

              }  else {

                    // TODO: 3.02.2023 show snackbar
                }


        }



    }



    public static void updateSeekBar(MediaPlayer mediaPlayer1) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mediaPlayer1!= null){
                        if (mediaPlayer1.isPlaying()) {
                            int currentPos = mediaPlayer1.getCurrentPosition();
                            int totalDuration = mediaPlayer1.getDuration();
                            int totalDurationSecond = totalDuration / 1000;
                            int totalDurationMinute = totalDuration / 60000;

                            int durationMinutes = currentPos / 60000;
                            int durationInSeconds = currentPos / 1000;
                            int progress = (int) (((float) currentPos / totalDuration) * 100);
                            seekBar.setProgress(progress);

                            if (durationInSeconds >= 60 ){
                                durationInSeconds = currentPos/1000 - durationMinutes*60;
                            }
                            if(durationInSeconds < 10){
                                textClock.setText(durationMinutes+":0"+durationInSeconds);
                            }else {
                                textClock.setText(durationMinutes+":"+durationInSeconds);
                            }
                        }

                        MainActivity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {

                                if (!replay){

                                    if (musics.get(SongRecylerviewAdapter.currentMusicPosition+1).getMusicId()>0){
                                        try {
                                            MainActivity.mediaPlayer.reset();
                                            MainActivity.mediaPlayer.release();
                                            MainActivity.mediaPlayer = null;

                                            MainActivity.setCards(musics.get((SongRecylerviewAdapter.currentMusicPosition+1)));
                                            MainActivity.mediaPlayer = MediaPlayer.create(context, Uri.parse(musics.get(SongRecylerviewAdapter.currentMusicPosition+1).getMusicUrl()));
                                            MainActivity.mediaPlayer.start();
                                            MainActivity.updateSeekBar(MainActivity.mediaPlayer);
                                            SongRecylerviewAdapter.currentMusicPosition+=1;

                                        }catch (Exception e){
                                            Log.d(TAG, "onCompletion: error "+e.getMessage());
                                        }
                                    }else {
                                        btnBigCardPlayPause.setBackgroundResource(R.drawable.playcirclebtn);
                                        btnLittlecardPlayPause.setBackgroundResource(R.drawable.playarrowbtn);
                                    }

                                }
                                if (replay){

                                    //MainActivity.mediaPlayer = MediaPlayer.create(context, Uri.parse(musics.get(SongRecylerviewAdapter.currentMusicPosition).getMusicUrl()));
                                    MainActivity.mediaPlayer.start();
                                    MainActivity.updateSeekBar(MainActivity.mediaPlayer);
                                }

                                else if (shuffle){

                                    playshuffle();

                                }


                            }
                        });

                    }
                }catch (Exception e){
                   // Log.d(TAG, "run: error "+e.getMessage());
                }

                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }




    public static void showMustGoOn(String url){




        if (MainActivity.mediaPlayer == null){

            MainActivity.mediaPlayer = MediaPlayer.create(context, Uri.parse(url));
            MainActivity.mediaPlayer.start();
            MainActivity.littleCard.setVisibility(View.VISIBLE);
        }
        else {

            try {
                MainActivity.mediaPlayer.reset();
                MainActivity.mediaPlayer.release();
                MainActivity.mediaPlayer = null;

                MainActivity.mediaPlayer = MediaPlayer.create(context, Uri.parse(url));
                MainActivity.mediaPlayer.start();
                MainActivity.littleCard.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public static void playshuffle(){



        Random random = new Random();
        int shuffleNumber = random.nextInt(shuffleMusic.size());


            MainActivity.mediaPlayer.reset();
            MainActivity.mediaPlayer.release();
            MainActivity.mediaPlayer = null;

            MainActivity.setCards(musics.get(shuffleNumber));
            MainActivity.mediaPlayer = MediaPlayer.create(context, Uri.parse(shuffleMusic.get(shuffleNumber).getMusicUrl()));
            MainActivity.mediaPlayer.start();
            MainActivity.updateSeekBar(MainActivity.mediaPlayer);
            SongRecylerviewAdapter.currentMusicPosition=shuffleNumber;



    }


}