package com.codex.touchface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {
    TextView nick, status, money;
    CircleImageView imImage;
    ImageView editPhoto, editNickName, editStatus, DefaultBackground, HalloweenBackground, DefaultBackgroundEditProfile, HalloweenBackgroundEditProfile;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser cUser;
    private StorageReference mStorageRef;
    private String get_nick;
    private int get_money;
    public String money_user;
    public String get_status;
    private Uri uploadUri;
    private String get_ava_url;
    private int get_background;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_edit_profile, container, false);

        myRef = FirebaseDatabase.getInstance().getReference().child("User");
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageDB");
        final String id = mAuth.getCurrentUser().getUid();
        //nick = v.findViewById(R.id.edTextNickName);
        //status = v.findViewById(R.id.edTextAboutMe);
        imImage = v.findViewById(R.id.imImage);
        //editPhoto = v.findViewById(R.id.imageView20);
        //editNickName = v.findViewById(R.id.imageViewEditNickname);
        //editStatus = v.findViewById(R.id.imageViewEditStatus);
        //DefaultBackground = v.findViewById(R.id.DefaultBackground);
        //HalloweenBackground = v.findViewById(R.id.HalloweenBackground);
        //DefaultBackgroundEditProfile = v.findViewById(R.id.defaultBackgroundEditProfile);
        //HalloweenBackgroundEditProfile = v.findViewById(R.id.halloweenBackgroundEditProfile);

        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    UserGet userGet = dataSnapshot.getValue(UserGet.class);
                    get_nick = userGet.getNickname();
                    get_money = userGet.getMoney();
                    get_status = userGet.getStatus();
                    get_ava_url = userGet.getAva_url();
                    get_background = userGet.getBackground();

                }
                nick.setText(String.valueOf(get_nick));
                status.setText(String.valueOf(get_status));
                Picasso.get().load(get_ava_url).into(imImage);

                if (get_background == 1){
                    DefaultBackgroundEditProfile.setVisibility(View.VISIBLE);
                }
                if (get_background == 2){
                    HalloweenBackgroundEditProfile.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

        editNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNicknameFragment editNicknameFragment = new EditNicknameFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, editNicknameFragment);
                transaction.commit();
            }
        });

        editStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditStatusFragment editStatusFragment = new EditStatusFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, editStatusFragment);
                transaction.commit();
            }
        });

        DefaultBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(id).child("background").setValue(1);
            }
        });

        HalloweenBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(id).child("background").setValue(2);
            }
        });


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null){
            if (resultCode == Activity.RESULT_OK){
                Log.d("MyLog", "ImageURI: " + data.getData());
                imImage.setImageURI(data.getData());
                uploadImage();
            }
        }
    }

    private void uploadImage(){
        Bitmap bitmap = ((BitmapDrawable) imImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference mRef = mStorageRef.child(System.currentTimeMillis() + "my_image");
        UploadTask up = mRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                uploadUri = task.getResult();
                final String id = mAuth.getCurrentUser().getUid();
                myRef.child(id).child("ava_url").setValue(uploadUri.toString());
            }
        });

    }

    private void getImage(){
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }


}