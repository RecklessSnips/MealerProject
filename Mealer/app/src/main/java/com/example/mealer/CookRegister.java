package com.example.mealer;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.mealer.datas.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

//import com.example.mealer.databinding.ActivityCookRegisterBinding;

public class CookRegister extends AppCompatActivity {
    private DatabaseReference accountsReference;
    // store info into accounts(linkedList)
    private LinkedList<Account> accounts = new LinkedList<>();
    private EditText editFirstName1, editLastName1, editEmailAddress1,
            editAccountPass1, editAddress1, editTextTextUserDescription;
    private String firstname, lastname, emailAddress, password, address, userDescription ;

    private boolean ifInputsAreValid() {
        if(TextUtils.isEmpty(editFirstName1.getText().toString()) || TextUtils.isEmpty(editLastName1.getText().toString())
                || TextUtils.isEmpty(editEmailAddress1.getText().toString()) || TextUtils.isEmpty(editAccountPass1.getText().toString())
                || TextUtils.isEmpty(editAddress1.getText().toString()) || TextUtils.isEmpty(editTextTextUserDescription.getText().toString())){
            prompt("Please fill out all the information").show();
            return false;
        }
        // check redundant
        for(Account a: accounts){
            if(a.getLastName().equals(lastname) && a.getFirstName().equals(firstname)){
                prompt("User already exists!").show();
            } else if(a.getEmailAddress().equals(emailAddress)){
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
        setContentView(R.layout.activity_cook_register);

        editFirstName1 =  findViewById(R.id.editFirstName1);
        editLastName1 =  findViewById(R.id.editLastName1);
        editEmailAddress1 = findViewById(R.id.editEmailAddress1);
        editAccountPass1 = findViewById(R.id.editAccountPass1);
        editAddress1 = findViewById(R.id.editAddress1);
        editTextTextUserDescription = findViewById(R.id.editTextTextUserDescription);

        accountsReference = FirebaseDatabase.getInstance().getReference("Cooks");


        // firstname
        editFirstName1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                firstname = editable.toString();
            }
        });

        // lastname
        editLastName1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                lastname = editable.toString();
            }
        });

        // emailAddress
        editEmailAddress1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                emailAddress = editable.toString();
            }
        });

        // password
        editAccountPass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = editable.toString();
            }
        });

        // address
        editAddress1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                address = editable.toString();
            }
        });

        // user description
        editTextTextUserDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userDescription = editable.toString();
            }
        });

        accountsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // clear the database otherwise it will print all the accounts in hietory
                accounts.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    Account account = child.getValue(Account.class);
                    accounts.add(account);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Button btn =findViewById(R.id.RegisterCook);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ifInputsAreValid()) {
                    Account account = new Account(firstname, lastname, emailAddress,
                            password,address,userDescription);
                    // now add accounts into the firebase by call the method push() and getKey() and setValue()
                    accountsReference.child(accountsReference.push().getKey()).setValue(account);
                    Intent intent = new Intent(getApplicationContext(), WelcomMenu.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                }
            }
        });
    }
}