package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mealer.Accounts.SuspendAccounts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class CookSuspendTempActivity extends AppCompatActivity {
    private TextView suspendMsg_1;
    private String suspendDate;
    private TextView logInCookID;
    private String logInID;
    private static final String HINT_MSG = "Sorry, your account has been suspend until ";
    // get suspended accounts from the database
    private DatabaseReference suspendedAccounts;
    private List<SuspendAccounts> a;


    @SuppressLint({"WrongViewCast", "MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        suspendMsg_1 = (TextView) findViewById(R.id.cookTempSuspendMsg);
        suspendedAccounts = FirebaseDatabase.getInstance().getReference("Suspended Accounts");
        logInCookID = findViewById(R.id.logInCookID);
        a = new LinkedList<>();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_suspend_temp);

        // get cook ID
        Intent i = getIntent();
        logInID = i.getStringExtra("CookID");
        System.out.println(logInID);
//        System.out.println(logInID);

        suspendedAccounts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                    SuspendAccounts susAccounts = child.getValue(SuspendAccounts.class);
                    susAccounts.setSuspendAccountID(child.getKey());
//                    System.out.println(susAccounts);
//                    a.add(susAccounts);
                    if (susAccounts.getCookID().equals(logInID)) {
                        suspendDate = susAccounts.getSuspendDate();
                    }
//                    break;
                }
                updateTextView(HINT_MSG + suspendDate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void updateTextView(String toThis) {
        suspendMsg_1 = (TextView) findViewById(R.id.cookTempSuspendMsg);
        suspendMsg_1.setText(toThis);
    }
}