package com.example.otagchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
     RelativeLayout rl_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        rl_logout = findViewById(R.id.rl_logout);

        rl_logout.setOnClickListener(this);

        //getting the toolbar
/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setting the title
        toolbar.setTitle("Setting");
        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_logout:
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                break;
        }
    }
}
