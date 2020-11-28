
package com.example.Doit;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

public class settings extends AppCompatActivity {
    Button mchangepass,medit;
    TextView t2;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mchangepass = findViewById(R.id.changepass);
        medit = findViewById(R.id.editprofile);
        mchangepass.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), editprofile.class)));
        medit.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), editprofile.class)));
        t2 = (TextView) findViewById(R.id.fbText);
        fAuth = FirebaseAuth.getInstance();


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.calender);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.Tasks:
                    startActivity(new Intent(getApplicationContext(), tasks.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.calender:
                    startActivity(new Intent(getApplicationContext(), calender.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.settings:
                    return true;

            }

            return false;
        });
    }





}