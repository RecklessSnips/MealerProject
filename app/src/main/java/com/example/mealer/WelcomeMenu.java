package com.example.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mealer.Accounts.Account;

public class WelcomeMenu extends AppCompatActivity {
    private TextView welcomeMenu_msg;
    private String name;
    private String role;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_menu);

        welcomeMenu_msg = findViewById(R.id.welcomeMenu_msg);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        role = i.getStringExtra("role");
        welcomeMenu_msg.setText("Welcome " + name + ", you have signed in as a "
                + role + "!");
    }
}