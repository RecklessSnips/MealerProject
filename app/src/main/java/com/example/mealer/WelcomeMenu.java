package com.example.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealer.Accounts.Account;

public class WelcomeMenu extends AppCompatActivity {
    private TextView welcomeMenu_msg, textRole;
    private String name, role, id;
    private Button order, ordered, logoff_1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_menu);

        welcomeMenu_msg = findViewById(R.id.welcomeMenu_msg);
//        textRole = findViewById(R.id.role);
        order = findViewById(R.id.order);
        ordered = findViewById(R.id.ordered);
        logoff_1 = findViewById(R.id.logoff_1);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        role = i.getStringExtra("role");
        id = i.getStringExtra("ClientID");

        welcomeMenu_msg.setText(name + ", you have signed in as a "
                + role + "!");

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClientSearch.class);
                intent.putExtra("client_id", id);
                startActivity(intent);
            }
        });

        ordered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MealStatus.class);
                intent.putExtra("client_id", id);
                startActivity(intent);
            }
        });

        logoff_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(getApplicationContext(),"Logged off!", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
    }
}
