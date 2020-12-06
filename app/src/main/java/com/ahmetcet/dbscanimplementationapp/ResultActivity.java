package com.ahmetcet.dbscanimplementationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle b = getIntent().getExtras();
        String result = "";
        String noise = "";
        if(b != null){
            result = b.getString("clusters");
            noise = b.getString("noises");
        }

        TextView tv_result = (TextView) findViewById(R.id.textView_result);
        tv_result.setText(result);

    }
}