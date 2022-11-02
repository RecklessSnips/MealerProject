package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.mealer.Accounts.Account;
import com.example.mealer.Accounts.Client;
import com.example.mealer.Accounts.ClientComplaints;
import com.example.mealer.Accounts.Cook;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AdminManagement extends AppCompatActivity {
    // get all reference from the database
    private DatabaseReference clientReference;
    private DatabaseReference cookReference;
    private DatabaseReference complaintReference;

    private ListView listView;

    // to store all the accounts and IDs in a list
    // for later data retrieving
    private List<String> clientIDs;
    private List<String> cookIDs;
    private List<ClientComplaints> complaints;
    private List<Client> clientAccounts;
    private List<Cook> cookAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_management);

        listView = findViewById(R.id.listView);

        // get there paths / snapshots
        clientReference = FirebaseDatabase.getInstance().getReference("Clients");
        cookReference = FirebaseDatabase.getInstance().getReference("Cooks");
        complaintReference = FirebaseDatabase.getInstance().getReference("Complaints");

        // initializing IDs and accounts
        clientIDs = new LinkedList<>();
        cookIDs = new LinkedList<>();
        complaints = new LinkedList<>();
        clientAccounts = new LinkedList<>();
        cookAccounts = new LinkedList<>();

        // use addValueEventListener to listen for
        // value in a specific path changes
        clientReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // need to clear since the database will keep the previous stored data
                clientAccounts.clear();
                clientIDs.clear();
                // get each children in that path
                for(DataSnapshot child : snapshot.getChildren()){
                    // encapsulate the account into children
                    Account client =  child.getValue(Client.class);
                    // set their ID to show on the listView later
                    client.setId(child.getKey());

                    // add objects into the clintAccounts and IDs list for
                    // later data retrieving
                    clientAccounts.add((Client) client);
                    // later data retrieving
                    clientIDs.add(client.getId());
                }
                // test, to generate Complaints
//                generateRandomComplaints(10);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cookAccounts.clear();
                cookIDs.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    Account cook =  child.getValue(Cook.class);
                    cook.setId(child.getKey());

                    cookAccounts.add((Cook) cook);
                    cookIDs.add(cook.getId());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        complaintReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaints.clear();
                // in order to put the correct data
                // onto the listView, need to use <Key, Value> pair
                // which is map hashMap C
                // and store C into the each listView content
                List<Map<String, String>> c = new LinkedList<>();
                for(DataSnapshot child : snapshot.getChildren()){
                    // encapsulation into Class
                    ClientComplaints complaint = child.getValue(ClientComplaints.class);
                    complaint.setComplaintID(child.getKey());
                    complaints.add(complaint);

                    // assign each value in the map
                    Map<String, String> hashMap = new HashMap<>();
                    // in order to dismiss, suspend a cook, we need to
                    // get the specific complaint ID and the cook ID
                    // associated with it, then store it in the complaints listView
                    // make the visibility to gone
                    hashMap.put("id", complaint.getComplaintID());
                    hashMap.put("cookID", complaint.getCookID());
                    // functionality prepared for actual client complaints

//                    for(Client client : clientAccounts){
//                        if(client.getId() == complaint.getClientID()) {
//                            hashMap.put("Client", client.getFirstName());
//                        }
//                    }
//                    for(Cook cook : cookAccounts){
//                        if(cook.getId() == complaint.getClientID()) {
//                            hashMap.put("Cook", cook.getFirstName());
//                        }
//                    }
                    // put corresponding ID to their client and cook
                    hashMap.put("Client", complaint.getClientID());
                    hashMap.put("Cook", complaint.getCookID());
                    // finish the list
                    c.add(hashMap);
                }

                // using simple adapter to customize the content we want to transfer
                // to the listView
                // constructor explanation:
                // ( current activity context, data awaiting to be transfer, String[] of keys
                //                in the map, int[] of the textView to be transferred)
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                        c, R.layout.complaint_list_view, new String[]{"id", "cookID", "Client", "Cook"},
                        new int[] {R.id.idTextView, R.id.cookIdTextView, R.id.clientName, R.id.cookName});
                listView.setAdapter(adapter);

                // enable onClick functionality
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    // constructor explanation:
                    // ( adapterView is the listView which getting passed...,
                    // view is the view which contain each listView's layouts
                    // by finding its ID, assign the values is getting passed ,
                    // int i, long l are the index of the content in the listView)
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // get their IDs in order to transfer to the next activity
                        // through Intent
                        TextView idTextView = view.findViewById(R.id.idTextView);
                        TextView cookId = view.findViewById(R.id.cookIdTextView);
                        TextView clientName = view.findViewById(R.id.clientName);
                        TextView cookName = view.findViewById(R.id.cookName);

                        // the content being transferred is by calling the getText()
                        Intent intent = new Intent(getApplicationContext(), Complaints.class);
                        intent.putExtra("ID", idTextView.getText());
                        intent.putExtra("cookID", cookId.getText());
                        intent.putExtra("client", clientName.getText());
                        intent.putExtra("cook", cookName.getText());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // generating random complaints, just for Deliverable 2 tests.
    private void generateRandomComplaints(int number) {
        Random random = new Random();
        for(int i = 0; i < number; i++){
            String clientID = clientIDs.get(random.nextInt(clientIDs.size()));
            String cookID = cookIDs.get(random.nextInt(cookIDs.size()));
            // create complaints objects and display
            // client and cook IDs
            ClientComplaints complaints = new ClientComplaints();
            complaints.setComplaint("I think the foods sucks ");
            complaints.setClientID((clientID));
            complaints.setCookID((cookID));
            // push to the database through complaintReference path
            // and set each value to each complaint
            complaintReference.push().setValue(complaints);
        }
    }
}