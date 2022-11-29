package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.mealer.Accounts.ClientComplaints;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Evaluation extends AppCompatActivity {
    private RatingBar ratingBar;
    private EditText complaints;
    private Button sendComplaints;
    private String complaint;
    private String requestID;
    private String clientID, cookID, rating;

    private DatabaseReference requestReference;
    private DatabaseReference complaintReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        Intent i = getIntent();
        requestID = i.getStringExtra("requestID");
        System.out.println(requestID);

        ratingBar = findViewById(R.id.ratingBar);
        complaints = findViewById(R.id.com);
        sendComplaints = findViewById(R.id.sendComplaints);

        requestReference = FirebaseDatabase.getInstance().getReference("Request/" + requestID);
        complaintReference = FirebaseDatabase.getInstance().getReference("Complaints");

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = String.valueOf(v);
                Toast.makeText(getApplicationContext(), String.valueOf(v) + " star!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        complaints.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                complaint = editable.toString();
            }
        });

        requestReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientID = (String) snapshot.child("clientId").getValue();
                cookID = (String) snapshot.child("cookID").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(complaint == null){
                    Toast.makeText(getApplicationContext(), "Please enter your complaints",
                            Toast.LENGTH_SHORT).show();
                } else {
                    complaintReference.push().setValue(new ClientComplaints(complaint, clientID, cookID, rating));
                    Toast.makeText(getApplicationContext(), "Thanks for your feedback!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}