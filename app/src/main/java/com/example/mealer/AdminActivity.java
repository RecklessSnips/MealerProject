package com.example.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {
    private TextView adminWelcomeMsg;
    private String name;
    private String role;
    private DatabaseReference admin;

    private Button checkComplaintsBTN, logOff;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        logOff=findViewById(R.id.logOff);
        adminWelcomeMsg = findViewById(R.id.adminWelcomeMsg);
        checkComplaintsBTN = findViewById(R.id.checkComplaintsBTN);

        admin = FirebaseDatabase.getInstance().getReference("admin");

        Intent i = getIntent();
        name = i.getStringExtra("name");
        role = i.getStringExtra("role");
        adminWelcomeMsg.setText("Welcome " + name + ", you have signed in as an "
                + role + "!");

        checkComplaintsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminManagement.class);
                startActivity(intent);
            }
        });
        logOff.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        }));

    }
}