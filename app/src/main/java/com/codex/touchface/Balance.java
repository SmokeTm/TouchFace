package com.codex.touchface;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

import java.util.EventListener;

public class Balance extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser cUser;
    ImageView im_back;
    TextView textBalance;
    public int get_balance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_balance, container, false);

        textBalance = v.findViewById(R.id.textBalance);

        mAuth = FirebaseAuth.getInstance();
        final String id = mAuth.getCurrentUser().getUid();
        myRef = FirebaseDatabase.getInstance().getReference("User");
        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot dataSnapshot1 : datasnapshot.getChildren()) {
                    UserGet userGet = datasnapshot.getValue(UserGet.class);
                    get_balance = userGet.getMoney();
                    textBalance.setText(String.valueOf(get_balance));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //тест

        im_back = v.findViewById(R.id.im_back);
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObzorFragment obzorFragment = new ObzorFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, obzorFragment);
                transaction.commit();
            }
        });



        return v;
    }
}