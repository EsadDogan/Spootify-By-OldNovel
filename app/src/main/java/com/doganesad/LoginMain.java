package com.doganesad;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginMain#} factory method to
 * create an instance of this fragment.
 */
public class LoginMain extends Fragment {

    public final static String TAG= "loginMain Fragment";
    private TextInputLayout editTextEmail, editTextPassword;
    private TextInputEditText etEmail,etPassword;
    private MaterialButton btnLogIn,btnBackArrow,txtSignUp;
    private FirebaseAuth mAuth;
    private FragmentManager manager;





    public LoginMain() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextEmail = view.findViewById(R.id.textInputLayoutEmailLogIn);
        editTextPassword  = view.findViewById(R.id.textInputLayoutPasswordLogIn);
        txtSignUp = view.findViewById(R.id.txtSignUp);
        btnLogIn = view.findViewById(R.id.mBtnLogIn);
        btnBackArrow = view.findViewById(R.id.BackArrowbtnlogin);
        etEmail = view.findViewById(R.id.etemailLogIn);
        etPassword = view.findViewById(R.id.etPasswordLogIn);



        mAuth = FirebaseAuth.getInstance();


        //ONCLICK LISTENERS

        btnLogIn.setOnClickListener(view1 -> {

            if (correction())
            {
                String email = editTextEmail.getEditText().getText().toString();
                String password = editTextPassword.getEditText().getText().toString();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    }else{
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Log.d(TAG, "onViewCreated: ");
                        Toast.makeText(view.getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });

            }


        });

        txtSignUp.setOnClickListener(view1 -> {

            manager = getParentFragmentManager();

            manager.beginTransaction().replace(R.id.myFrameLaySignIn, new SignIn() , SignIn.TAG).addToBackStack(null)
                    .commit();

        });

        btnBackArrow.setOnClickListener(view1 -> {
            manager = getParentFragmentManager();
            manager.beginTransaction().replace(R.id.myFrameLaySignIn,new login(), login.TAG).addToBackStack(null).commit();
        });


        //ON CHANGE LISTENERS



        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String email = charSequence.toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmail.setError("PLease enter a valid Email address!");
                    editTextEmail.setEndIconMode(TextInputLayout.END_ICON_NONE);
                }
                else {
                    etEmail.setError(null);
                    editTextEmail.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String password = charSequence.toString().trim();
                if (password.length() <8 || password.length() >20 ){
                    etPassword.setError("Password must be longer than 8 character and lower less than 20 character!");
                    editTextPassword.setEndIconMode(TextInputLayout.END_ICON_NONE);
                }else {
                    etPassword.setError(null);
                    editTextPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }





    private void updateUI(FirebaseUser user) {

        if (user != null){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }

    }

    private boolean correction(){
        if (editTextEmail.getEditText().getText().toString().equals("")){
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getEditText().getText().toString()).matches()){

            return false;
        }

        if (editTextPassword.getEditText().getText().toString().equals("")){

            return false;
        }
        if (editTextPassword.getEditText().length()<6){

            return false;
        }
        return true;
    }

}