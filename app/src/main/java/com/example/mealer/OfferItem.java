package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealer.Accounts.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class OfferItem extends AppCompatActivity {
    private TextView meal_id, meal_name;
    private String id;
    private String name;
    private String cook_id;
    private Button delete;

    // in this class we can add meal to the offer menu
    // so we need to use data base
    private DatabaseReference offerReference;
    private List<Meal> offeredMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_item);

        Intent intent = getIntent();
        id = intent.getStringExtra("meal-ID");
        name = intent.getStringExtra("meal-Name");
        cook_id = intent.getStringExtra("cook-ID");

        meal_id = findViewById(R.id.m__id);
        meal_name = findViewById(R.id.m__name);

        delete  = findViewById(R.id.delete);

        meal_id.setText(id);
        meal_name.setText(name);

        offerReference = FirebaseDatabase.getInstance().getReference("Offer/" + cook_id);
        offeredMeal = new LinkedList<>();


        offerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offeredMeal.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    Meal m = child.getValue(Meal.class);
                    m.setMealID(child.getKey());
                    offeredMeal.add(m);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // already in the cookID child path, so we can
        // remove from the offer list
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offerReference.child(id).removeValue();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "delete from the offer menu", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });

    }
}