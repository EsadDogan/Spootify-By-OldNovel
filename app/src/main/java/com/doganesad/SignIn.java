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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignIn#} factory method to
 * create an instance of this fragment.
 */
public class SignIn extends Fragment {
    public static String TAG = "Sign in fragment";

    private MaterialButton btnBackArrow,txtLogin,btnSignup;
    private FragmentManager manager;
    private TextInputLayout edtEmail , edtUsername , edtPassword;
    private TextInputEditText etEmail, etUsernName, etPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DocumentReference mDocRef;
    public final static String KEY_USERNAME = "username";
    public final static String KEY_EMAIL ="email";
    public final static String KEY_UID ="uid";
    public final static String KEY_PASSWORD ="password";
    public final static String KEY_LIKEDSONGS = "likedsongs";

    FirebaseFirestore firebaseFirestore;



    public SignIn() {
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
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBackArrow = view.findViewById(R.id.BackArrowbtnSignin);
        //name txt but it actually material button
        txtLogin = view.findViewById(R.id.txtLoginLogin);
        btnSignup = view.findViewById(R.id.mBtnSignUpSignUp);
        edtEmail = view.findViewById(R.id.textInputLayoutEmailSignUp);
        edtUsername = view.findViewById(R.id.textInputLayoutUserNameSignUp);
        edtPassword = view.findViewById(R.id.textInputLayoutPassWordSignnUp);
        progressBar = view.findViewById(R.id.progressSignin);
        etUsernName =view.findViewById(R.id.etUserNameSignIn);
        etPassword = view.findViewById(R.id.etPasswordSignIn);
        etEmail = view.findViewById(R.id.etEmailSignIn);

        mAuth = FirebaseAuth.getInstance();

        manager = getParentFragmentManager();

        // ON CLICK LISTENERS

        btnBackArrow.setOnClickListener(view1 -> {

            manager.beginTransaction().replace(R.id.myFrameLaySignIn, new login(),login.TAG).addToBackStack(null).commit();

        });

        txtLogin.setOnClickListener(view1 -> {
            manager.beginTransaction().replace(R.id.myFrameLaySignIn,new LoginMain(),LoginMain.TAG).addToBackStack(null).commit();
        });

        btnSignup.setOnClickListener(view1 -> {
            if (correction()){
                registerAuth();
            }

        });


        //ON CHANGE LISTENERS
        etUsernName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String username = charSequence.toString().trim();

                if (username.length() <= 6 || username.length() >= 20 ){
                    etUsernName.setError("UserName must be longer than 6 character and less than 20 character");
                    edtUsername.setEndIconMode(TextInputLayout.END_ICON_NONE);
                }else {
                    etUsernName.setError(null);
                    edtUsername.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String email = charSequence.toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmail.setError("PLease enter a valid Email address!");
                    edtEmail.setEndIconMode(TextInputLayout.END_ICON_NONE);
                }
                else {
                    etEmail.setError(null);
                    edtEmail.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
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
                    edtPassword.setEndIconMode(TextInputLayout.END_ICON_NONE);
                }else {
                    etPassword.setError(null);
                    edtPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void registerAuth(){

        progressBar.setVisibility(View.VISIBLE);
            String email = edtEmail.getEditText().getText().toString();
            String username = edtUsername.getEditText().getText().toString();
            String password = edtPassword.getEditText().getText().toString();



            mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Log.d(TAG, "registerAuth: succesfull. user id = " + task.getResult().getUser().getUid());
                    User user = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(),username,email,password);

                    Map<String , Object> dataToSave = new HashMap<>();
                    dataToSave.put(KEY_UID,user.getuID());
                    dataToSave.put(KEY_USERNAME,user.getUserName());
                    dataToSave.put(KEY_EMAIL,user.getEmail());
                    dataToSave.put(KEY_PASSWORD,user.getPassword());

                    mDocRef = FirebaseFirestore.getInstance().document("Users/"+user.getuID());
                    mDocRef.set(dataToSave).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()){
                            Log.d(TAG, "registerAuth: UserFile has been created");
                        }else {
                            Log.d(TAG, "registerAuth: error" +task1.getException());
                        }
                    });

                    progressBar.setVisibility(View.GONE);

                    goMainActivity(user);

                }else
                {
                    Log.d(TAG, "registerAuth: error" +task.getException());
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Failed to sign up! please try again later ",Toast.LENGTH_LONG).show();
                }
            });








    }


    private boolean correction(){

        if (edtUsername.getEditText().getText().toString().equals("")){
            etUsernName.requestFocus();
            return false;
        }
         if (edtUsername.getEditText().length()<6){
             etUsernName.requestFocus();
             return false;
        }


        if (edtEmail.getEditText().getText().toString().equals("")){
            etEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getEditText().getText().toString()).matches()){
            etEmail.requestFocus();
            return false;
        }

        if (edtPassword.getEditText().getText().toString().equals("")){
            edtPassword.requestFocus();
            return false;
        }
        if (edtPassword.getEditText().length()<6 || edtPassword.getEditText().length()>20){
            edtPassword.requestFocus();
            return false;
        }
        return true;
    }



    private void goMainActivity(User user){
        if (user != null){
            Intent intent = new Intent(getActivity(), MainActivity.class).putExtra("userID",user.getuID());
            startActivity(intent);
        }
    }

}