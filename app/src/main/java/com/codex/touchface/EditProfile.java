package com.codex.touchface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class EditProfile extends AppCompatActivity {
    EditText nick;
    TextView money;
    MultiAutoCompleteTextView status;
    CircleImageView imImage;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser cUser;
    private StorageReference mStorageRef;
    private String get_nick;
    private int get_money;
    public String money_user;
    public String get_status;
    public int money_user_int;
    public int money_user_after_nickname_edit;
    private Uri uploadUri;
    private String get_ava_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        myRef = FirebaseDatabase.getInstance().getReference().child("User");
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageDB");
        final String id = mAuth.getCurrentUser().getUid();
        nick = findViewById(R.id.edTextNick);
        money = findViewById(R.id.money_edit_profile);
        status = findViewById(R.id.statusEdit2);
        imImage = findViewById(R.id.imImage);
        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    UserGet userGet = dataSnapshot.getValue(UserGet.class);
                    get_nick = userGet.getNickname();
                    get_money = userGet.getMoney();
                    get_status = userGet.getStatus();
                    get_ava_url = userGet.getAva_url();

                }
                nick.setHint(String.valueOf(get_nick));
                money.setText(String.valueOf(get_money));
                status.setHint(String.valueOf(get_status));
                Picasso.get().load(get_ava_url).into(imImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Инициализация
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Главная страница
        bottomNavigationView.setSelectedItemId(R.id.profile);

        //Слушатель выбранных айтемов
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main:
                        startActivity(new Intent(getApplicationContext(), Main.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                    case R.id.market:
                        //startActivity(new Intent(getApplicationContext(), Market.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.messages:
                        //startActivity(new Intent(getApplicationContext(), Message.class));
                        //overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


    }



    public void onClickEditNick(View view) {
        money_user = (String) money.getText();
        money_user_int = Integer.parseInt(money_user);
        if (!TextUtils.isEmpty(nick.getText().toString())) {
            if (money_user_int < 100) {
                Toast.makeText(this, "Недостаточно TouchCoins", Toast.LENGTH_SHORT).show();
            }
            if (money_user_int >= 100) {
                final String id = mAuth.getCurrentUser().getUid();
                myRef.child(id).child("nickname").setValue(nick.getText().toString());
                money_user_after_nickname_edit = money_user_int - 100;
                myRef.child(id).child("money").setValue(money_user_after_nickname_edit);
                nick.setText("");
                Toast.makeText(this, "Имя было успешно изменено!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Введите никнейм!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickBackSettings(View view){
        Intent intent = new Intent(getApplicationContext(), Main.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    public void onClickEditStatus(View view){
        if (!TextUtils.isEmpty(status.getText().toString())){
            final String id = mAuth.getCurrentUser().getUid();
            myRef.child(id).child("status").setValue(status.getText().toString());
            status.setText("");
            Toast.makeText(this, "Информация была изменена успешно!", Toast.LENGTH_SHORT).show();
        }
    }

    //все с изменением аватарки

    public void onClickEditAvatar(View view){
        getImage();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null){
            if (resultCode == RESULT_OK){
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
                Toast.makeText(EditProfile.this, "Аватар был успешно изменен!", Toast.LENGTH_SHORT).show();
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