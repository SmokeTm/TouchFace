package com.codex.touchface;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllPeopleFragment extends Fragment {
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> listData;
    List<UserGet> listTemp;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private DatabaseReference mRef;
    private FirebaseUser cUser;
    private String USER_KEY = "User";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_all_people, container, false);


        mAuth = FirebaseAuth.getInstance();
        listView = v.findViewById(R.id.listView);
        listData = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        myRef = FirebaseDatabase.getInstance().getReference("User");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (listData.size() > 0) listData.clear();
                if (listTemp.size() > 0) listTemp.clear();
                for (DataSnapshot ds : datasnapshot.getChildren()) {
                    UserGet userGet = ds.getValue(UserGet.class);
                    assert userGet != null;
                    listData.add(userGet.getNickname());
                    listTemp.add(userGet);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserGet userGet = listTemp.get(position);
                ProfileViewFragment profileViewFragment = new ProfileViewFragment();

                Bundle bundle = new Bundle();

                bundle.putString("user_nickname", userGet.getNickname());
                bundle.putString("user_ava", userGet.getAva_url());
                bundle.putString("user_email", userGet.getEmail());
                bundle.putInt("user_role", userGet.getLvl_role());
                bundle.putInt("user_ban", userGet.getBan());
                bundle.putInt("user_money", userGet.getMoney());
                bundle.putString("user_status", userGet.getStatus());
                bundle.putInt("user_subscribers", userGet.getFriends());
                bundle.putString("user_id", userGet.getId());
                bundle.putInt("user_network_lvl", userGet.getNetwork_lvl());
                bundle.putInt("user_background", userGet.getBackground());
                profileViewFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, profileViewFragment);
                transaction.commit();

            }
        });

        return v;
    }
}
