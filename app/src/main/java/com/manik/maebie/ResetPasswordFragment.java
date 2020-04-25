package com.manik.maebie;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {


    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    private EditText registeremail;
    private Button resetpassword_button;
    private TextView goback;

    private ViewGroup emailIconcontainer;
    private ImageView emailicon;
    private TextView emailIconText;
     private ProgressBar progressBar;
    private FrameLayout parentFrameLayout;

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_reset_password, container, false);
        registeremail = view.findViewById(R.id.forgot_pass_email);
        resetpassword_button = view.findViewById(R.id.reset_pass_btn);
        goback = view.findViewById(R.id.forgot_pass_goback);

        emailIconcontainer = view.findViewById(R.id.forgot_pass_layout);
        emailicon = view.findViewById(R.id.forgot_pass_emailicon);
        emailIconText = view.findViewById(R.id.forgot_pass_emailicon_text);
        progressBar = view.findViewById(R.id.forget_pass_progressbar);

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registeremail.addTextChangedListener(new TextWatcher() {
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

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setfragment(new SignInFragment());
            }
        });

        resetpassword_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(emailIconcontainer);
                emailIconText.setVisibility(View.GONE);

                TransitionManager.beginDelayedTransition(emailIconcontainer);
                emailicon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                resetpassword_button.setEnabled(false);
                resetpassword_button.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.sendPasswordResetEmail(registeremail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){

                                   ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0,emailicon.getWidth()/2
                                   ,emailicon.getHeight()/2);
                                   scaleAnimation.setDuration(100);
                                   scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                   scaleAnimation.setRepeatMode(Animation.REVERSE);
                                   scaleAnimation.setRepeatCount(1);

                                   scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                       @Override
                                       public void onAnimationStart(Animation animation) {

                                       }

                                       @Override
                                       public void onAnimationEnd(Animation animation) {

                                           emailIconText.setText("Recovery email send successfully! Check your Inbox");
                                           emailIconText.setTextColor(getResources().getColor(R.color.success_green));
                                           TransitionManager.beginDelayedTransition(emailIconcontainer);
                                           emailIconText.setVisibility(View.VISIBLE);
                                       }

                                       @Override
                                       public void onAnimationRepeat(Animation animation) {
                                        emailicon.setImageResource(R.drawable.email);
                                       }
                                   });

                                   emailicon.startAnimation(scaleAnimation);

                               }   else {

                                   String error = task.getException().getMessage();

                                   resetpassword_button.setEnabled(true);
                                   resetpassword_button.setTextColor(Color.rgb(255,255,255));

                                   TransitionManager.beginDelayedTransition(emailIconcontainer);
                                   emailIconText.setText(error);
                                   emailIconText.setTextColor(getResources().getColor(R.color.red));
                                   emailIconText.setVisibility(View.VISIBLE);
                               }
                                progressBar.setVisibility(View.GONE);

                            }
                        });

            }
        });
    }

    private void checkInputs(){
        if(TextUtils.isEmpty(registeremail.getText())){
            resetpassword_button.setEnabled(false);
            resetpassword_button.setTextColor(Color.argb(50,255,255,255));
        }else {
            resetpassword_button.setEnabled(true);
            resetpassword_button.setTextColor(Color.rgb(255,255,255));
        }
    }

    private void setfragment(Fragment fragment1){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_left_in,R.anim.slide_right_out);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment1);
        fragmentTransaction.commit();

    }

}
