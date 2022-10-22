package com.example.mealer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.mealer.datas.Account;
import com.example.mealer.datas.ClientAccount;
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

//import com.example.mealer.databinding.ActivityCookRegisterBinding;

public class ClientRegister extends AppCompatActivity {
    private DatabaseReference accountsReference;
    // store info into accounts(linkedList)
    private LinkedList<ClientAccount> accounts = new LinkedList<>();
    private EditText editFirstName, editLastName, editEmailAddress,
            accountPassword, editAddress, editCreditCardInfo;
    private String clientFirstname, clientLastname, clientEmailAddress, clientPassword, clientAddress, clientEditCreditCardInfo ;

    private boolean ifInputsAreValid() {
        if(TextUtils.isEmpty(editFirstName.getText().toString()) || TextUtils.isEmpty(editLastName.getText().toString())
                || TextUtils.isEmpty(editEmailAddress.getText().toString()) || TextUtils.isEmpty(accountPassword.getText().toString())
                || TextUtils.isEmpty(editAddress.getText().toString()) || TextUtils.isEmpty(editCreditCardInfo.getText().toString())){
            prompt("Please fill out all the information").show();
            return false;
        }
        // check redundant
        for(ClientAccount a: accounts){
            if(a.getLastName().equals(clientLastname) && a.getFirstName().equals(clientFirstname)){
                prompt("User already exists!").show();
            } else if(a.getEmailAddress().equals(clientEmailAddress)){
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
        setContentView(R.layout.activity_client_register);

        editFirstName =  findViewById(R.id.editFirstName);
        editLastName =  findViewById(R.id.editLastName);
        editEmailAddress = findViewById(R.id.editEmailAddress);
        accountPassword = findViewById(R.id.accountPassword);
        editAddress = findViewById(R.id.editAddress);
        editCreditCardInfo = findViewById(R.id.editCreditCardInfo);

        accountsReference = FirebaseDatabase.getInstance().getReference("Clients");


        // firstname
        editFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                clientFirstname = editable.toString();
            }
        });

        // lastname
        editLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                clientLastname = editable.toString();
            }
        });

        // emailAddress
        editEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                clientEmailAddress = editable.toString();
            }
        });

        // password
        accountPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                clientPassword = editable.toString();
            }
        });

        // address
        editAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                clientAddress = editable.toString();
            }
        });

        // user description
        editCreditCardInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                clientEditCreditCardInfo = editable.toString();
            }
        });

        accountsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // clear the database otherwise it will print all the accounts in hietory
                accounts.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    ClientAccount account = child.getValue(ClientAccount.class);
                    accounts.add(account);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Button btn =findViewById(R.id.RegisterClient);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ifInputsAreValid()) {
                    ClientAccount account = new ClientAccount(clientFirstname, clientLastname, clientEmailAddress,
                            clientPassword,clientAddress,clientEditCreditCardInfo);
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