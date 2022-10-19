package com.example.mealer;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mealer.databinding.ActivityWelcomMenuBinding;

public class WelcomMenu extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcom_menu);

        Bundle extras = getIntent().getExtras();
        int type = extras.getInt("type");
        TextView message=findViewById(R.id.message);


        if (type==1){
            message.setText("Welcome! You are logged in as Client");

        }
        else if (type==0){
            message.setText("Welcome! You are logged in as Cook");
        }
    }

}