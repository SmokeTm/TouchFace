package com.codex.touchface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {
    public TextView textDown;
    private Intent second_screen;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser cUser;
    public String get_version;
    public String get_version_key;
    public String UpdateLel;
    public int get_ban;
    TextView textUpdateCheckLol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textDown = findViewById(R.id.textDown);
        Random random = new Random();
        int text_downRandom = random.nextInt(4);
        if (text_downRandom == 1) {
            textDown.setText("#общайся");
        }
        if (text_downRandom == 2) {
            textDown.setText("#обсуждай");
        }
        if (text_downRandom == 3) {
            textDown.setText("#продавай");
        }
        if (text_downRandom == 4) {
            textDown.setText("#покупай");
        }

        myRef = FirebaseDatabase.getInstance().getReference().child("Version");
        textUpdateCheckLol = findViewById(R.id.textUpdateCheckLol);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if (cUser != null) {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        VersionGet versionGet = dataSnapshot.getValue(VersionGet.class);
                        get_version = versionGet.getVersion();
                    }
                    textUpdateCheckLol.setText(get_version);
                    UpdateLel = (String) textUpdateCheckLol.getText();

                    String versionCode = BuildConfig.VERSION_NAME;

                    if (UpdateLel.equals(versionCode)) {
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("User");
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        final String id = mAuth.getCurrentUser().getUid();
                        myRef.child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    UserGet userGet = dataSnapshot.getValue(UserGet.class);

                                    get_ban = userGet.getBan();
                                    if (get_ban == 0) {
                                        final Intent intent = new Intent(SplashActivity.this, Main.class);
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                                } else {
                                                    ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                                }
                                            }
                                        }, 2000);
                                    }
                                    else{
                                        final Intent intent = new Intent(SplashActivity.this, Ban.class);
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                                } else {
                                                    ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                                }
                                            }
                                        }, 2000);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        final Intent intent = new Intent(SplashActivity.this, Update.class);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                } else {
                                    ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }
                            }
                        }, 2000);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            final Intent intent = new Intent(SplashActivity.this, SignIn.class);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    } else {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            }, 2000);
        }
    }
}