package com.example.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CookActivity extends AppCompatActivity {
    private TextView cookWelcomeMsg;
    private Button mealMenu, offerMenu, addMeal;
    private String name;
    private String role;
    private String cookID;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);

        cookWelcomeMsg = findViewById(R.id.cookWelcomeMsg);

        mealMenu = findViewById(R.id.mealMenu);
        offerMenu = findViewById(R.id.offerMenu);
        addMeal = findViewById(R.id.addMeal);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        role = i.getStringExtra("role");
        cookID = i.getStringExtra("CookID");
//        System.out.println(name);
//        System.out.println(role);
        cookWelcomeMsg.setText("Welcome " + name + ", you have signed in as a "
                + role + "!");

        // we need to pass the cookID and the cook name to the
        // following class whatsoever
        mealMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                intent.putExtra("Name", name);
                intent.putExtra("CookID", cookID);
                startActivity(intent);
            }
        });

        offerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Offer.class);
                intent.putExtra("Name", name);
                intent.putExtra("CookID", cookID);
                startActivity(intent);
            }
        });

        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreatMeal.class);
                intent.putExtra("Name", name);
                intent.putExtra("CookID", cookID);
                startActivity(intent);
            }
        });
    }

    public void updateTextView(String toThis) {
        cookWelcomeMsg = (TextView) findViewById(R.id.cookWelcomeMsg);
        cookWelcomeMsg.setText(toThis);
    }
}
