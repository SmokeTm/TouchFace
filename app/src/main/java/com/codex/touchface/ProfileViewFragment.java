package com.codex.touchface;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileViewFragment extends Fragment {
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
        View v =  inflater.inflate(R.layout.fragment_profile_view, container, false);

        nickView = v.findViewById(R.id.nick2);
        emailView = v.findViewById(R.id.email2);
        moneyView = v.findViewById(R.id.group2);
        //friendsView = v.findViewById(R.id.friends_profile);
        statusView = v.findViewById(R.id.statusEdit2);
        textRoleView = v.findViewById(R.id.textRole2);
        imBDView = v.findViewById(R.id.imBD);
        editProfileView = v.findViewById(R.id.imageView5);
        defaultBackgroundView = v.findViewById(R.id.defaultBackground);
        halloweenBackgroundView = v.findViewById(R.id.halloweenBackground);

        Bundle bundle = this.getArguments();
        nickView.setText(bundle.getString("user_nickname"));
        moneyView.setText(String.valueOf(bundle.getInt("user_money")));
        friendsView.setText(String.valueOf(bundle.getInt("user_subscribers")));
        emailView.setText(bundle.getString("user_email"));
        statusView.setText(bundle.getString("user_status"));
        get_lvl_role = bundle.getInt("user_role");
        get_ava_url = bundle.getString("user_ava");
        get_background = bundle.getInt("user_background");
        if (get_lvl_role == 0){
            textRoleView.setText("Пользователь");
            textRoleView.setTextColor(Color.parseColor("#f7f5e9")); //white
        }
        if (get_lvl_role == 1){
            textRoleView.setText("Подтвержденная страница");
            textRoleView.setTextColor(Color.parseColor("#02bfcc")); //soft ocean
        }
        if (get_lvl_role == 2){
            textRoleView.setText("Тестер");
            textRoleView.setTextColor(Color.parseColor("#cc02a0")); // pink (i think that must change in future)
        }
        if (get_lvl_role == 3){
            textRoleView.setText("Модератор");
            textRoleView.setTextColor(Color.parseColor("#96cc02")); //salat color
        }
        if (get_lvl_role == 4){
            textRoleView.setText("Администратор");
            textRoleView.setTextColor(Color.parseColor("#cc0202")); //soft red
        }
        if (get_lvl_role == 5){
            textRoleView.setText("Разработчик");
            textRoleView.setTextColor(Color.parseColor("#ffd500")); //yellow
        }
        Picasso.get().load(get_ava_url).into(imBDView);

        if (get_background == 1){
            defaultBackgroundView.setVisibility(View.VISIBLE);
        }
        if (get_background == 2){
            halloweenBackgroundView.setVisibility(View.VISIBLE);
        }


        return v;
    }
}