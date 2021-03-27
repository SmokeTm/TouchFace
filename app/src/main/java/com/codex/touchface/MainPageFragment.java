package com.codex.touchface;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainPageFragment extends Fragment {

    private DatabaseReference myRef, myRef2;
    private FirebaseAuth mAuth;
    private String get_nick;
    TextView textNick, textTime, textDay;
    FusedLocationProviderClient fusedLocationProviderClient;
    RoundedImageView iv_look;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_page, container, false);

        myRef = FirebaseDatabase.getInstance().getReference().child("News");
        myRef2 = FirebaseDatabase.getInstance().getReference().child("User");
        mAuth = FirebaseAuth.getInstance();
        textTime = v.findViewById(R.id.textTime_main);
        textNick = v.findViewById(R.id.textNick);
        textDay = v.findViewById(R.id.textDay_main);
        iv_look = v.findViewById(R.id.iv_look);


        Date d = new Date();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(d);


        Date currentDate = new Date();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);

        textTime.setText(timeText);
        textDay.setText(formattedDate);

        final String id = mAuth.getCurrentUser().getUid();
        myRef2.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                    UserGet userGet = dataSnapshot2.getValue(UserGet.class);
                    get_nick = userGet.getNickname();
                }

                Calendar c = Calendar.getInstance();
                int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

                if (timeOfDay >= 5 && timeOfDay < 12) {
                    textNick.setText("Доброе утро, " + get_nick);
                    iv_look.setImageResource(R.drawable.morning_back_black);
                } else if (timeOfDay >= 12 && timeOfDay < 16) {
                    textNick.setText("Добрый день, " + get_nick);
                    iv_look.setImageResource(R.drawable.day_back_black);
                } else if (timeOfDay >= 16 && timeOfDay < 21) {
                    textNick.setText("Добрый вечер, " + get_nick);
                    iv_look.setImageResource(R.drawable.evening_back_black);
                } else if (timeOfDay >= 21 && timeOfDay < 22) {
                    textNick.setText("Доброй ночи, " + get_nick);
                    iv_look.setImageResource(R.drawable.night_back_black);
                } else if (timeOfDay >= 22 && timeOfDay < 24) {
                    textNick.setText("Доброй ночи, " + get_nick);
                    iv_look.setImageResource(R.drawable.night_back_black);
                } else if (timeOfDay >= 0 && timeOfDay < 5) {
                    textNick.setText("Доброй ночи, " + get_nick);
                    iv_look.setImageResource(R.drawable.night_back_black);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }
}
