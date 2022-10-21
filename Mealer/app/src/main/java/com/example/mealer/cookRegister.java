package com.example.mealer;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.mealer.databinding.ActivityCookRegisterBinding;

public class cookRegister extends AppCompatActivity {
    private DatabaseReference accountsReference;
    private EditText editFirstName1, editLastName1, editEmailAddress1,
            editAccountPass1, editAddress1, editTextTextUserDescription;
    private String firstname, lastname, emailAddress, password, address, userDescription ;

    private boolean ifInputsAreValid() {
        if(TextUtils.isEmpty(editFirstName1.getText().toString()) || TextUtils.isEmpty(editLastName1.getText().toString())
        || TextUtils.isEmpty(editEmailAddress1.getText().toString()) || TextUtils.isEmpty(editAccountPass1.getText().toString())
        || TextUtils.isEmpty(editAddress1.getText().toString()) || TextUtils.isEmpty(editTextTextUserDescription.getText().toString())){
            prompt("Please fill out all the information").show();
            return false;
        }
        return true;
    }

    private Toast prompt(String text){
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        return toast;
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_register);

        editFirstName1 =  findViewById(R.id.editFirstName1);
        editLastName1 =  findViewById(R.id.editLastName1);
        editEmailAddress1 = findViewById(R.id.editEmailAddress1);
        editAccountPass1 = findViewById(R.id.editAccountPass1);
        editAddress1 = findViewById(R.id.editAddress1);
        editTextTextUserDescription = findViewById(R.id.editTextTextUserDescription);

        accountsReference = FirebaseDatabase.getInstance().getReference("accounts");


        // firstname
        editFirstName1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                firstname = editable.toString();
            }
        });

        // lastname
        editLastName1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                lastname = editable.toString();
            }
        });

        // emailAddress
        editEmailAddress1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                emailAddress = editable.toString();
            }
        });

        // password
        editAccountPass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = editable.toString();
            }
        });

        // address
        editAddress1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                address = editable.toString();
            }
        });

        // user description
        editTextTextUserDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userDescription = editable.toString();
            }
        });


        Button btn =findViewById(R.id.RegisterCook);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ifInputsAreValid()) {


                    Intent intent = new Intent(getApplicationContext(), WelcomMenu.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                }
            }
        });
    }
}
