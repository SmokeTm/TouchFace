package com.codex.touchface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update extends AppCompatActivity {
    TextView update_version, update_info, download_url;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser cUser;
    public String get_version_lol;
    public String get_version_info;
    public String get_version_download;
    public String download_url_final;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        update_version = findViewById(R.id.update_version);
        update_info = findViewById(R.id.update_info2);
        download_url = findViewById(R.id.download_url);
        myRef = FirebaseDatabase.getInstance().getReference().child("Version");
        mAuth = FirebaseAuth.getInstance();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    VersionGet versionGet = dataSnapshot.getValue(VersionGet.class);
                    get_version_lol = versionGet.getVersion();
                    get_version_info = versionGet.getVersion_info();
                    get_version_download = versionGet.getVersion_download();
                }
                update_version.setText(get_version_lol);
                update_info.setText(get_version_info);
                download_url.setText(get_version_download);
                download_url_final = (String) download_url.getText();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void onClickDownload(View view){
        Log.d("MyLog","Url is " + download_url_final);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(download_url_final));
        startActivity(browserIntent);
    }
}