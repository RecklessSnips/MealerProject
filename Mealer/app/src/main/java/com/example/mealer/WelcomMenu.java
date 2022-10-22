package com.example.mealer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mealer.databinding.ActivityWelcomMenuBinding;

public class WelcomMenu extends AppCompatActivity {
    private TextView message;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_menu);
        message = findViewById(R.id.message);
//
//        Bundle extras = getIntent().getExtras();
//        int type = extras.getInt("type");
//        TextView message=findViewById(R.id.message);
//
//
//        if (type==1){
//            message.setText("Welcome! You are logged in as Client");
//
//        }
//        else if (type==0){
//            message.setText("Welcome! You are logged in as Cook");
//        }
        Intent i = getIntent();
        message.setText("Welcome "+ i.getStringExtra("firstName") + "! You have signed as a "
                + i.getStringExtra("role") + "!");

        Button logoff=findViewById(R.id.logOff);
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
