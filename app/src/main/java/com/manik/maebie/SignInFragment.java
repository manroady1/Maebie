package com.manik.maebie;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView donthaveanaccount;
    FrameLayout parentFrameLayout;

    private EditText email;
    private EditText password;

    private TextView  forgotpassword;

    private TextView signinbtn;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view  =  inflater.inflate(R.layout.fragment_sign_in, container, false);
       donthaveanaccount = view.findViewById(R.id.NewUser);
       parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

       forgotpassword = view.findViewById(R.id.sign_in_forgot_password);

       email = view.findViewById(R.id.sign_in_email);
       password = view.findViewById(R.id.sign_in_password);
       signinbtn = view.findViewById(R.id.signin_btn);
       firebaseAuth = FirebaseAuth.getInstance();
       progressBar = view.findViewById(R.id.SignInprogressBar);

       return  view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        donthaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setfragment(new SignUpFragment());
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setfragment(new ResetPasswordFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
    }

    private void setfragment(Fragment fragment1){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment1);
        fragmentTransaction.commit();

    }
    private void checkInputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())) {
                signinbtn.setEnabled(true);
                signinbtn.setTextColor(Color.rgb(255,255,255));
                }else{
                signinbtn.setEnabled(false);
                signinbtn.setTextColor(Color.argb(50,255,255,255));
            }
        }else {
            signinbtn.setEnabled(false);
            signinbtn.setTextColor(Color.argb(50,255,255,255));
        }
    }


    private void checkEmailAndPassword(){
        if (email.getText().toString().matches(emailPattern)){
            if(password.length() >= 8){

                progressBar.setVisibility(View.VISIBLE);
                signinbtn.setEnabled(false);
                signinbtn.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent mainintent = new Intent(getActivity(),Main2Activity.class);
                                    startActivity(mainintent);
                                    getActivity().finish();
                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signinbtn.setEnabled(true);
                                    signinbtn.setTextColor(Color.rgb(255,255,255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else {
                Toast.makeText(getActivity(),"Incorrect email and password",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(),"Incorrect email and password",Toast.LENGTH_SHORT).show();
        }
    }

}
