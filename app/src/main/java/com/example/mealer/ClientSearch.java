package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.mealer.Accounts.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ClientSearch extends AppCompatActivity {
    private DatabaseReference offerReference;
    private SearchView searchView;
    private ListView listView;
    private List<Meal> offer;
    private String id;
//    SimpleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_search);

        Intent intent = getIntent();
        id = intent.getStringExtra("client_id");

        searchView = (SearchView) findViewById(R.id.searchView);
        listView = findViewById(R.id.list);
        offer = new LinkedList<>();

//        offerReference = FirebaseDatabase.getInstance().getReference("Offer/-NGyYwaZr9B9ZxNRIW2e");
        offerReference = FirebaseDatabase.getInstance().getReference("Offer");

        offerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offer.clear();
                List<Map<String, String>> list = new LinkedList<>();
                for(DataSnapshot child : snapshot.getChildren()){
                    for(DataSnapshot c : child.getChildren()) {
                        Map<String, String> map = new HashMap<>();
                        Meal meal = c.getValue(Meal.class);
                        Objects.requireNonNull(meal).setMealID(c.getKey());
                        offer.add(meal);

                        // This allow user to search either in name, cuisine, or type
                        map.put("mealID", meal.getMealID());
                        map.put("mealName", meal.getMealName());
                        map.put("mealType", meal.getMealType());
                        map.put("cuisineType", meal.getCuisineType());
                        map.put("cookID", meal.getCookID());
                        list.add(map);
                    }
                }
//                System.out.println(list);

                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.client_search_view,
                        new String[]{"mealName", "cookID", "mealID", "mealType", "cuisineType"},
                        new int[]{R.id.name, R.id.id, R.id.mealid, R.id.mealtype, R.id.cuisinetype});

//                SimpleAdapter adapter1 = new SimpleAdapter(getApplicationContext(), list, R.layout.client_search_view,
//                        new String[]{"mealName", "cookID"},
//                        new int[]{R.id.name, R.id.id});
                listView.setAdapter(adapter);
//                listView.setAdapter(adapter1);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        // Just for test
//                System.out.println(s);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        // Just for test
//                System.out.println(s);
//                        (ClientSearch.this).
                        adapter.getFilter().filter(s);
//                        System.out.println(adapter);
                        return false;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView meal = view.findViewById(R.id.name);
                TextView cook = view.findViewById(R.id.id);
                TextView mealType = view.findViewById(R.id.mealtype);
                TextView cousineType = view.findViewById(R.id.cuisinetype);
                TextView iD = view.findViewById(R.id.mealid);
//                System.out.println(meal.getText());
//                System.out.println(cook.getText());
//                System.out.println(iD.getText());
//                System.out.println(id);
                Intent intent = new Intent(getApplicationContext(), Purchase.class);
                intent.putExtra("mealName", meal.getText().toString());
                intent.putExtra("mealType", mealType.getText().toString());
                intent.putExtra("cousineType", cousineType.getText().toString());
                intent.putExtra("cookID", cook.getText().toString());
                intent.putExtra("mealID", iD.getText().toString());
                // pass client id
                intent.putExtra("client_ID", id);
                startActivity(intent);
            }
        });
    }
}