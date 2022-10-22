package com.example.mealer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner userTypes= findViewById(R.id.UserTypes);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.Users, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        userTypes.setAdapter(adapter);


        Button next = findViewById(R.id.next1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = userTypes.getSelectedItem().toString();
                if (user.equals("Client")) {
                    Intent intent = new Intent(getApplicationContext(), ClientRegister.class);
                    startActivity(intent);
                }
                else if (user.equals("Cook")){
                    Intent intent = new Intent(getApplicationContext(),CookRegister.class);
                    startActivity(intent);
                }


            }
        });

    }

}