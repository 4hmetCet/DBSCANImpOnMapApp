package com.ahmetcet.dbscanimplementationapp;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Cluster noise;
    private ArrayList<Cluster> result = new ArrayList<>();
    private ArrayList<Integer> colorList = new ArrayList<Integer>();
    private ArrayList<Point> dataSet;
    private int minPts = 2;
    private double eps = 1.0;
    private final double distToPerEPS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        noise = new Cluster("noise");
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.GRAY);
        colorList.add(Color.GREEN);
        colorList.add(Color.DKGRAY);
        colorList.add(Color.LTGRAY);
        colorList.add(Color.CYAN);
        colorList.add(Color.YELLOW);
        colorList.add(Color.MAGENTA);
        colorList.add(Color.BLACK);

        Bundle b = getIntent().getExtras();

        if(b != null){
            eps = b.getDouble("eps");
            minPts = b.getInt("minPts");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng point = new LatLng(40.8235, 29.3722);
        mMap.addMarker(new MarkerOptions().position(point));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 12));

        dataSet = DataSet.getDataSet();
        SetDataSetPointsToMap();
        ClusterDataSet();

    }

    private void SetDataSetPointsToMap(){
        for (Point point:
                dataSet)
        {
            mMap.addMarker(new MarkerOptions().position(point.getLoc()));
        }
    }

    private void ClusterDataSet(){
        while (IsThereUnvisitedPointInDataSet(dataSet)){
            ArrayList<Point> unvisitedPoints = GetUnvisitedPointsInDataSet(dataSet);
            Point p = unvisitedPoints.get(new Random().nextInt(unvisitedPoints.size()));
            ArrayList<Point> n_pointList = getNeighbourPointsByEps(p);
            if(n_pointList.size()>=minPts){
                Cluster cluster = new Cluster(String.valueOf(p.getLoc().latitude));
                int cluster_color = colorList.get(new Random().nextInt(colorList.size()));
                cluster.AddPointToCluster(p);
                drawCircle(p.getLoc(),eps,cluster_color);
                p.setVisited(true);
                AddNeighboursOfPToCluster(n_pointList,cluster,cluster_color);
                result.add(cluster);
            }else{
                p.setVisited(true);
                addPointAsNoise(p);
            }


        }
    }

    private boolean IsThereUnvisitedPointInDataSet(ArrayList<Point> points){
        boolean result = false;
        for (Point point:
             points) {
            if(!point.isVisited()){
                result = true;
                break;
            }
        }
        return result;

    }

    private ArrayList<Point> GetUnvisitedPointsInDataSet(ArrayList<Point> points){
        ArrayList<Point> result = new ArrayList<>();
        for (Point point:
                points) {
            if(!point.isVisited()){
                result.add(point);
            }
        }
        return result;

    }



    private Circle drawCircle(LatLng latLng, Double eps, int color){
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(distToPerEPS*eps)
                .fillColor(color)
                .strokeColor(color));
        return circle;
    }

    private void addPointAsNoise(Point point){
        noise.AddPointToCluster(point);
    }


    private Double getMarkersDist(Point point1, Point point2 ){
        Location point1Location = new Location("");
        point1Location.setLatitude(point1.getLoc().latitude);
        point1Location.setLongitude(point1.getLoc().longitude);

        Location point2Location = new Location("");
        point2Location.setLatitude(point2.getLoc().latitude);
        point2Location.setLongitude(point2.getLoc().longitude);

        Double dist = (double)point2Location.distanceTo(point1Location);
        return  dist;
    }

    private ArrayList<Point> getNeighbourPointsByEps(Point centerPoint){
        ArrayList<Point> resultList = new ArrayList<>();
        for (Point n_point:
            dataSet) {
            if(getMarkersDist(centerPoint,n_point)<=distToPerEPS*eps){
                resultList.add(n_point);
            }
        }
        return  resultList;
    }


    private void AddNeighboursOfPToCluster(ArrayList<Point> n_pointList,Cluster cluster,int cluster_color){
        for (Point n_p:
                n_pointList) {
            if(!n_p.isVisited()){
                n_p.setVisited(true);
                ArrayList<Point> n_n_pointList = getNeighbourPointsByEps(n_p);
                if(n_n_pointList.size()>=minPts){
                    drawCircle(n_p.getLoc(),eps,cluster_color);
                    AddNeighboursOfPToCluster(n_n_pointList,cluster,cluster_color);
                }

            }
            if(!n_p.isClustered()){
                cluster.AddPointToCluster(n_p);
            }
        }
    }

    public void GoToResults(View view) {
        Gson gson = new Gson();
        Intent intent = new Intent(MapsActivity.this, ResultActivity.class);
        Bundle b = new Bundle();
        b.putString("clusters", gson.toJson(result));
        b.putString("noises", gson.toJson(noise));
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        finish();
    }
}