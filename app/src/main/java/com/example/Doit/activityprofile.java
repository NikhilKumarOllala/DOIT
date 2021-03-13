package com.example.Doit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.auth.User;

public class activityprofile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityprofile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userid = user.getUid();

        final TextView greetingtextview = findViewById(R.id.greetings);
        final TextView fullnametextview =  findViewById(R.id.nameadress);
        final TextView emailtextview = findViewById(R.id.emailadress);
        final TextView phonetextview =  findViewById(R.id.phonedress);

        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null) {
                    String fullName = user.getDisplayName();
                    String number = user.getPhoneNumber();
                    String email = user.getEmail();

                    greetingtextview.setText("Your Profile!");
                    fullnametextview.setText(fullName);
                    emailtextview.setText(email);
                    phonetextview.setText(number);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activityprofile.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
            }

        });


    }

    public void edit(View view) {
        Intent ie = new Intent(this, activityprofile.class);
        startActivity(ie);
    }
}