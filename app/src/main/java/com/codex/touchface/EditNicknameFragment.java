package com.codex.touchface;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class EditNicknameFragment extends Fragment {

    TextView textNicknameEditError, money2;
    EditText editNick2;
    FloatingActionButton floatingActionButtonEditNickname;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser cUser;
    private String get_nick;
    private int get_money;
    public String money_user;
    public int money_user_int;
    public int money_user_after_nickname_edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_nickname, container, false);

        myRef = FirebaseDatabase.getInstance().getReference().child("User");
        mAuth = FirebaseAuth.getInstance();
        final String id = mAuth.getCurrentUser().getUid();
        editNick2 = v.findViewById(R.id.edTextNick2);
        textNicknameEditError = v.findViewById(R.id.textNicknameEditError);
        floatingActionButtonEditNickname = v.findViewById(R.id.floatingActionButtonEditNickname);
        money2 = v.findViewById(R.id.money_edit_profile2);
        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    UserGet userGet = dataSnapshot.getValue(UserGet.class);
                    get_nick = userGet.getNickname();
                    get_money = userGet.getMoney();
                }
                editNick2.setHint(String.valueOf(get_nick));
                money2.setText(String.valueOf(get_money));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        floatingActionButtonEditNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money_user = (String) money2.getText();
                money_user_int = Integer.parseInt(money_user);
                if (!TextUtils.isEmpty(editNick2.getText().toString())) {
                    if (editNick2.getText().toString().length() > 10){
                        textNicknameEditError.setVisibility(View.VISIBLE);
                        textNicknameEditError.setText("Никнейм не может привышать 10 символов!");
                    }
                    else {
                        if (money_user_int < 100) {
                            textNicknameEditError.setVisibility(View.VISIBLE);
                            textNicknameEditError.setText("Недостаточно TouchCoins");
                        } else {
                            if (money_user_int >= 100) {
                                final String id = mAuth.getCurrentUser().getUid();
                                myRef.child(id).child("nickname").setValue(editNick2.getText().toString());
                                money_user_after_nickname_edit = money_user_int - 100;
                                myRef.child(id).child("money").setValue(money_user_after_nickname_edit);
                                editNick2.setText("");
                                ProfileFragment profileFragment = new ProfileFragment();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, profileFragment);
                                transaction.commit();
                            }
                        }
                    }
                }
                else{
                    textNicknameEditError.setVisibility(View.VISIBLE);
                    textNicknameEditError.setText("Некорректный никнейм!");
                }
            }
        });




        return v;
    }
}