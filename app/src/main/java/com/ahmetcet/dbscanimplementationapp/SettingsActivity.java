package com.ahmetcet.dbscanimplementationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void GoToResultOnMap(View view) {
        EditText editText_eps = (EditText) findViewById(R.id.editTextEps);
        Double eps = Double.parseDouble(editText_eps.getText().toString());
        EditText editText_mpts = (EditText) findViewById(R.id.editMinPts);
        int mpts = Integer.parseInt(editText_mpts.getText().toString());
        Intent intent = new Intent(SettingsActivity.this,MapsActivity.class);
        Bundle b = new Bundle();
        b.putDouble("eps", eps);
        b.putInt("minPts", mpts);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
}