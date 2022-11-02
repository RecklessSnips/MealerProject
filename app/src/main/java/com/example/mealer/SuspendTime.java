package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealer.Accounts.Account;
import com.example.mealer.Accounts.Cook;
import com.example.mealer.Accounts.SuspendAccounts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SuspendTime extends AppCompatActivity {
    private EditText suspendTimeTextView;
    private Button suspendTimeButton;
    private String suspendDate, cookID, dateID;
    private TextView suspendCookID;

    // gte suspendAccounts reference
    private DatabaseReference suspendAccountsReference;
    private DatabaseReference cookReference;

    // store the suspend accounts
    private List<String> cookIDs;
    private List<Cook> cookAccounts;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspend_time);

        // get cookId
        Intent intent = getIntent();
        cookID = intent.getStringExtra("cookId");

        suspendTimeTextView = findViewById(R.id.suspendTimeTextView);
        suspendTimeButton = findViewById(R.id.suspendTimeButton);

        // get database reference
        suspendAccountsReference = FirebaseDatabase.getInstance().getReference("Suspended Accounts");
        cookReference = FirebaseDatabase.getInstance().getReference("Cooks");

        // initializing suspendAccounts
        cookIDs = new LinkedList<>();
        cookAccounts = new LinkedList<>();


        cookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cookAccounts.clear();
                cookIDs.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    Account cook =  child.getValue(Cook.class);
                    cook.setId(child.getKey());

                    cookAccounts.add( (Cook) cook);
                    cookIDs.add(cook.getId());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        suspendTimeTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                suspendDate = editable.toString();
            }
        });

        suspendTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(suspendDate == null){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please specify a suspend date", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    generateSuspendAccount(suspendDate);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Suspend successfully", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }
        });
    }

    public void generateSuspendAccount(String date){
        SuspendAccounts suspendAccount = new SuspendAccounts();
        suspendAccount.setCookID(cookID);
        suspendAccount.setSuspendDate(date);

        suspendAccountsReference.push().setValue(suspendAccount);
    }
}