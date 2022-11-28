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

// this menu will show the list of meal that CURRENT cook can do
// so we need to retrieve the cook id from the database
// in order to get the correct menu list corresponding to the logged in cook
public class Menu extends AppCompatActivity {
    private ListView menu;

    private String cookID;
    // Store the meal in a list for later use
    // for example to check redundancy and put into the listView
    private List<Meal> mealList;

    private DatabaseReference menuReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        cookID = intent.getStringExtra("CookID");

        menu = findViewById(R.id.offer_menu);
        mealList = new ArrayList<>();

        menuReference = FirebaseDatabase.getInstance().getReference("Menu/" + cookID);

        menuReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mealList.clear();
                List<Map<String, String>> list = new LinkedList<>();

                for(DataSnapshot child : snapshot.getChildren()){
                    Meal m = child.getValue(Meal.class);
                    // set a random meal ID
                    m.setMealID(child.getKey());
                    mealList.add(m);

                    Map<String, String> map = new HashMap<>();
                    map.put("meal_id", m.getMealID());
                    map.put("meal_name", m.getMealName());
                    map.put("meal_type", m.getMealType());
                    map.put("cuisine_type", m.getCuisineType());
                    list.add(map);
                }
                // test
//                System.out.println(mealList);
                // send to the listView
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list,
                        R.layout.meal_list_view, new String[]{"meal_id", "meal_name", "meal_type", "cuisine_type"},
                        new int[]{R.id.m__id, R.id.m__name, R.id.m__type, R.id.cuisine__type});
                menu.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // transfer current meal's name and id to the next page
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView mealID = view.findViewById(R.id.m__id);
                TextView meal = view.findViewById(R.id.m__name);
                TextView mealType = view.findViewById(R.id.m__type);
                TextView cuisineType = view.findViewById(R.id.cuisine__type);

                Intent intent1 = new Intent(getApplicationContext(), MenuItem.class);
                intent1.putExtra("meal-ID", mealID.getText().toString());
                intent1.putExtra("meal-Name", meal.getText().toString());
                intent1.putExtra("meal-Type", mealType.getText().toString());
                intent1.putExtra("cuisine-Type", cuisineType.getText().toString());
                intent1.putExtra("cook-ID", cookID);
                startActivity(intent1);
            }
        });
    }
}