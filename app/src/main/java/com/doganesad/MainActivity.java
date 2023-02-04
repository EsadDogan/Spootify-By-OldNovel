package com.doganesad;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;


import android.app.Activity;
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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "main activity";
    public static CardView littleCard,bigCard;
    public BottomNavigationView bottomNavigationView;
    public static  Button btndownArrowBigcard,btnLittlecardPlayPause ;
    public Button btnplayNextSongBC,btnplayPreviousSongBC;
    public static Button btnBigCardPlayPause;
    FragmentManager manager;
    public static ArrayList<Music> musics;
    public static MediaPlayer mediaPlayer;
    public static TextView txtSongNameLittleCard,txtSongNameBigCard,txtArtistNameLittleCard,txtArtistNameBigCard;
    public static ImageView imageViewBC,imageViewLC;
    public static FrameLayout frameLayout;
    public static SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // go to hame fragment when its first time opened
        goHome();

        littleCard = findViewById(R.id.littleCard);
        bigCard = findViewById(R.id.BigCard);
        btndownArrowBigcard =findViewById(R.id.downButtonBigCard);
        btnLittlecardPlayPause=findViewById(R.id.btnPlayPauseLittleCard);
        btnBigCardPlayPause =findViewById(R.id.btnPlayPauseBigCard);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.myFrameLay);
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


        musics = new ArrayList<>();


        try {
            getDataFromFirebase();

        }catch (Exception e){
            Log.d(TAG, "onCreate: error" + e.getMessage());
        }






        btnLittlecardPlayPause.setOnClickListener(view -> playPause());

        btnBigCardPlayPause.setOnClickListener(view -> playPause());


        // TODO: 3.02.2023 make revision for this part
        
        btnplayNextSongBC.setOnClickListener(view -> {

            try {
                playNextSong(musics.get(SongRecylerviewAdapter.currentMusicPosition + 1));
                if (SongRecylerviewAdapter.currentMusicPosition < musics.size() -3 ) {
                    SongRecylerviewAdapter.currentMusicPosition += 1;
                }
            } catch (Exception e) {
                Log.d(TAG, "onCreate: error probably out of bound" + e.getMessage());
               }

        });

        btnplayPreviousSongBC.setOnClickListener(view -> {

            try {
                playPreviousSong(musics.get(SongRecylerviewAdapter.currentMusicPosition-1));
                if (SongRecylerviewAdapter.currentMusicPosition > 0) {
                    SongRecylerviewAdapter.currentMusicPosition -= 1;
                }
            }catch (Exception e){
                Log.d(TAG, "onCreate: error probably out of bound " + e.getMessage());
            }

        });

        // TODO: 3.02.2023 enable repeat button

//        if (mediaPlayer != null) {
//            mediaPlayer.setOnCompletionListener(mediaPlayer -> {
//
//                if (SongRecylerviewAdapter.nextMusic != null) {
//                    playNextSong(SongRecylerviewAdapter.nextMusic);
//                }
//
//            });
//        }


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
                    updateSeekBar();

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
                    updateSeekBar();

                }catch (Exception e){
                    Log.d(TAG, "playNextSong: error" + e.getMessage());
                }

              }  else {

                    // TODO: 3.02.2023 show snackbar
                }


        }



    }



    public static void updateSeekBar() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer.isPlaying()) {
                    int currentPos = mediaPlayer.getCurrentPosition();
                    int totalDuration = mediaPlayer.getDuration();
                    int progress = (int) (((float) currentPos / totalDuration) * 100);
                    seekBar.setProgress(progress);
                }
                handler.postDelayed(this, 100);
            }
        }, 100);
    }

}