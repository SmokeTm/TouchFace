package com.codex.touchface;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditStatusFragment extends Fragment {

    EditText editStatus;
    TextView textStatusEditError;
    FloatingActionButton floatingActionButtonEditStatus;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String get_status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_status, container, false);

        myRef = FirebaseDatabase.getInstance().getReference().child("User");
        mAuth = FirebaseAuth.getInstance();
        final String id = mAuth.getCurrentUser().getUid();
        editStatus = v.findViewById(R.id.edStatus);
        textStatusEditError = v.findViewById(R.id.textStatusEditError);
        floatingActionButtonEditStatus = v.findViewById(R.id.floatingActionButtonEditStatus);

        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    UserGet userGet = dataSnapshot.getValue(UserGet.class);
                    get_status = userGet.getStatus();
                }
                editStatus.setHint(String.valueOf(get_status));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        floatingActionButtonEditStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editStatus.getText().toString())) {
                    if (editStatus.getText().toString().length() > 30) {
                        textStatusEditError.setVisibility(View.VISIBLE);
                        textStatusEditError.setText("Статус не может привышать 30 символов!");
                    }
                    else {
                        final String id = mAuth.getCurrentUser().getUid();
                        myRef.child(id).child("status").setValue(editStatus.getText().toString());
                        ProfileFragment profileFragment = new ProfileFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, profileFragment);
                        transaction.commit();
                    }
                }
                else {
                    textStatusEditError.setVisibility(View.VISIBLE);
                    textStatusEditError.setText("Некорректный статус!");
                }
            }
        });


        return v;
    }
}