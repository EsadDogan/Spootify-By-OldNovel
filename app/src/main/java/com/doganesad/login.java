package com.doganesad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link login} factory method to
 * create an instance of this fragment.
 */
public class login extends Fragment {

    public login() {
        // Required empty public constructor
    }

    private MaterialButton btnSignUp;
    private TextView txtLogIn;
    private FragmentManager manager;

    public final static String TAG= "login Fragment";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSignUp = view.findViewById(R.id.mBtnSignUp);
        txtLogIn = view.findViewById(R.id.txtLogIn);

        //ONCLICK LISTENERS

        btnSignUp.setOnClickListener(view1 -> {

            goSignIn();

        });

        txtLogIn.setOnClickListener(view1 -> {

            goLogInMain();

        });



    }

    private void goLogInMain() {
        manager = getParentFragmentManager();
        manager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction().replace(R.id.myFrameLaySignIn, new LoginMain(),LoginMain.TAG)
                .addToBackStack(null).commit();
    }

    private void goSignIn() {

        manager = getParentFragmentManager();

        manager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction()
                .replace(R.id.myFrameLaySignIn, new SignIn(),SignIn.TAG)
                .addToBackStack(null).commit();


    }

//    private void goMainActivity() {
//        Intent intent;
//        intent = new Intent(login. , MainActivity.class);
//        startActivity(intent);
//    }

}