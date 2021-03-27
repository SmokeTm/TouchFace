package com.codex.touchface;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    TextView nickView, emailView, moneyView, friendsView, statusView, textRoleView;
    CircleImageView imBDView;
    ImageView editProfileView, defaultBackgroundView, halloweenBackgroundView;

    public int get_lvl_role;
    public String get_ava_url;
    public int get_background;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_profile, container, false);













        return view;
    }
}