package com.seongwon.publictransportapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public  class BusHolder extends RecyclerView.ViewHolder
{
    LinearLayout ll_item;
    TextView tv_number,tv_regionName,tv_routeTypeName;
    ImageView iv_type,iv_arrow;
    ImageButton ib_fav;

    public BusHolder(@NonNull View view)
    {
        super(view);
        ll_item = view.findViewById(R.id.ll_item);
        tv_number = view.findViewById(R.id.tv_number);
        tv_regionName = view.findViewById(R.id.tv_regionName);
        tv_routeTypeName = view.findViewById(R.id.tv_routeTypeName);
        iv_type = view.findViewById(R.id.iv_type);
        iv_arrow = view.findViewById(R.id.iv_arrow);
        ib_fav = view.findViewById(R.id.ib_fav);
    }
}