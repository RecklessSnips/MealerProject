package com.example.mealer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.mealer.datas.Account;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mealer.databinding.ActivityLogInBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class LogIn extends AppCompatActivity {
    private DatabaseReference accountsReference;
    private LinkedList<Account> accounts = new LinkedList<>();
    private String role;
    // attributes for Cook
    private EditText UserEmail, pwdEditText;
    private String firstname,emailAddress, password;
    //****************************************************************************************************************
    // attributes for Client
    private String clientFirstname, clientEmailAddress, clientPassword;

    //****************************************************************************************************************
    private boolean ifClientInputsAreValid() {
        if(TextUtils.isEmpty(UserEmail.getText().toString())){
            prompt("Please type your first name or email address").show();
            return false;
        }
        if(TextUtils.isEmpty(pwdEditText.getText().toString())){
            prompt("Please type your password").show();
            return false;
        }
        // check redundant users
        boolean whetherExist = false;
        boolean correctPassword = false;
        Account client = null;
        for(Account a: accounts){
            if(a.getEmailAddress().equals(clientEmailAddress)){
                whetherExist = true;
                client = a;
            }
        }
        if(whetherExist == false){
            prompt("User not found, please register first").show();
        }
        // check if password is correct
        if(client.getPwd().equals(clientPassword)){
            correctPassword = true;
        }else{
            prompt("Password incorrect, please try again").show();
            correctPassword = false;
        }
        return whetherExist && correctPassword;

    }

    private Toast prompt(String text){
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        return toast;
    }
    //****************************************************************************************************************
    private boolean ifCookInputsAreValid() {
        if(TextUtils.isEmpty(UserEmail.getText().toString())){
            prompt("Please type your first name or email address").show();
            return false;
        }
        if(TextUtils.isEmpty(pwdEditText.getText().toString())){
            prompt("Please type your password").show();
            return false;
        }
        // check redundant users
        boolean whetherExist_1 = false;
        boolean correctPassword_1 = false;
        Account client = null;
        for(Account a: accounts){
            if(a.getEmailAddress().equals(emailAddress)){
                whetherExist_1 = true;
                client = a;
            }
        }
        if(whetherExist_1 == false){
            prompt("User not found, please register first").show();
        }
        // check if password is correct
        if(client.getPwd().equals(password)){
            correctPassword_1 = true;
        }else{
            prompt("Password incorrect, please try again").show();
            correctPassword_1 = false;
        }
        return whetherExist_1 && correctPassword_1;

    }

    //****************************************************************************************************************
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //****************************************************************************************************************
        // Initiation for Client
        UserEmail = findViewById(R.id.UserEmail);
        pwdEditText = findViewById(R.id.pwdEditText);

        accountsReference = FirebaseDatabase.getInstance().getReference("Clients");

        // emailAddress
        UserEmail.addTextChangedListener(new TextWatcher() {
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
        pwdEditText.addTextChangedListener(new TextWatcher() {
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
        //****************************************************************************************************************
        // Initiation for Cook
        UserEmail = findViewById(R.id.UserEmail);
        pwdEditText = findViewById(R.id.pwdEditText);

        accountsReference = FirebaseDatabase.getInstance().getReference("Cooks");

        // emailAddress
        UserEmail.addTextChangedListener(new TextWatcher() {
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
        pwdEditText.addTextChangedListener(new TextWatcher() {
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

        accountsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // clear the database otherwise it will print all the accounts in history
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

        //****************************************************************************************************************
        Button logIn = findViewById(R.id.LogIN);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ifClientInputsAreValid()) {
                    role = "Client";
                    Intent i = new Intent(getApplicationContext(), WelcomMenu.class);
                    i.putExtra("firstName", clientFirstname);
                    i.putExtra("role", role);
                    startActivity(i);
                }
                if(ifCookInputsAreValid()){
                    role = "Cook";
                    Intent i = new Intent(getApplicationContext(), WelcomMenu.class);
                    i.putExtra("firstName", firstname);
                    i.putExtra("role", role);
                    startActivity(i);
                }
            }
        });

        //****************************************************************************************************************
        Button register = findViewById(R.id.reg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}