package com.seongwon.publictransportapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrainAdapter extends RecyclerView.Adapter<TrainHolder> {

    private Context mContext = null;
    private ArrayList<Train> trains =  Static.getTrains();

    public TrainAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public TrainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_train,parent,false);
        return new TrainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainHolder holder, int position) {

        switch (trains.get(position).getLine_num())
        {
            case "01호선":
                holder.iv_trainIcon.setImageResource(R.drawable.ic_one);
                break;
            case "02호선":
                holder.iv_trainIcon.setImageResource(R.drawable.ic_two);
                break;
            case "03호선":
                holder.iv_trainIcon.setImageResource(R.drawable.ic_three);
                break;
            case "04호선":
                holder.iv_trainIcon.setImageResource(R.drawable.ic_four);
                break;
            case "05호선":
                holder.iv_trainIcon.setImageResource(R.drawable.ic_five);
                break;
            case "06호선":
                holder.iv_trainIcon.setImageResource(R.drawable.ic_six);
                break;
            case "07호선":
                holder.iv_trainIcon.setImageResource(R.drawable.ic_seven);
                break;
            case "08호선":
                holder.iv_trainIcon.setImageResource(R.drawable.ic_eight);
                break;
            default:
                holder.iv_trainIcon.setImageResource(R.drawable.ic_train);
                break;
        }

        holder.tv_stationName.setText(trains.get(position).getStation_nm());
        holder.tv_lineNum.setText(trains.get(position).getLine_num());

        holder.ll_train_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TrainActivity.class);
                intent.putExtra("route",trains.get(position).getLine_num());
                intent.putExtra("station",trains.get(position).getStation_nm());
                mContext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return trains.size();
    }
}
