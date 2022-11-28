package com.example.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

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

public class ClientSearch extends AppCompatActivity {
    private SearchView searchView;
    private ListView listView;
    private List<Meal> offer;
    SimpleAdapter adapter;

    private DatabaseReference offerReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_search);

        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        offer = new LinkedList<>();

        offerReference = FirebaseDatabase.getInstance().getReference("Offer/");

        offerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offer.clear();
                List<Map<String, String>> list = new LinkedList<>();
                for(DataSnapshot child : snapshot.getChildren()){
                    Map<String, String> map = new HashMap<>();
                    Meal meal = child.getValue(Meal.class);
                    meal.setMealID(child.getKey());
                    offer.add(meal);

                    map.put("mealID", meal.getMealID());
                    map.put("mealName", meal.getMealName());
                    map.put("cookID", meal.getCookID());
                    list.add(map);
                }

                adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.client_search_view,
                        new String[]{"mealID", "mealName", "cookID"},
                        new int[]{R.id.mealid, R.id.name, R.id.id});
                listView.setAdapter(adapter);

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
                        adapter.getFilter().filter(s);
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

            }
        });
    }
}