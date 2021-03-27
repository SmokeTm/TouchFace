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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String USER_KEY = "User";
    public int get_ban;
    private Intent sign_up;
    RelativeLayout loadingSignIn;
    ConstraintLayout constraintLayout;
    TextInputLayout edEmail, edPassword;
    ImageView succeful2, dark2;
    Animation anim_dark;
    Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference(USER_KEY);
        edEmail = findViewById(R.id.edTextENicknameSignUp);
        edPassword = findViewById(R.id.edTextEmailSignUp);
        anim_dark = AnimationUtils.loadAnimation(this, R.anim.alpha);
        signin = findViewById(R.id.btn_signin);
        constraintLayout = findViewById(R.id.constraintLayout);
        loadingSignIn = findViewById(R.id.online123);

    }
    public void onClickSignIn(View view){
        Animation anim_up = AnimationUtils.loadAnimation(SignIn.this, R.anim.down_up);
        loadingSignIn.setVisibility(View.VISIBLE);
        loadingSignIn.startAnimation(anim_up);
        if (!TextUtils.isEmpty(edEmail.getEditText().getText().toString()) && !TextUtils.isEmpty(edPassword.getEditText().getText().toString())){
            mAuth.signInWithEmailAndPassword(edEmail.getEditText().getText().toString(), edPassword.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        final String id = mAuth.getCurrentUser().getUid();
                        myRef.child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                    UserGet userGet = dataSnapshot.getValue(UserGet.class);
                                    get_ban = userGet.getBan();
                                }
                                if (get_ban == 0) {
                                    sign_up = new Intent(getApplicationContext(), Main.class);
                                }
                                else {
                                    sign_up = new Intent(getApplicationContext(), Ban.class);
                                }
                                Animation anim_down = AnimationUtils.loadAnimation(SignIn.this, R.anim.down_up_back);
                                loadingSignIn.startAnimation(anim_down);
                                loadingSignIn.setVisibility(View.INVISIBLE);
                                startActivity(sign_up);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else {
                        Animation anim_down = AnimationUtils.loadAnimation(SignIn.this, R.anim.down_up_back);
                        loadingSignIn.startAnimation(anim_down);
                        loadingSignIn.setVisibility(View.INVISIBLE);
                        Snackbar snackbar = Snackbar.make(constraintLayout, "Не верный пароль!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }
            });
        }
        else{
            Animation anim_down = AnimationUtils.loadAnimation(SignIn.this, R.anim.down_up_back);
            loadingSignIn.startAnimation(anim_down);
            loadingSignIn.setVisibility(View.INVISIBLE);
            Snackbar snackbar = Snackbar.make(constraintLayout, "Введите данные!", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }
    public void regText(View view){
        sign_up = new Intent(getApplicationContext(), SignUp.class);
        startActivity(sign_up);
    }
}