package com.codex.touchface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    Animation anim_signup, anim_dark;
    TextInputLayout edNickname, edEmail, edPassword;
    ImageView succeful, dark;
    Button sing_up;
    TextView Error;
    ConstraintLayout constraintLayout2;
    RelativeLayout loadingSignIn;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String USER_KEY = "User";
    public int get_users_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        edNickname = findViewById(R.id.edTextENicknameSignUp1);
        edEmail = findViewById(R.id.edTextEmailSignUp1);
        edPassword = findViewById(R.id.edTextPasswordSignUp1);
        constraintLayout2 = findViewById(R.id.constraintLayout2);
        loadingSignIn = findViewById(R.id.online123);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }

    public void onClickmakeAccount(View view) {
        Animation anim_up = AnimationUtils.loadAnimation(SignUp.this, R.anim.down_up);
        loadingSignIn.setVisibility(View.VISIBLE);
        loadingSignIn.startAnimation(anim_up);
        final String id;
        final String nick = edNickname.getEditText().getText().toString();
        final String email = edEmail.getEditText().getText().toString();
        final String password = edPassword.getEditText().getText().toString();
        final String ban_reason = "test";
        final String status = "Статус пользователя";
        final String ava_url = "pusto";
        final int money = 0;
        final int friends = 0;
        final int subscribers = 0;
        final int ban = 0;
        final int lvl_role = 0;
        final int network_lvl = 0;
        final int background = 1;
        final int lvl = 1;
        final int lr = 1;

        if (!TextUtils.isEmpty(edNickname.getEditText().getText().toString()) && !TextUtils.isEmpty(edEmail.getEditText().getText().toString()) && !TextUtils.isEmpty(edPassword.getEditText().getText().toString())) {
            if (nick.length() <= 15) {
                mAuth.createUserWithEmailAndPassword(edEmail.getEditText().getText().toString(), edPassword.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = mAuth.getCurrentUser().getUid();
                            UserPost newUser = new UserPost(id, nick, email, password, ban_reason, status, ava_url, lvl_role, money, friends, subscribers, ban, network_lvl, background, lvl, lr);
                            myRef.child(id).setValue(newUser);
                            Animation anim_down = AnimationUtils.loadAnimation(SignUp.this, R.anim.down_up_back);
                            loadingSignIn.startAnimation(anim_down);
                            loadingSignIn.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(getApplicationContext(), Main.class);
                            startActivity(intent);
                        } else {
                            Animation anim_down = AnimationUtils.loadAnimation(SignUp.this, R.anim.down_up_back);
                            loadingSignIn.startAnimation(anim_down);
                            loadingSignIn.setVisibility(View.INVISIBLE);
                            Snackbar snackbar = Snackbar.make(constraintLayout2, "Данная электронная почта недоступна!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }
                });
            } else {
                Animation anim_down = AnimationUtils.loadAnimation(SignUp.this, R.anim.down_up_back);
                loadingSignIn.startAnimation(anim_down);
                loadingSignIn.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(constraintLayout2, "Слишком длинный никнейм!", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        } else {
            Animation anim_down = AnimationUtils.loadAnimation(SignUp.this, R.anim.down_up_back);
            loadingSignIn.startAnimation(anim_down);
            loadingSignIn.setVisibility(View.INVISIBLE);
            Snackbar snackbar = Snackbar.make(constraintLayout2, "Введите данные!", Snackbar.LENGTH_SHORT);
            snackbar.show();

        }
    }
    public void onClickBackToSignIn(View view){
        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        startActivity(intent);
    }
}