package com.codex.touchface;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    TextView textnick, textRole, textStatus, textTouchcoins, textLvl, textSubs;
    CircleImageView imBDView;
    ImageView editProfileView;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private DatabaseReference mRef;
    private FirebaseUser cUser;

    private String nickname, email, status, avatar_url;
    private String touchcoins;
    private String subs;
    private int role;
    private String lvl;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        textnick = v.findViewById(R.id.textNick);
        textTouchcoins = v.findViewById(R.id.textTouchcoins);
        textSubs = v.findViewById(R.id.textSubs);
        textStatus = v.findViewById(R.id.textStatus);
        textRole = v.findViewById(R.id.textRole);
        textLvl = v.findViewById(R.id.textLvl);
        imBDView = v.findViewById(R.id.imBD);
        editProfileView = v.findViewById(R.id.imEditProfile);


        mAuth = FirebaseAuth.getInstance();
        final String id = mAuth.getCurrentUser().getUid();
        myRef = FirebaseDatabase.getInstance().getReference("User").child(id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserGet userGet = dataSnapshot.getValue(UserGet.class);
                    nickname = String.valueOf(userGet.getNickname());
                    status = String.valueOf(userGet.getStatus());
                    avatar_url = String.valueOf(userGet.getAva_url());
                    touchcoins = String.valueOf(userGet.getMoney());
                    subs = String.valueOf(userGet.getSubscribers());
                    role = userGet.getLvl_role();
                    lvl = String.valueOf(userGet.getLvl());
                }
                textnick.setText(nickname);
                textStatus.setText(status);
                textTouchcoins.setText(touchcoins);
                textSubs.setText(subs);
                textLvl.setText(lvl);
                Picasso.get().load(avatar_url).into(imBDView);

                if (role == 0){
                    textRole.setText("Пользователь");
                    textRole.setTextColor(Color.parseColor("#f7f5e9")); //white
                }
                if (role == 1){
                    textRole.setText("Подтвержденная страница");
                    textRole.setTextColor(Color.parseColor("#02bfcc")); //soft ocean
                }
                if (role == 2){
                    textRole.setText("Тестер");
                    textRole.setTextColor(Color.parseColor("#cc02a0")); // pink (i think that must change in future)
                }
                if (role == 3){
                    textRole.setText("Модератор");
                    textRole.setTextColor(Color.parseColor("#96cc02")); //salat color
                }
                if (role == 4){
                    textRole.setText("Администратор");
                    textRole.setTextColor(Color.parseColor("#cc0202")); //soft red
                }
                if (role == 5){
                    textRole.setText("Разработчик");
                    textRole.setTextColor(Color.parseColor("#ffd500")); //yellow
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









        return v;
    }
}