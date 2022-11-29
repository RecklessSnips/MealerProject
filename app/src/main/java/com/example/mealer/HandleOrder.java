package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mealer.Accounts.Cook;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HandleOrder extends AppCompatActivity {
    private TextView food, cli, reID, time;
    private String foodName, clientID, requestID, pickTime, cookID;
    private Button approve, reject;
    private DatabaseReference requestReference, cookReference;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_order);

        food = findViewById(R.id.meal);
        cli = findViewById(R.id.client);
        reID = findViewById(R.id.requestID);
        time = findViewById(R.id.time);

        approve = findViewById(R.id.approve);
        reject = findViewById(R.id.reject);

        Intent i = getIntent();
        foodName = i.getStringExtra("foodName");
        clientID = i.getStringExtra("clientId");
        requestID = i.getStringExtra("requestID");

        requestReference = FirebaseDatabase.getInstance().getReference("Request/" + requestID);
        cookReference = FirebaseDatabase.getInstance().getReference("Cooks");

        food.setText(foodName);
        cli.setText(clientID);
        reID.setText(requestID);

        requestReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println(requestID);
                pickTime = (String) snapshot.child("pickUpTime").getValue();
                time.setText(pickTime);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestReference.child("status").setValue("approved");
//                cookID =  requestReference.child("cookID").toString();
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

//        cookReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println(cookID);
////                System.out.println(cookReference.child(cookID));
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }
}