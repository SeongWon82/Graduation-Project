package com.seongwon.publictransportapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BusAdapter extends RecyclerView.Adapter<BusHolder>{

    private Context mContext;
    private ArrayList<Bus> buses= Static.getBuses();

    public BusAdapter(Context mContext) { this.mContext = mContext; }

    // 처음 뷰를 생성
    @NonNull
    @Override
    public BusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.recyclerview_bus,parent,false);
        return new BusHolder(view);
    }

    // 뷰 홀더가 재활용될때 실행되는 메소드 삽입, 삭제등 변경이 일어날때 필요한 코드 작성
    @Override
    public void onBindViewHolder(@NonNull BusHolder busHolder, int position) {

        int code = Integer.parseInt(buses.get(position).getRouteTypeCd());
        int type = API.getTypeCodeNumber(code);
        switch (type)
        {
            case 2:
                busHolder.iv_type.setImageResource(R.drawable.bus_yellow);
                break;
            case 3:
                busHolder.iv_type.setImageResource(R.drawable.bus_red);
                break;
            case 4:
                busHolder.iv_type.setImageResource(R.drawable.bus_blue);
                break;
            default:
                busHolder.iv_type.setImageResource(R.drawable.bus_green);
                break;
        }
        busHolder.tv_number.setText(buses.get(position).getRouteName());
        busHolder.tv_regionName.setText(buses.get(position).getRegionName());
        busHolder.tv_routeTypeName.setText(buses.get(position).getRouteTypeName());
        if(Static.favorites.containsKey(buses.get(position).getRouteId()))
            busHolder.ib_fav.setImageResource(R.drawable.fav_star_on);
        else
            busHolder.ib_fav.setImageResource(R.drawable.fav_star_off);
        busHolder.ib_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Static.favorites.containsKey(buses.get(position).getRouteId()))
                    Static.favorites.remove(buses.get(position).getRouteId());
                else
                    Static.favorites.put(buses.get(position).getRouteId(),buses.get(position));
                notifyItemChanged(position);
            }
        });

        busHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BusItemAcitivity.class);
                intent.putExtra("bus_routeId",buses.get(position).getRouteId());
                intent.putExtra("bus_Index",position);
                intent.putExtra("bus_number", busHolder.tv_number.getText().toString());
                intent.putExtra("bus_type",type);
                intent.putExtra("bus_region", busHolder.tv_regionName.getText().toString());
                intent.putExtra("bus_typeName", busHolder.tv_routeTypeName.getText().toString());

                Static.clearBusStations();
                mContext.startActivity(intent);
            }
        });
    }

    // 리스트 개수
    @Override
    public int getItemCount() {
        return buses.size();
    }
}
