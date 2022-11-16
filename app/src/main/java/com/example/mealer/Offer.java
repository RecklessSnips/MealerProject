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

import com.example.mealer.Accounts.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Offer extends AppCompatActivity {
    private ListView offer_menu;

    private String cookID;
    // Store the meal in a list for later use
    // for example to check redundancy and put into the listView
    private List<Meal> offerList;

    private DatabaseReference offerReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        Intent intent = getIntent();
        cookID = intent.getStringExtra("CookID");

        offer_menu = findViewById(R.id.offer_menu);
        offerList = new ArrayList<>();

        // locate the data base path we want to put the data
        offerReference = FirebaseDatabase.getInstance().getReference("Offer/" + cookID);

        offerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offerList.clear();
                List<Map<String, String>> list = new LinkedList<>();

                for(DataSnapshot child : snapshot.getChildren()){
                    Meal m = child.getValue(Meal.class);
                    // set a random meal ID
                    m.setMealID(child.getKey());
                    offerList.add(m);

                    Map<String, String> map = new HashMap<>();
                    map.put("meal_id", m.getMealID());
                    map.put("meal_name", m.getMealName());
                    list.add(map);
                }
                // test
//                System.out.println(mealList);
                // send to the listView
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list,
                        R.layout.offer_list_view, new String[]{"meal_id", "meal_name"},
                        new int[]{R.id.m__id, R.id.m__name});
                offer_menu.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        offer_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // we need to transfer all the contents from the
                // list view to the list item page, RESPECTIVELY!!!
                TextView mealID = view.findViewById(R.id.m__id);
                TextView meal = view.findViewById(R.id.m__name);

                Intent intent1 = new Intent(getApplicationContext(), OfferItem.class);
                intent1.putExtra("meal-ID", mealID.getText().toString());
                intent1.putExtra("meal-Name", meal.getText().toString());
//              // and cook id of course, for later deletion and compare
                intent1.putExtra("cook-ID", cookID);
                startActivity(intent1);
            }
        });
    }
}