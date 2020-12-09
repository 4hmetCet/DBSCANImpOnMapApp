package com.ahmetcet.dbscanimplementationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    ArrayList<Cluster> clusterList = new ArrayList<>();
    Cluster noise = new Cluster("noise");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Gson gson = new Gson();
        Bundle b = getIntent().getExtras();
        String resultClusterList = "";
        String noiseCluster = "";
        if(b != null){
            resultClusterList = b.getString("clusters");
            noiseCluster = b.getString("noises");
        }
        noise = gson.fromJson(noiseCluster,Cluster.class);

        Type collectionType = new TypeToken<ArrayList<Cluster>>(){}.getType();
        ArrayList<Cluster> clusterList = gson.fromJson(resultClusterList, collectionType);


        TextView tv_noiseCount = (TextView) findViewById(R.id.textView_noiseCount);
        String noiseCount = String.valueOf(noise.getPointList().size());
        tv_noiseCount.setText(noiseCount);


        ListView listView_clusterList = (ListView) findViewById(R.id.listView_result_clusterList);

// get data from the table by the ListAdapter
        ResultClusterListAdapter resultClusterListAdapter = new ResultClusterListAdapter(this, R.layout.item_result_cluster, clusterList);

        listView_clusterList .setAdapter(resultClusterListAdapter);

    }
}