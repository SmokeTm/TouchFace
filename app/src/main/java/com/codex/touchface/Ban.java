package com.codex.touchface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class Ban extends AppCompatActivity {
    TextView banText;
    CircleImageView imBD2;
    public String nickname;
    public String reason;
    public String ban_text;
    public String reason_text;
    public String ava_url;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser cUser;
    private TextView banTextNick, banTextReason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ban);
        myRef = FirebaseDatabase.getInstance().getReference().child("User");
        mAuth = FirebaseAuth.getInstance();
        banTextNick = findViewById(R.id.banTextNick);
        banTextReason = findViewById(R.id.banTextReason);
        imBD2 = findViewById(R.id.imBD2);
        final String id = mAuth.getCurrentUser().getUid();
        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    UserGet userGet = dataSnapshot.getValue(UserGet.class);
                    nickname = userGet.getNickname();
                    reason = userGet.getBan_reason();
                    ava_url = userGet.getAva_url();
                    ban_text = nickname + ", Ваш аккаунт заблокирован!";
                    reason_text = reason;
                    banTextNick.setText(ban_text);
                    banTextReason.setText(reason_text);
                    Picasso.get().load(ava_url).into(imBD2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onClickSignOutBan(View view){
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), SignIn.class));
    }
}