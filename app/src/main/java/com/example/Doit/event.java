package com.example.Doit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class event extends AppCompatActivity {
    EditText title;
    EditText location,email;
    EditText description;
    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        title = findViewById(R.id.etTitle);
        location = findViewById(R.id.etLocation);
        description= findViewById(R.id.etDescription);
        addEvent = findViewById(R.id.btnadd);
        email = findViewById(R.id.etEmail);
        addEvent.setOnClickListener(v -> {
            if(!title.getText().toString().isEmpty() && !location.getText().toString().isEmpty()
                    && !description.getText().toString().isEmpty() && !email.getText().toString().isEmpty()){
                Intent intent = new Intent (Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE,title.getText().toString());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION,location.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION,description.getText().toString());
                intent.putExtra(Intent.EXTRA_EMAIL,email.getText().toString());

                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else{
                    Toast.makeText(event.this, "There is no app that supports this action", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(event.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
        });
}}