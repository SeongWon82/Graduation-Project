package com.seongwon.publictransportapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BusStationHolder extends RecyclerView.ViewHolder{

    LinearLayout ll_station_item;
    ImageView iv_routeImg;
    TextView tv_stationName,tv_mobileNo;

    public BusStationHolder(@NonNull View view) {
        super(view);
        ll_station_item = view.findViewById(R.id.ll_station_item);
        iv_routeImg = view.findViewById(R.id.iv_routeImg);
        tv_stationName = view.findViewById(R.id.tv_stationName);
        tv_mobileNo = view.findViewById(R.id.tv_mobileNo);
    }
}
