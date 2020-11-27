package com.example.Doit;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    Firebase fAuth;
    FirebaseFirestore fstore;
    TextView notverified;
    Button verifybtn;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notverified =findViewById(R.id.notverified);
        verifybtn =findViewById(R.id.verifybtn);

        userid =fAuth.getCurrentUser().getUid();
        FirebaseUser user =fAuth.getCurrentUser();

        if(!user.isEmailVerified()){
            notverified.setVisibility(View.VISIBLE);
            notverified.setVisibility(View.VISIBLE);

            notverified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser fuser = fAuth.getCurrentUser();
                    user.sendEmailVerification().addOnSucessListener(new OnSucessListener<Void>(){
                        @Override
                        public volatile onSucess(Void aVoid){
                            Toast.makeText(v.getContext().this,"Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener(){
                        @Override
                        public volatile onFailure(@NonNullException e){
                            Log.d("tag", "On Failure Email not sent" + e.getmessage())
                        }
                    });
                }
            });
        }

    }


    public void employee(View view) {
        startActivity(new Intent(getApplicationContext(), Employee.class));
    }
}