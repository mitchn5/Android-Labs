package com.example.micha.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AndroidLabActivity extends AppCompatActivity {

    SharedPreferences prefs;
    EditText emailEdit;
    Button loginB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab3);

        prefs = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        emailEdit = findViewById(R.id.emailInput);
        String emailS = prefs.getString("ReserveName", "");
        emailEdit.setText(emailS);


        loginB = findViewById(R.id.loginB);
        loginB.setOnClickListener((View v) ->
                {
                    Intent intent = new Intent(this, ProfileActivity.class);
                    intent.putExtra("ReserveName", emailEdit.getText().toString());
                    startActivity(intent);
                }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = prefs.edit();

        //save what was typed under the name "ReserveName"
        String userIn = emailEdit.getText().toString();
        editor.putString("ReserveName", userIn);

        //write it to disk:
        editor.commit();

    }
}
