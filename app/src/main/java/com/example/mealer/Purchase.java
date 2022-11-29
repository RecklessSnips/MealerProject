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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealer.Accounts.Cook;
import com.example.mealer.Accounts.Meal;
import com.example.mealer.Accounts.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Purchase extends AppCompatActivity {
    private TextView meal, cook, iD, address_1, description_1;
    private TextView price_1, type_1, cuisine_1, ingredients_1, allergrens_1, description_2;
    private String mealName, cookID, mealID, clientID;
    private String cookName, address, description, pickUpDate;
    private String mealPrice, mealType, mealCuisine, mealIngredients, mealAllergrens, mealDescription;
    private Button purchase;
    private EditText pick;


    private DatabaseReference cookReference;
    private DatabaseReference offerReference;
    private DatabaseReference requestReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        Intent intent = getIntent();
        // crucial, to see which id is getting passed, and
        // open the info regrading to this
        mealName = intent.getStringExtra("mealName");
        cookID = intent.getStringExtra("cookID");
        mealID = intent.getStringExtra("mealID");
        clientID = intent.getStringExtra("client_ID");

        pick = findViewById(R.id.pick);

        meal = findViewById(R.id.name);
        cook = findViewById(R.id.id);
        iD = findViewById(R.id.mealid);
        address_1 = findViewById(R.id.address);
        description_1 = findViewById(R.id.description_1);
//        private TextView price_1, type_1, cuisine_1, ingredients_1, allegrens_1, description_2;
        price_1 = findViewById(R.id.price_1);
        type_1 = findViewById(R.id.type_1);
        cuisine_1 = findViewById(R.id.cuisine_1);
        ingredients_1 = findViewById(R.id.ingredients_1);
        allergrens_1 = findViewById(R.id.allegrens_1);
        description_2 = findViewById(R.id.description_2);

        meal.setText(mealName);
//        cook.setText(cookID);
        iD.setText(mealID);

        purchase = findViewById(R.id.purchase);

        requestReference = FirebaseDatabase.getInstance().getReference("Request");
        cookReference = FirebaseDatabase.getInstance().getReference("Cooks/" + cookID);
        offerReference = FirebaseDatabase.getInstance().getReference("Offer/" + cookID);
//        requestReference.push().setValue(new Request("client-1" ,meal, Status.PENDING));
//        finish();
        cookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cookName = (String) snapshot.child("firstName").getValue();
                address = (String) snapshot.child("address").getValue();
                description = (String) snapshot.child("description").getValue();
//                System.out.println(child);
//                System.out.println(cook);

//                System.out.println(cookID);
//                System.out.println(address);
//                System.out.println(description);
//                System.out.println(cookName);
                address_1.setText(address);
                description_1.setText(description);
                cook.setText(cookName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Different from the COOK!!!
        // dont directly use the snapshot.child()!!!
        offerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println(snapshot);
//                System.out.println(cookID);
//                System.out.println(mealID);
                for(DataSnapshot child : snapshot.getChildren()) {
                    // otherwise it always use the last child as reference
                    if(mealID.equals(child.getKey())) {
                        mealPrice = (String) child.child("price").getValue();
                        mealType = (String) child.child("mealType").getValue();
                        mealCuisine = (String) child.child("cuisineType").getValue();
                        mealIngredients = (String) child.child("ingredients").getValue();
                        mealAllergrens = (String) child.child("allergens").getValue();
                        mealDescription = (String) child.child("description").getValue();
//                        System.out.println(mealPrice);
//                        System.out.println(mealType);
//                        System.out.println(mealCuisine);
//                        System.out.println(mealIngredients);
//                        System.out.println(mealAllergrens);
//                        System.out.println(mealDescription);
                        price_1.setText(mealPrice);
                        type_1.setText(mealType);
                        cuisine_1.setText(mealCuisine);
                        ingredients_1.setText(mealIngredients);
                        allergrens_1.setText(mealAllergrens);
                        description_2.setText(mealDescription);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pickUpDate = editable.toString();
            }
        });


        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pickUpDate == null){
                    Toast t = Toast.makeText(getApplicationContext(), "Please enter a date",
                            Toast.LENGTH_SHORT);
                    t.show();
                }else {
                    requestReference.push().setValue(new Request(clientID, cookID, mealName, "pending", pickUpDate));
//                System.out.println(clientID);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Request successfully!", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }
        });
    }
}