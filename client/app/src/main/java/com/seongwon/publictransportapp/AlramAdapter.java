package com.seongwon.publictransportapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlramAdapter extends RecyclerView.Adapter<AlramHolder> {

    private Context mContext;
    private List<Alram> alramList = new ArrayList<Alram>() ;

    public AlramAdapter(Context mContext)
    {
        this.mContext = mContext;
        for (Map.Entry<Integer, Alram> entrySet : Static.alramMap.entrySet()) {
            if(entrySet.getValue()!=null)
                alramList.add(entrySet.getValue());
        }
    }

    @NonNull
    @Override
    public AlramHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.recyclerview_alram,parent,false);
        return new AlramHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlramHolder holder, int position) {

        for(int i=0;i<alramList.size();i++)
            alramList.set(i,Static.alramMap.get(i+1));

        if(alramList.get(position).isCondition())
        {
            holder.tv_flag.setText("---");
            holder.tv_locationNo1.setText("---");
            holder.tv_locationNo2.setText("---");
            holder.tv_predictTime1.setText("---");
            holder.tv_predictTime2.setText("---");
            holder.tv_remainSeatCnt1.setText("---");
            holder.tv_remainSeatCnt2.setText("---");
        }
        else if(alramList.get(position).getTrainLineNm()!=null)
        {
            holder.tv_flag.setText(alramList.get(position).stationName);

            holder.tv_topLeft.setText("지하철 노선");
            holder.tv_topCenter.setText("남은 정류장");
            holder.tv_topRight.setText("현재 위치");

            holder.tv_bottomLeft.setText("지하철 노선");
            holder.tv_bottomCenter.setText("남은 정류장");
            holder.tv_bottomRight.setText("현재 위치");

            holder.tv_locationNo1.setText(alramList.get(position).getTrainLineNm());
            holder.tv_predictTime1.setText(alramList.get(position).getLocationNo1());
            holder.tv_remainSeatCnt1.setText(alramList.get(position).getPredictTime1());

            if(alramList.get(position).getTrainLineNm2()!=null)
                holder.tv_locationNo2.setText(alramList.get(position).getTrainLineNm2());
            else
                holder.tv_locationNo2.setText("---");

            if(alramList.get(position).getLocationNo2()!=null)
                holder.tv_predictTime2.setText(alramList.get(position).getLocationNo2());
            else
                holder.tv_predictTime2.setText("---");

            if(alramList.get(position).getPredictTime2()!=null)
                holder.tv_remainSeatCnt2.setText(alramList.get(position).getPredictTime2());
            else
                holder.tv_remainSeatCnt2.setText("---");
        }
        else {
            int pLocationNo1 = -1;
            int pLocationNo2 = -1;

            holder.tv_topLeft.setText("남은 정류장 수");
            holder.tv_topCenter.setText("예상시간");
            holder.tv_topRight.setText("남은 좌석");

            holder.tv_bottomLeft.setText("남은 정류장 수");
            holder.tv_bottomCenter.setText("예상시간");
            holder.tv_bottomRight.setText("남은 좌석");

            holder.tv_flag.setText(String.format("%s(%s)", alramList.get(position).number, alramList.get(position).stationName));

            if (alramList.get(position).getLocationNo1() != null)
                pLocationNo1 = Integer.parseInt(alramList.get(position).getLocationNo1());

            if (alramList.get(position).getLocationNo2() != null)
                pLocationNo2 = Integer.parseInt(alramList.get(position).getLocationNo2());

            if (pLocationNo1 == -1)
                holder.tv_locationNo1.setText("---");
            else if (pLocationNo1 > 1)
                holder.tv_locationNo1.setText(alramList.get(position).getLocationNo1());
            else
                holder.tv_locationNo1.setText("곧 도착");

            if (pLocationNo2 == -1)
                holder.tv_locationNo2.setText("---");
            else if (pLocationNo2 > 1)
                holder.tv_locationNo2.setText(alramList.get(position).getLocationNo2());
            else
                holder.tv_locationNo2.setText("곧 도착");

            holder.tv_predictTime1.setText(alramList.get(position).getPredictTime1());

            holder.tv_predictTime2.setText(alramList.get(position).getPredictTime2());

            if (alramList.get(position).getRemainSeatCnt1() == null)
                holder.tv_remainSeatCnt1.setText("---");
            else if (alramList.get(position).getRemainSeatCnt1().equals("-1"))
                holder.tv_remainSeatCnt1.setText("---");
            else
                holder.tv_remainSeatCnt1.setText(alramList.get(position).getRemainSeatCnt1());

            if (alramList.get(position).getRemainSeatCnt2() == null)
                holder.tv_remainSeatCnt2.setText("---");
            else if (alramList.get(position).getRemainSeatCnt2().equals("-1"))
                holder.tv_remainSeatCnt2.setText("---");
            else
                holder.tv_remainSeatCnt2.setText(alramList.get(position).getRemainSeatCnt2());
        }
    }
    @Override
    public int getItemCount() { return alramList.size();}
}
