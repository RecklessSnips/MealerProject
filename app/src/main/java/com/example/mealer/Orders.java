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

public class Orders extends AppCompatActivity {
    private ListView orderList;
    private TextView client, meal, requestID;
    private DatabaseReference requestReference;
    private List<Request> requests = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        orderList = findViewById(R.id.orderList);
        client = findViewById(R.id.client);
        meal = findViewById(R.id.meal);
        requestID = findViewById(R.id.requestID);

        requestReference = FirebaseDatabase.getInstance().getReference("Request");

        requestReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requests.clear();
                List<Map<String, String>> list = new LinkedList<>();
                for (DataSnapshot child : snapshot.getChildren()){
                    Request request = child.getValue(Request.class);
                    request.setId(child.getKey());

                    Map<String, String> map = new HashMap<>();
                    map.put("clientID", request.getClientId());
                    map.put("mealName", request.getMealName());
                    map.put("requestID", request.getId());

                    list.add(map);
                }

                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.order_list_view,
                        new String[] {"clientID", "mealName", "requestID"},
                        new int[] {R.id.client, R.id.meal, R.id.requestID});
                orderList.setAdapter(adapter);

                orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView food, cli, reID;
                        String foodName, clientId, requestID;

                        food = view.findViewById(R.id.meal);
                        cli = view.findViewById(R.id.client);
                        reID = findViewById(R.id.requestID);

                        foodName = food.getText().toString();
                        clientId = cli.getText().toString();
                        requestID = reID.getText().toString();

                        Intent intent = new Intent(getApplicationContext(), HandleOrder.class);
                        intent.putExtra("foodName", foodName);
                        intent.putExtra("clientId", clientId);
                        intent.putExtra("requestID", requestID);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}