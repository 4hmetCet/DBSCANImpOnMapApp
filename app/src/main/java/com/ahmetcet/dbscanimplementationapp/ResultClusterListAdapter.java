package com.ahmetcet.dbscanimplementationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultClusterListAdapter extends ArrayAdapter {

    private int resourceLayout;
    private Context mContext;

    public ResultClusterListAdapter(Context context, int resource, ArrayList<Cluster> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Cluster p = (Cluster) getItem(position);

        if (p != null) {
            TextView tv_clusterCount = (TextView) v.findViewById(R.id.textView_itemClusterCount);
            TextView tv_clusterName = (TextView) v.findViewById(R.id.textView_itemClusterName);
            TextView tv_clusterSize = (TextView) v.findViewById(R.id.textView_itemClusterSize);

            if (tv_clusterCount != null) {
                tv_clusterCount.setText(String.valueOf(position+1));
            }

            if (tv_clusterName != null) {
                tv_clusterName.setText(p.getClusterName());
            }

            if (tv_clusterSize != null) {
                tv_clusterSize.setText(String.valueOf(p.getPointList().size()));
            }
        }

        return v;
    }

}