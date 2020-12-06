package com.ahmetcet.dbscanimplementationapp;

import com.google.android.gms.maps.model.LatLng;

public class Point {

    private LatLng loc;

    private boolean isVisited;

    private boolean isClustered;

    public Point(LatLng Loc){
        this.loc = Loc;
        isVisited = false;
        isClustered = false;
    }

    public LatLng getLoc() {
        return loc;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setLoc(LatLng loc) {
        this.loc = loc;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public boolean isClustered() {
        return isClustered;
    }

    public void setClustered(boolean clustered) {
        isClustered = clustered;
    }
}
