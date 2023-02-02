package com.doganesad;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "main activity";
    public static CardView littleCard,bigCard;
    public BottomNavigationView bottomNavigationView;
    public static  Button btndownArrowBigcard, btnLittlecardPlayPause,btnBigCardPlayPause;
    FragmentManager manager;
    public static ArrayList<Music> musics;
    public static MediaPlayer mediaPlayer;
    public static TextView txtSongNameLittleCard,txtSongNameBigCard,txtArtistNameLittleCard,txtArtistNameBigCard;
    public static ImageView imageViewBC,imageViewLC;
    public FrameLayout frameLayout;

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

        //inside cards
        txtArtistNameLittleCard =findViewById(R.id.txtArtistNameLittleCard);
        txtArtistNameBigCard = findViewById(R.id.txtArtistNameBigCard);
        txtSongNameBigCard = findViewById(R.id.txtSongNameBigCard);
        txtSongNameLittleCard = findViewById(R.id.txtSongNameLittleCard);
        imageViewBC = findViewById(R.id.imageViewBigCard);
        imageViewLC = findViewById(R.id.imageViewLittleCard);


        musics = new ArrayList<>();


        try {
            getDataFromFirebase();

        }catch (Exception e){
            Log.d(TAG, "onCreate: error" + e.getMessage());
        }






        btnLittlecardPlayPause.setOnClickListener(view -> playPause());

        btnBigCardPlayPause.setOnClickListener(view -> playPause());


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

        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            btnBigCardPlayPause.setBackgroundResource(R.drawable.playcircle_icon);
            btnLittlecardPlayPause.setBackgroundResource(R.drawable.play_icon);
        }
        else {
            mediaPlayer.start();
            btnBigCardPlayPause.setBackgroundResource(R.drawable.pausecircle_icon);
            btnLittlecardPlayPause.setBackgroundResource(R.drawable.pause_icon);
        }
    }

    public static void setCards(Music music){

        //little card
        txtSongNameLittleCard.setText(music.getSongName());
        txtArtistNameLittleCard.setText(music.getArtistName());
        btnLittlecardPlayPause.setBackgroundResource(R.drawable.pause_icon);

        //Big Card
        txtSongNameBigCard.setText(music.getSongName());
        txtArtistNameBigCard.setText(music.getArtistName());
        btnBigCardPlayPause.setBackgroundResource(R.drawable.pausecircle_icon);



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

                    musics.add(new Music(songName,artistName,songUrl,coverUrl,musicId));


                }

                musics.add(new Music("","","a","",0));
                musics.add(new Music("","","","",0));
                Log.d(TAG, "onSuccess: veri geldi");

            }else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }

        });

    }

}