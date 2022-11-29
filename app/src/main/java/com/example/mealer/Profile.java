package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    private String rating, cookID;
    private TextView rate, numberSold;
    private ArrayList<Double> arrayList;
    private double sum;
    private Button logoff_4;

    private DatabaseReference complaintsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i = getIntent();
        cookID = i.getStringExtra("CookID");

        rate = findViewById(R.id.rate);
        numberSold = findViewById(R.id.numberSold);
        logoff_4 = findViewById(R.id.logoff_4);

        arrayList = new ArrayList<>();

        complaintsReference = FirebaseDatabase.getInstance().getReference("Complaints");

        complaintsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot child : snapshot.getChildren()){
//                    System.out.println(child);
                    if(((String)child.child("cookID").getValue()).equals(cookID)){
                        arrayList.add(Double.valueOf((String) child.child("rating").getValue()));
                    }
                }

//                add together
                for(int i = 0; i< arrayList.size(); i++){
                    sum += arrayList.get(i);
                }
                rating = String.valueOf(sum / arrayList.size());
                rate.setText(rating);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logoff_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(getApplicationContext(),"Logged off!", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

    }
}