package com.doganesad;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
    FragmentManager manager;
    public static ArrayList<Music> musics;
    public static ArrayList<Music> shuffleMusic;
    public static MediaPlayer mediaPlayer =new MediaPlayer();
    public static TextView txtSongNameLittleCard,txtSongNameBigCard,txtArtistNameLittleCard,txtArtistNameBigCard;
    public static ImageView imageViewBC,imageViewLC;
    public static FrameLayout frameLayout;
    public static SeekBar seekBar;
    public static TextView textClock;
    static Context context;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
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
        goHome();
        bottomNavigationView.setVisibility(View.VISIBLE);

        littleCard = findViewById(R.id.littleCard);
        bigCard = findViewById(R.id.BigCard);
        btndownArrowBigcard =findViewById(R.id.downButtonBigCard);
        btnLittlecardPlayPause=findViewById(R.id.btnPlayPauseLittleCard);
        btnBigCardPlayPause =findViewById(R.id.btnPlayPauseBigCard);
        btnLike = findViewById(R.id.btnLikeBigCard);
        viewReplay = findViewById(R.id.btnReplay);
        viewShuffle = findViewById(R.id.btnShufflePlay);

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
            getDataFromFirebase();

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

                handler.postDelayed(this, 10);
            }
        }, 10);
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