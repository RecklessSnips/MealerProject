package com.example.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HandleOrder extends AppCompatActivity {
    private TextView food, cli, reID;
    private String foodName, clientID, requestID;
    private Button approve, reject;
    private DatabaseReference requestReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_order);

        food = findViewById(R.id.meal);
        cli = findViewById(R.id.client);
        reID = findViewById(R.id.requestID);


        approve = findViewById(R.id.approve);
        reject = findViewById(R.id.reject);

        Intent i = getIntent();
        foodName = i.getStringExtra("foodName");
        clientID = i.getStringExtra("clientId");
        requestID = i.getStringExtra("requestID");

        requestReference = FirebaseDatabase.getInstance().getReference("Request/" + requestID);

        food.setText(foodName);
        cli.setText(clientID);
        reID.setText(requestID);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestReference.child("status").setValue("approved");
                finish();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestReference.child("status").setValue("rejected");
                finish();
            }
        });
    }
}