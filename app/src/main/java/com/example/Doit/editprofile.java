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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class editprofile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText profileFullName,profileEmail,profilePhone;
    ImageView profileImageView;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUSer user;
    storageReference = FirebaseStorage.getInstance().getRefernce


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);


        Intent data = getTntent();
        String fullName = data.getStringExtra("fullName");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");

        fAuth =FirebasAuth.getInstance();
        fstore =FirebaseFirestaor.getInstance();
        user = fAuth.getCurrentUser();


        profileFullName = findViewById(R.id.profileFullName);
        profileEmail = findViewById(R.id.profileEmailAdress);
        profilePhone = findViewById(R.id.profilephoneNo);
        profileImageView = findViewById(R.id.profileImageView);
        saveBtn = findViewById(R.id.saveProfileInfo);

        storageReference profileRef = storageRefernce.child.("users/"+fAuth.getCurrentser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener((OnSuccessListener) (uri){
                Picasso.get().load(uri).into(profileImageView);
                })

        profileImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void(View V){
                Toast.makeText(editprofile.this,"Profile Image Clicked.",Toast.LENGTH_SHORT).show();
            }
        })
        saveBtn.setOnClickListener(new View.OnClickListener(){
                                       @Override
                                       public  void  onClick(View V){
                                           if (profileFullName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty() || profilePhone.getText().toString().isEmpty()) {
                                               Intent poenGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                               startActivityForResult(openGalleryIntent, 1000);
                                           }

                                           String email = profileEmail.getText().toString();
                                           user.updateEmail(email).addOnSucessListener(new OnSucessListener<void>(){
                                               @Override
                                               public void  onSucess(Void aVoid){
                                                   DocumentReference docRef = fstore.collection("users").document(user.getuid());
                                                   Map<String,Object> edited =new HashMap<>();
                                                   edited.put("email"email);
                                                   edited.put("fName"profileFullName.getText().toString());
                                                   edited.put("phone"profilePhone.getText().toString());
                                                   docRef.update(edited).addOnSucessListener(new OnSucessListener<void>(){
                                                       @Override
                                                       public void onSucess(Void aVoid){
                                                           Toast.makeText(editprofile.this,"Profile Updated", Toast.LENGTH_SHORT).show();
                                                           startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                           finish();
                                                       }
                                                   })
                                                   Toast.makeText(editprofile.this, "Email is Changed.", Toast.LENGTH_SHORT).show();

                                               }
                                           }).addOnFailureListener(new OnFailureListener(){
                                               @Override
                                               public void onFailure(@NonNull Exception e){
                                                   Toast.makeText(editprofile.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                               }
                                           });

                                       }
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

                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri){
        final StorageRefernce fileRef = storageReference.child("users/"+fAuth.getCurrentser().getUid()+"/profile.jpg"));

        fileRef,putFile(imageUri).addOnSucessListener() (OnSucecss(takeSnapShot){
            fileRef.getDownloadUrl().addOnSucessListener(onSucess(Uri){
                Picasso.get().load(Uri).into(profileImageView);
            });
        }).addOnFailureListener(onFailure(e){
            Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
        });
    }

