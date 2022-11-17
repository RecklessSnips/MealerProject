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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference clientReference, cookReference;
    private TextView welcome;
    private Button logIn, register;
    private EditText username, password;
    private String name, combination, role;
    private RadioGroup radioGroup;
    private RadioButton clientRadioBtn, adminRadioBtn, cookRadioBtn;
    private Client client = null;
    private Cook cook = null;
    // to transfer this cookID to the complaint page
    private String cookID;
    // to specify the current log in cook
    private Cook loggedInCook;

    private List<Client> clientAccounts ;
    private List<Cook> cookAccounts;

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
                loggedInCook = cook;
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

    // additional method for checking a cook
    // whether he/she is being suspended
    private boolean ifCookActive(){
        boolean active;
//        for(Cook a : cookAccounts){
//            // isActive attribute is a String, simply tells if
//            // is active or not
//            if(a.getIsActive().equals("active")){
//                active = true;
//            }else{
//                active = false;
//            }
//        }
        active = loggedInCook.getIsActive().equals("active");
        return active;
    }
    private boolean ifAdminInputsAreValid(){
        boolean valid = false;
        if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
            prompt("Please fill out all the information").show();
            valid = false;
        }
        if(username.getText().toString().equals("admin@gmail.com")
                && password.getText().toString().equals("iamtheadmin")){
            valid = true;
        }else{
            if (! username.getText().toString().equals("admin@gmail.com")) {
                prompt("Admin doesn't exist!").show();
                valid = false;
            }else if(!password.getText().toString().equals("iamtheadmin")){
                prompt("password is incorrect!").show();
                valid = false;
            }
        }
        return valid;
    }

    private Toast prompt(String text){
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        return toast;
    }

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
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
        // radio group *************************************************************************
        radioGroup = findViewById(R.id.radioGroup);
        clientRadioBtn = findViewById(R.id.clientRadioBtn);
        adminRadioBtn = findViewById(R.id.adminRadioBtn);
        cookRadioBtn = findViewById(R.id.cookRadioBtn);

        // list
        clientAccounts = new LinkedList<>();
        cookAccounts = new ArrayList<>();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.clientRadioBtn){
                    role = "client";
                }else if(i == R.id.cookRadioBtn){
                    role = "cook";
                }else{
                    role = "admin";
                }
            }
        });

        // connect to database **************************************************************
        clientReference = FirebaseDatabase.getInstance().getReference("Clients");
        cookReference = FirebaseDatabase.getInstance().getReference("Cooks");

        clientReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientAccounts.clear();
                for(DataSnapshot child : snapshot.getChildren()){

                    Account client = child.getValue(Client.class);
//                    System.out.println(client);
                    clientAccounts.add((Client) client);
                }
//                System.out.println(clientAccounts.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cookAccounts.clear();
                for(DataSnapshot child : snapshot.getChildren()){

                    Account cook = child.getValue(Cook.class);
//                    System.out.println(cook);
                    cook.setId(child.getKey());
                    cookAccounts.add((Cook) cook);
                }
//                System.out.println(cookAccounts.size());
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
                if(radioGroup.getCheckedRadioButtonId() != -1) {
                    if(cookRadioBtn.isChecked()) {
//                        System.out.println(username);
//                        System.out.println(role);
                        if (ifCookInputsAreValid()) {
//                            System.out.println(cook);
                            // additional check, if the cook account is active
                            if(ifCookActive()) {
                                cookID = loggedInCook.getId();
                                System.out.println(cookID);
                                Intent i = new Intent(getApplicationContext(), CookActivity.class);
                                i.putExtra("name", name);
                                i.putExtra("role", role);
                                i.putExtra("CookID", cookID);
                                startActivity(i);
                            }else{
                                // not using for loop here because we need to
                                // make sure this is the current logged in cook
                                // which won't pass all the cookID to the CookSuspendTempActivity
                                if(loggedInCook.getIsActive().equals("Suspend temporarily")){
                                    cookID = loggedInCook.getId();
//                                        System.out.println(cookID);
                                    Intent i = new Intent(getApplicationContext(), CookSuspendTempActivity.class);
                                    i.putExtra("CookID", cookID);
                                    startActivity(i);
                                }else if(loggedInCook.getIsActive().equals("Suspend permanently")){
                                    Intent i = new Intent(getApplicationContext(), CookSuspendPermActivity.class);
                                    startActivity(i);
                                }
                            }
                        }
                    }
                    if(clientRadioBtn.isChecked()) {
                        if (ifClientInputsAreValid()) {
                            Intent i = new Intent(getApplicationContext(), WelcomeMenu.class);
                            i.putExtra("name", name);
                            i.putExtra("role", role);
                            startActivity(i);
                        }
                    }
                    if(adminRadioBtn.isChecked()) {
                        if (ifAdminInputsAreValid()) {
                            Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                            i.putExtra("name", name);
                            i.putExtra("role", role);
                            startActivity(i);
                        }
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