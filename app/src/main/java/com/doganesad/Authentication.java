package com.doganesad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication extends AppCompatActivity {

    FragmentManager manager;
    private FirebaseAuth mAuth;
    public final static String TAG = "authentication activity";

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
       // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            Intent intent = new Intent(this, MainActivity.class).putExtra("userID",currentUser.getUid());
            startActivity(intent);
            Log.d(TAG, "onStart: succes"+currentUser.getUid());

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);



        manager = getSupportFragmentManager();

        manager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction()
                .replace(R.id.myFrameLaySignIn, new login(),login.TAG)
                .addToBackStack(null).commit();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        manager = getSupportFragmentManager();

        manager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction()
                .replace(R.id.myFrameLaySignIn, new login(),login.TAG)
                .addToBackStack(null).commit();
    }
}