package com.codex.touchface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main extends AppCompatActivity {
    TextView time;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> listData;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser cUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        myRef = FirebaseDatabase.getInstance().getReference().child("User");
        mAuth = FirebaseAuth.getInstance();
        final String id = mAuth.getCurrentUser().getUid();
        myRef.child(id).child("network_lvl").setValue(1);

        BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()){
                            case R.id.main:
                                selectedFragment = new MainPageFragment();
                                break;
                            case R.id.messages:
                                selectedFragment = new ObzorFragment();
                                break;
                            case R.id.market:
                                selectedFragment = new FragmentMessages();
                                break;
                            case R.id.profile:
                                selectedFragment = new ProfileFragment();
                                break;
                        }
                        assert selectedFragment != null;
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();

                        return true;
                    }
                };



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);



        MainPageFragment mainPageFragment = new MainPageFragment();
        FragmentTransaction ftmainpage = getSupportFragmentManager().beginTransaction();
        ftmainpage.replace(R.id.container, mainPageFragment);
        ftmainpage.commit();


    }

    public void onBackPressed(){
        //block
    }
}