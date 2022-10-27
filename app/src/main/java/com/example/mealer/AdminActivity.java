package com.example.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity {
    private TextView adminWelcomeMsg;
    private String name;
    private String role;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminWelcomeMsg = findViewById(R.id.adminWelcomeMsg);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        role = i.getStringExtra("role");
        adminWelcomeMsg.setText("Welcome " + name + ", you have signed in as a "
                + role + "!");
    }
}