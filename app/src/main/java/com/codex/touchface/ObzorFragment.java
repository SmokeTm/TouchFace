package com.codex.touchface;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ObzorFragment extends Fragment {

    CardView InfoPlit, PeoplePlit, BalancePlit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_obzor, container, false);

        InfoPlit = v.findViewById(R.id.InfoPlit);
        InfoPlit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoFragment infoFragment = new InfoFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, infoFragment);
                transaction.commit();
            }
        });

        PeoplePlit = v.findViewById(R.id.PeoplePlit);
        PeoplePlit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllPeopleFragment allPeopleFragment = new AllPeopleFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, allPeopleFragment);
                transaction.commit();
            }
        });

        BalancePlit = v.findViewById(R.id.BalancePlit);
        BalancePlit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Balance balance = new Balance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, balance);
                transaction.commit();
            }
        });





        return v;
    }
}