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

import com.example.mealer.Accounts.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class MenuItem extends AppCompatActivity {
    private TextView meal_id, meal_name;
    private String id;
    private String name;
    private String cook_id;
    private Button offer, delete;

    // in this class we can add meal to the offer menu
    // so we need to use data base

    // need menuReference to delete
    private DatabaseReference offerReference, menuReference;
    private List<Meal> offeredMeal;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        Intent intent = getIntent();
        id = intent.getStringExtra("meal-ID");
        name = intent.getStringExtra("meal-Name");
        cook_id = intent.getStringExtra("cook-ID");

        meal_id = findViewById(R.id.m__id);
        meal_name = findViewById(R.id.m__name);

        offer = findViewById(R.id.offer);
        delete  = findViewById(R.id.delete);

        meal_id.setText(id);
        meal_name.setText(name);

        // locate the data base path we want to put the data
        offerReference = FirebaseDatabase.getInstance().getReference("Offer/" + cook_id);
        menuReference = FirebaseDatabase.getInstance().getReference("Menu/" + cook_id);
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
//                System.out.println(offeredMeal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // need to check if this meal has already
                // been added to the offer list
//                System.out.println(name);             Just for testing
//                System.out.println(cook_id);          Just for testing
//                System.out.println(id);               //Just for testing
                Meal meal = new Meal(name, cook_id);
                offerReference.child(id).setValue(meal);
//                System.out.println(offeredMeal);
                //Just for testing

                for(Meal m : offeredMeal){
                    if (m.getMealID().equals(id)){
                        Toast toast1 = Toast.makeText(getApplicationContext(),
                                "meal has been added", Toast.LENGTH_SHORT);
                        toast1.show();
//                        add return here to end the method
//                        otherwise it will run the next Toast
//                        :(
                        return;
                    }
                }
//                Can't use else in side the for loop!
//                it will cause ethe toast to run twice when
//                adding them twice!    :(
                Toast toast = Toast.makeText(getApplicationContext(),
                        "add successfully!", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });

        // delete the meal
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(Meal m : offeredMeal){
                    if (m.getMealID().equals(id)){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Can't delete!", Toast.LENGTH_SHORT);
                        toast.show();
//                        is really important to add the return
//                        statement here, because we DO NOT want to
//                        exit the current page if the meal is in the
//                        offer menu
                        return;
                    }
                }
//                if no redundancy, then we will delete the meal
//                normally from the menu list
                menuReference.child(id).removeValue();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "delete successfully!", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });
    }
}