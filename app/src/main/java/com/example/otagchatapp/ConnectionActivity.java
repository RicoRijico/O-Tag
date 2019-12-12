package com.example.otagchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ConnectionActivity extends AppCompatActivity {
    SwitchCompat SwitchCompat;
    SharedPreferences.Editor prefEditor;
    SharedPreferences prefs;
    TextView text_service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        SwitchCompat = findViewById(R.id.switchButton);
        text_service = findViewById(R.id.text_service);
        prefEditor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        SwitchCompat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (SwitchCompat.isChecked()) {
                    // DO what ever you want here when button is enabled
                    Toast.makeText(ConnectionActivity.this, "Enabled", Toast.LENGTH_SHORT).show();
                    text_service.setTextColor(Color.BLUE);
                    prefEditor.putString("checked","yes");
                    prefEditor.apply();
                } else {
                    // DO what ever you want here when button is disabled
                Toast.makeText(ConnectionActivity.this, "Disabled", Toast.LENGTH_SHORT).show();
                    text_service.setTextColor(Color.RED);
                    prefEditor.putString("checked","false");
                    prefEditor.apply();
                }
            }
        });

        if (prefs.getString("checked","no").equals("yes")){
            SwitchCompat.setChecked(true);
            text_service.setTextColor(Color.BLUE);


        }else {
            SwitchCompat.setChecked(false);
            text_service.setTextColor(Color.RED);
        }
    }
}
