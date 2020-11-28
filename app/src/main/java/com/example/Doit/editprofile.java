package com.example.Doit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class editprofile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText profileFullName,profileEmail,profilePhone;
    ImageView profileImageView;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUser user;
    FirebaseFirestore storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);


        Intent data = getIntent();
        String fullName = data.getStringExtra("fullName");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");

        fAuth =FirebaseAuth.getInstance();
        fstore =FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();



        profileFullName = findViewById(R.id.profileFullName);
        profileEmail = findViewById(R.id.profileEmailAdress);
        profilePhone = findViewById(R.id.profilephoneNo);
        profileImageView = findViewById(R.id.profileImageView);
        saveBtn = findViewById(R.id.saveProfileInfo);



        profileImageView.setOnClickListener(v -> Toast.makeText(editprofile.this,"Profile Image Clicked.",Toast.LENGTH_SHORT).show());
        saveBtn.setOnClickListener(V -> {
            if (profileFullName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty() || profilePhone.getText().toString().isEmpty()) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }

            String email1 = profileEmail.getText().toString();
            user.updateEmail(email1).addOnSuccessListener(aVoid -> {
                DocumentReference docRef = fstore.collection("users").document(user.getUid());
                Map<String,Object> edited =new HashMap<>();
                edited.put("email",email1);
                edited.put("fName",profileFullName.getText().toString());
                edited.put("phone",profilePhone.getText().toString());
                docRef.update(edited).addOnSuccessListener(aVoid1 -> {
                    Toast.makeText(editprofile.this,"Profile Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                });
                Toast.makeText(editprofile.this, "Email is Changed.", Toast.LENGTH_SHORT).show();

            }).addOnFailureListener(e -> Toast.makeText(editprofile.this,e.getMessage(), Toast.LENGTH_SHORT).show());

        }

        );

        profileEmail.setText(email);
        profileFullName.setText(fullName);
        profilePhone.setText(phone);

        Log.d(TAG, "onCreate: " + fullName +" "+email+" "+phone);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, @androidx.annotation.Nullable Intent data){
        super.onActivityResult(requestCode,resultcode,data);
        if(requestCode == 1000){
            if(resultcode == Activity.RESULT_OK){
                Uri imageUri = data.getData();


            }
        }
    }


    }