package com.example.Doit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void employee(View view) {
        startActivity(new Intent(getApplicationContext(), Employee.class));
    }
    public void student(View view) {
        startActivity(new Intent(getApplicationContext(), Employee.class));
    }
    public void homemaker(View view) {
        startActivity(new Intent(getApplicationContext(), Employee.class));
    }
    public void others(View view) {
        startActivity(new Intent(getApplicationContext(), Employee.class));
    }
}