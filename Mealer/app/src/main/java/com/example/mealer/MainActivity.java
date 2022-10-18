package com.example.mealer;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mealer.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
                    Intent intent = new Intent(getApplicationContext(), clientRegister.class);
                    startActivityForResult(intent, 0);
                }
                else if (user.equals("Cook")){
                    Intent intent = new Intent(getApplicationContext(),cookRegister.class);
                    startActivityForResult(intent, 0);
                }


            }
        });

    }

}