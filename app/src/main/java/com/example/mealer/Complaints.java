package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.mealer.Accounts.ClientComplaints;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Complaints extends AppCompatActivity {
    // since we need to retrieve the data form the database
    // is important to set up all the reference once again
    private DatabaseReference complaintReference;
    private DatabaseReference cookReference;
    private List<ClientComplaints> complaints;

    private TextView clientName;
    private TextView cookName;
    private TextView complaintMsg;

    private Button dismiss;
    private Button suspendTemp;
    private Button suspendPerm;

    private String complaintID, cookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        complaintReference = FirebaseDatabase.getInstance().getReference("Complaints");
        cookReference = FirebaseDatabase.getInstance().getReference("Cooks");
        complaints = new LinkedList<>();

        clientName = findViewById(R.id.clientID);
        cookName = findViewById(R.id.cookID);
        complaintMsg = findViewById(R.id.complaintDescription);

        dismiss = findViewById(R.id.dismiss);
        suspendTemp = findViewById(R.id.suspendTemp);
        suspendPerm = findViewById(R.id.suspendPerm);

        // get all the complaints from the listView
        Intent intent = getIntent();
        complaintID = intent.getStringExtra("ID");
        cookID = intent.getStringExtra("cookID");
        String client = intent.getStringExtra("client");
        String cook = intent.getStringExtra("cook");

        // set their IDs
        clientName.setText(client);
        cookName.setText(cook);

        complaintReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaints.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    // get all the Complaints from the database
                    ClientComplaints complaint = child.getValue(ClientComplaints.class);
                    complaint.setComplaintID(child.getKey());
                    complaints.add(complaint);
                    // check if the complaintID passed in equals to
                    // the ID in the database, then set to the textView
                    if(complaint.getComplaintID().equals(complaintID)){
                        complaintMsg.setText(complaint.getComplaint());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // dismiss button logic
        // 1. VIA the complaintReference, get its child, with the complaintID
        // 2. then set the value to null, then this child will be deleted automatically
        //    done by the database


        // suspend button logic
        // 1. instead remove the value, create a new attribute in Cook class
        //    named isActive to determine if this Cook account is active
        // 2. if the admin temporarily suspend the Cook, then the Cook should see a msg
        // saying that their account has been frozen
        // 3. else permanently suspend, then the Cook should see a msg saying that they
        // will no longer able to use this account, please register a new one
        // 4. and the last delete the complaint from the database
        // simply set the Cook's attributes to what it should be

        // then use finish() return to the listView activity
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaintReference.child(complaintID).removeValue();
                finish();
            }
        });

        suspendTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookReference.child(cookID).child("isActive").setValue("Suspend temporarily");
                Intent i = new Intent(getApplicationContext(), SuspendTime.class);
                // crucial step, in order to let the admin suspend a SPECIFIC
                // cook, need to transfer this cookID into the SuspendTime.java file
                i.putExtra("cookId", cookID);
                startActivity(i);
                complaintReference.child(complaintID).removeValue();
                finish();
            }
        });

        suspendPerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookReference.child(cookID).child("isActive").setValue("Suspend permanently");
                complaintReference.child(complaintID).removeValue();
                finish();
            }
        });
    }

}