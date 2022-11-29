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
import android.widget.EditText;
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

public class RegisterCook extends AppCompatActivity {
    private DatabaseReference cookReference;
    private EditText cookFirst, cookLast, cookEmail, cookPassword, cookVoidCheque, cookAddress, cookDescription;
    private Button cookRegisterBtn;
    private String fst, lst, eml, pwd, cheque, add, description;
    private List<Cook> cookAccounts;

    private boolean ifInputsAreValid() {
        if(TextUtils.isEmpty(cookFirst.getText().toString()) || TextUtils.isEmpty(cookLast.getText().toString())
                || TextUtils.isEmpty(cookEmail.getText().toString()) || TextUtils.isEmpty(cookPassword.getText().toString())
                || TextUtils.isEmpty(cookAddress.getText().toString()) || TextUtils.isEmpty(cookDescription.getText().toString())
                || TextUtils.isEmpty(cookVoidCheque.getText().toString())){
            prompt("Please fill out all the information").show();
            return false;
        }
        // check redundant
        for(Cook a: cookAccounts){
            if(a.getLastName().equals(cookLast) && a.getFirstName().equals(cookFirst)){
                prompt("User already exists!").show();
            } else if(a.getEmail().equals(cookEmail)){
                prompt("Email already exists!").show();
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
        setContentView(R.layout.activity_register_cook);

        cookFirst = findViewById(R.id.cookFirst);
        cookLast = findViewById(R.id.cookLast);
        cookEmail = findViewById(R.id.cookEmail);
        cookPassword = findViewById(R.id.cookPassword);
        cookVoidCheque = findViewById(R.id.cookVoidCheque);
        cookAddress = findViewById(R.id.cookAddress);
        cookDescription = findViewById(R.id.cookDescription);

        cookRegisterBtn = findViewById(R.id.cookRegisterBtn);

        cookAccounts = new LinkedList<>();
        // extract the data from the database
        cookReference = FirebaseDatabase.getInstance().getReference("Cooks");

        // listen for new change in Clients
        cookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cookAccounts.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    Cook cook = child.getValue(Cook.class);
                    cook.setIsActive("active");
                    cookAccounts.add((Cook) cook);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // firstname
        cookFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                fst = editable.toString();
            }
        });

        // lastname
        cookLast.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                lst = editable.toString();
            }
        });

        // emailAddress
        cookEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                eml = editable.toString();
            }
        });

        // password
        cookPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pwd = editable.toString();
            }
        });

        // void cheque
        cookVoidCheque.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {cheque = editable.toString();}
        });

        // address
        cookAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                add = editable.toString();
            }
        });

        // user description
        cookDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                description = editable.toString();
            }
        });

        cookRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ifInputsAreValid()){
//                    String n = "0";
                    Account account = new Cook(fst, lst, eml,
                            pwd, add, cheque, description);
                    // now add accounts into the firebase by call the method push() and getKey() and setValue()
                    cookReference.child(cookReference.push().getKey()).setValue(account);
                    prompt("Welcome to Mealer!").show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                }
            }
        });
    }
}