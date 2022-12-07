package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealer.Accounts.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeMenu extends AppCompatActivity {
    private TextView welcomeMenu_msg, textRole, textView18;
    private String name, role, id, firstname;
    private Button order, ordered, logoff_1;
    private DatabaseReference client;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_menu);

        welcomeMenu_msg = findViewById(R.id.welcomeMenu_msg);

        client = FirebaseDatabase.getInstance().getReference("Clients");
//        textRole = findViewById(R.id.role);
        order = findViewById(R.id.order);
        ordered = findViewById(R.id.ordered);
        logoff_1 = findViewById(R.id.logoff_1);
        textView18 = findViewById(R.id.textView18);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        role = i.getStringExtra("role");
        id = i.getStringExtra("ClientID");

        client.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()){
//                    System.out.println(child.child("firstName"));
//                    System.out.println(child.getKey());
                    if(child.getKey().equals(id)){
                        firstname = (String)child.child("firstName").getValue();
//                        System.out.println(firstname);
                        textView18.setText("Welcome " + firstname + "!");
                    }
                }
//                System.out.println(cookID);
//                System.out.println(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        welcomeMenu_msg.setText("you have signed in as a "
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
