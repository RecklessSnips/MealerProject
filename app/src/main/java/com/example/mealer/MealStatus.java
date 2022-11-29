package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealer.Accounts.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MealStatus extends AppCompatActivity {
    private String clientID, currentStatus;
    private ListView status_view;
//    private List<Request> requestList;
//    private List<String> statusList;

    private DatabaseReference requestReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_status);

        Intent intent = getIntent();
        clientID = intent.getStringExtra("client_id");


        status_view = findViewById(R.id.status_view);

//        requestList  = new LinkedList<>();
//        statusList  = new LinkedList<>();

        requestReference = FirebaseDatabase.getInstance().getReference("Request");

        requestReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                requestList.clear();
//                statusList.clear();
                List<Map<String, String>> list = new LinkedList<>();
                for(DataSnapshot child : snapshot.getChildren()){
//                    System.out.println(child);
                    Request request = child.getValue(Request.class);
                    request.setStatus((String) child.child("status").getValue());
                    request.setId(child.getKey());
//                    requestList.add(request);


                    Map<String, String> map = new HashMap<>();
//                    System.out.println(request.getMealName());
//                    System.out.println(request.getStatus());
                    map.put("mealName", request.getMealName());
                    map.put("status", request.getStatus());
                    map.put("requestID", request.getId());
//                    currentStatus = request.getStatus();
//                    statusList.add(currentStatus);
//                    status.setText(currentStatus);
                    list.add(map);
                }

                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.status_list_view,
                        new String[] {"mealName", "status", "requestID"},
                        new int[] {R.id.m_name, R.id.status, R.id.request_ID});
                status_view.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        status_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView status = view.findViewById(R.id.status);
                TextView request_ID = view.findViewById(R.id.request_ID);

                if(status.getText().toString().equals("pending")){
                    Toast.makeText(getApplicationContext(), "Sorry, your order is awaiting for processing",
                            Toast.LENGTH_SHORT).show();
                } else if (status.getText().toString().equals("rejected")){
                    Toast.makeText(getApplicationContext(), "Sorry, your order has been rejected",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(getApplicationContext(), Evaluation.class);
                    intent1.putExtra("requestID", request_ID.getText().toString());
                    startActivity(intent1);
                }
            }
        });

    }
}