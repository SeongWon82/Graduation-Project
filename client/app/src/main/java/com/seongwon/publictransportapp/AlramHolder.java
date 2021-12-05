package com.seongwon.publictransportapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class AlramHolder extends RecyclerView.ViewHolder{

    TextView tv_flag,tv_locationNo1,tv_predictTime1,tv_remainSeatCnt1,
            tv_locationNo2,tv_predictTime2,tv_remainSeatCnt2;

    TextView tv_topLeft,tv_topCenter,tv_topRight,tv_bottomLeft,tv_bottomCenter,tv_bottomRight;

    public AlramHolder(@NonNull  View view)
    {
        super(view);

        tv_flag = (TextView) view.findViewById(R.id.tv_flag);

        tv_topLeft = (TextView) view.findViewById(R.id.tv_topLeft);
        tv_topCenter = (TextView) view.findViewById(R.id.tv_topCenter);
        tv_topRight = (TextView) view.findViewById(R.id.tv_topRight);

        tv_bottomLeft = (TextView) view.findViewById(R.id.tv_bottomLeft);
        tv_bottomCenter = (TextView) view.findViewById(R.id.tv_bottomCenter);
        tv_bottomRight = (TextView) view.findViewById(R.id.tv_bottomRight);

        tv_locationNo1 = (TextView) view.findViewById(R.id.tv_locationNo1);
        tv_predictTime1 = (TextView) view.findViewById(R.id.tv_predictTime1);
        tv_remainSeatCnt1 = (TextView) view.findViewById(R.id.tv_remainSeatCnt1);

        tv_locationNo2 = (TextView) view.findViewById(R.id.tv_locationNo2);
        tv_predictTime2 = (TextView) view.findViewById(R.id.tv_predictTime2);
        tv_remainSeatCnt2 = (TextView) view.findViewById(R.id.tv_remainSeatCnt2);
    }
}
