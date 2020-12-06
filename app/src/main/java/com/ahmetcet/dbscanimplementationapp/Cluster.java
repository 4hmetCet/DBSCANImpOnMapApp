package com.ahmetcet.dbscanimplementationapp;

import java.util.ArrayList;

public class Cluster {
    private String ClusterName;
    private ArrayList<Point> pointList;

    public Cluster(String clusterName){
        pointList = new ArrayList<>();
        this.ClusterName = clusterName;
    }
    public void setPointList(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    public void setClusterName(String clusterName) {
        ClusterName = clusterName;
    }

    public ArrayList<Point> getPointList() {
        return pointList;
    }

    public String getClusterName() {
        return ClusterName;
    }

    public void AddPointToCluster(Point point){
        if(pointList == null)
            pointList = new ArrayList<>();

        pointList.add(point);
        point.setClustered(true);
    }
}
