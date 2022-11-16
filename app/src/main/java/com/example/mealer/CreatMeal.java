package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mealer.Accounts.Client;
import com.example.mealer.Accounts.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

// this class will create a Meal list on to the database
// which is very convenience for later retrieving (mean list, offer list)
public class CreatMeal extends AppCompatActivity {
    private EditText mealName, mealType, cuisineType, ingredients, allergens, price, description;
    private Button queDing;
    private String name, type, cuisine, ing, aller, p, descrip;
    private String chuzi, id;
    private List<Meal> mealList;

    private DatabaseReference mealReference;

    private boolean ifInputsAreValid() {
        if(TextUtils.isEmpty(mealName.getText().toString()) || TextUtils.isEmpty(mealType.getText().toString())
                || TextUtils.isEmpty(cuisineType.getText().toString()) || TextUtils.isEmpty(ingredients.getText().toString())
                || TextUtils.isEmpty(allergens.getText().toString()) || TextUtils.isEmpty(price.getText().toString())
                || TextUtils.isEmpty((description.getText().toString()))){
            prompt("Please fill out all the information").show();
            return false;
        }
        // check redundant
        for(Meal m: mealList){
//            check if the input meal name exists in the existing list
            if(m.getMealName().equals(name)){
                prompt("Meal already exists!").show();
            }
        }
        return true;

    }

    private Toast prompt(String text){
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        return toast;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_meal);

        Intent intent = getIntent();
        chuzi = intent.getStringExtra("Name");
        id = intent.getStringExtra("CookID");

        mealName = findViewById(R.id.mealName);
        mealType = findViewById(R.id.mealType);
        cuisineType = findViewById(R.id.cuisineType);
        ingredients = findViewById(R.id.ingredients);
        allergens = findViewById(R.id.allergens);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);

        queDing = findViewById(R.id.queDing);

        mealList = new LinkedList<>();

        // we have specified the path here
        // no longer need to set the child.getKey()
        // when pushing them
        mealReference = FirebaseDatabase.getInstance().getReference("Menu/" + id);

        mealReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()){
                    Meal m = child.getValue(Meal.class);
                    mealList.add(m);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // meal name
        mealName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                name = editable.toString();
            }
        });

        // meal type
        mealType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                type = editable.toString();
            }
        });

        // cuisine
        cuisineType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                cuisine = editable.toString();
            }
        });

        // ingredients
        ingredients.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ing = editable.toString();
            }
        });

        allergens.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                aller = editable.toString();
            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                p = editable.toString();
            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                descrip = editable.toString();
            }
        });

        queDing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifInputsAreValid()){
                    Meal meal = new Meal(id, name, type, cuisine, ing, aller, p, descrip);
                    mealReference.push().setValue(meal);

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "add successfully!", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }
        });
    }

    // manually create
    public void create(){
        String[] meal = new String[]{"rice", "noods", "hi", "ji", "ui", "io","pp"};
        for(String m : meal){
            Meal meal2 = new Meal(m, id);
            mealReference.push().setValue(meal2);
        }
    }
}