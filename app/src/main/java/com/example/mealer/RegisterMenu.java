package com.example.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterMenu extends AppCompatActivity {
    Button confirm;
    RadioGroup radioGroup;
    int id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);

        confirm = findViewById(R.id.confirm);
        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                id = radioGroup.getCheckedRadioButtonId();
                if(i == R.id.radioButton){
                    id = R.id.radioButton;
                }else if(i == R.id.radioButton2){
                    id = R.id.radioButton2;
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id == 0){
                    Toast.makeText(getApplicationContext(), "Please select your role",
                            Toast.LENGTH_SHORT).show();
                }else {
                    if (id == R.id.radioButton) {
                        Intent intent = new Intent(getApplicationContext(), RegisterClient.class);
                        startActivity(intent);
                    } else if (id == R.id.radioButton2) {
                        Intent intent2 = new Intent(getApplicationContext(), RegisterCook.class);
                        startActivity(intent2);
                    }
                }
            }
        });
    }
}