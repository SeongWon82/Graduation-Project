package com.seongwon.publictransportapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrainHolder extends RecyclerView.ViewHolder{

    LinearLayout ll_train_item;
    ImageView iv_trainIcon;
    TextView tv_stationName,tv_lineNum;

    public TrainHolder(@NonNull View view) {
        super(view);

        ll_train_item = (LinearLayout) view.findViewById(R.id.ll_train_item);
        iv_trainIcon = (ImageView) view.findViewById(R.id.iv_trainIcon);
        tv_stationName = (TextView) view.findViewById(R.id.tv_stationName);
        tv_lineNum = (TextView) view.findViewById(R.id.tv_lineNum);
    }
}
