package com.codex.touchface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class SingInOrSignUp extends AppCompatActivity {
    Button sing_in, sign_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_or_signup);
        sing_in = findViewById(R.id.btn_signin);
        sign_up = findViewById(R.id.btn_signup);
        
    }
    public void onClickSignIn(View view){
        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
    public void onClickSignUp(View view){
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}