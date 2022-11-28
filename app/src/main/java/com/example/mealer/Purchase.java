package com.example.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mealer.Accounts.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Purchase extends AppCompatActivity {
    private TextView meal, cook, iD;
    private String mealName, cookID, mealID, clientID;
    private Button purchase;

    private DatabaseReference requestReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        Intent intent = getIntent();
        mealName = intent.getStringExtra("mealName");
        cookID = intent.getStringExtra("cookID");
        mealID = intent.getStringExtra("mealID");
        clientID = intent.getStringExtra("client_ID");

        meal = findViewById(R.id.name);
        cook = findViewById(R.id.id);
        iD = findViewById(R.id.mealid);

        meal.setText(mealName);
        cook.setText(cookID);
        iD.setText(mealID);

        purchase = findViewById(R.id.purchase);

        requestReference = FirebaseDatabase.getInstance().getReference("Request");

//        requestReference.push().setValue(new Request("client-1" ,meal, Status.PENDING));
//        finish();

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestReference.push().setValue(new Request(clientID, mealName, "pending"));
//                System.out.println(clientID);
                finish();
            }
        });
    }
}