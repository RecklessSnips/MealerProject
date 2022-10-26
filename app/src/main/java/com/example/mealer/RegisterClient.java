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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class RegisterClient extends AppCompatActivity {
    private DatabaseReference clientReference;
    private EditText clientFirst, clientLast, clientEmail, clientPassword, clientAddress, clientCreditCard;
    private Button clientRegisterBtn;
    private String first, last, email, password, address, creditCard;
    private List<Client> clientAccounts = new LinkedList<>();

    private boolean ifInputsAreValid() {
        if(TextUtils.isEmpty(clientFirst.getText().toString()) || TextUtils.isEmpty(clientLast.getText().toString())
                || TextUtils.isEmpty(clientEmail.getText().toString()) || TextUtils.isEmpty(clientPassword.getText().toString())
                || TextUtils.isEmpty(clientAddress.getText().toString()) || TextUtils.isEmpty(clientCreditCard.getText().toString())){
            prompt("Please fill out all the information").show();
            return false;
        }
        // check redundant
        for(Client a: clientAccounts){
            if(a.getLastName().equals(clientLast) && a.getFirstName().equals(clientFirst)){
                prompt("User already exists!").show();
            } else if(a.getEmail().equals(clientEmail)){
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
        setContentView(R.layout.activity_register_client);

        clientFirst = findViewById(R.id.clientFirst);
        clientLast = findViewById(R.id.clientLast);
        clientEmail = findViewById(R.id.clientEmail);
        clientPassword = findViewById(R.id.clientPassword);
        clientAddress = findViewById(R.id.clientAddress);
        clientCreditCard = findViewById(R.id.clientCreditCard);
        clientRegisterBtn = findViewById(R.id.clientRegisterBtn);

        // extract the data from the database
        clientReference = FirebaseDatabase.getInstance().getReference("Clients");

        // listen for new change in Clients
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

        // firstname
        clientFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                first = editable.toString();
            }
        });

        // lastname
        clientLast.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                last = editable.toString();
            }
        });

        // emailAddress
        clientEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email = editable.toString();
            }
        });

        // password
        clientPassword.addTextChangedListener(new TextWatcher() {
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
        clientAddress.addTextChangedListener(new TextWatcher() {
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

        // credit card
        clientCreditCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                creditCard = editable.toString();
            }
        });

        clientRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ifInputsAreValid()){
                    Account account = new Client(first, last, email,
                            password,address,creditCard);
                    // now add accounts into the firebase by call the method push() and getKey() and setValue()
                    clientReference.child(clientReference.push().getKey()).setValue(account);
                    prompt("Registration successfully!").show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                }
            }
        });
    }
}