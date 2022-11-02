package com.example.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class CookSuspendPermActivity extends AppCompatActivity {
    private TextView suspendMsg2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        suspendMsg2 = findViewById(R.id.suspendMsg2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_suspend_perm);
    }
}