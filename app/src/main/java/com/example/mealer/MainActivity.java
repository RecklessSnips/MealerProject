package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealer.Accounts.Account;
import com.example.mealer.Accounts.Client;
import com.example.mealer.Accounts.Cook;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference clientReference, cookReference;
    private TextView welcome;
    private Button logIn, register;
    private EditText username, password;
    private String name, combination, role;
    private CheckBox clientCheckBox, adminCheckBox, cookCheckBox;
    private Client client = null;
    private Cook cook = null;

    private List<Client> clientAccounts = new LinkedList<>();
    private List<Cook> cookAccounts = new LinkedList<>();

    // check if the inputs are valid
    private boolean ifClientInputsAreValid() {
        if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
            prompt("Please fill out all the information").show();
            return false;
        }
        // check client redundant
        for(Client a: clientAccounts){
            if(a.getEmail().equals(username.getText().toString())){
                client = a;
            }
        }
        if(client == null){
            prompt("user doesn't exists!").show();
            return false;
        }else {
            if (!client.getPwd().equals(password.getText().toString())) {
                prompt("password is incorrect!").show();
                return false;
            }
        }
        return true;
    }
    private boolean ifCookInputsAreValid() {
        if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
            prompt("Please fill out all the information").show();
            return false;
        }
        // check cook redundant
        for(Cook a : cookAccounts){
            if(a.getEmail().equals(username.getText().toString())){
                cook = a;
            }
        }
        if(cook == null){
            prompt("staff doesn't exists!").show();
            return false;
        }else {
            if (!cook.getPwd().equals(password.getText().toString())) {
                prompt("password is incorrect!").show();
                return false;
            }
        }
        return true;
    }

    private Toast prompt(String text){
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        return toast;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Edit text
        logIn = findViewById(R.id.login);
        register = findViewById(R.id.register);
        // user info
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        // checkBox *************************************************************************
        clientCheckBox = findViewById(R.id.clientCheckbox);
        adminCheckBox = findViewById(R.id.adminCheckBox);
        cookCheckBox = findViewById(R.id.cookCheckBox);

        clientCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    role = "Client";
            }
        });

        adminCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    role = "Admin";
            }
        });

        cookCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    role = "Cook";
            }
        });

        // connect to database **************************************************************
        clientReference = FirebaseDatabase.getInstance().getReference("Clients");
        cookReference = FirebaseDatabase.getInstance().getReference("Cooks");

        clientReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()){
                    clientAccounts.clear();
                    Account client = child.getValue(Client.class);
                    clientAccounts.add((Client) client);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()){
                    cookAccounts.clear();
                    Account cook = child.getValue(Cook.class);
                    cookAccounts.add((Cook) cook);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // realizing buttons and text views *************************************************

        // keep listening from user's inputs
        // after onCreate() is finished
        username.addTextChangedListener(new TextWatcher() {
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

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                combination = editable.toString();
            }
        });

        // realizing log in button
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clientCheckBox.isChecked()) {
                    if (ifClientInputsAreValid()) {
                        Intent i = new Intent(getApplicationContext(), WelcomeMenu.class);
                        i.putExtra("name", name);
                        i.putExtra("role", role);
                        startActivity(i);
                    }
                } else if(cookCheckBox.isChecked()) {
                    if (ifCookInputsAreValid()) {
                        Intent i = new Intent(getApplicationContext(), WelcomeMenu.class);
                        i.putExtra("name", name);
                        i.putExtra("role", role);
                        startActivity(i);
                    }
                }else{
                    prompt("Please select your role!").show();
                }

//                startActivity(i);
            }
        });

        // realizing register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterMenu.class);
                startActivity(i);
            }
        });
    }



}