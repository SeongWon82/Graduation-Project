package com.seongwon.publictransportapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class BusStationAdapter extends RecyclerView.Adapter<BusStationHolder>{

    private Context context;
    private ArrayList<BusStation> busStations = Static.getBusStations();
    private String routeId,result,number;

    public BusStationAdapter(Context context, String routeId,String number)
    {
        this.context = context;
        this.routeId=routeId;
        this.number = number;
    }

    @NonNull
    @Override
    public BusStationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.recyclerview_bus_station,parent,false);
        return new BusStationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusStationHolder holder, int position) {
        if( position == 0 )
            holder.iv_routeImg.setImageResource(R.drawable.station_start_line);
        else if( position == (busStations.size() - 1) )
            holder.iv_routeImg.setImageResource(R.drawable.station_end_line);
        else
            holder.iv_routeImg.setImageResource(R.drawable.station_line);
        holder.tv_stationName.setText(busStations.get(position).getStationName());
        holder.tv_mobileNo.setText(busStations.get(position).getMobileNo());

        holder.ll_station_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (Static.getStatus())
                {
                    case "native":
                    case "kakao":
                        try
                        {
                            boolean isDelete = deleteAlram(position);
                            if(isDelete==false)
                            {
                                int rest = Static.getRestIndex();

                                if(rest == -1)
                                    Toast.makeText(context, "목록이 가득 찼습니다.", Toast.LENGTH_LONG).show();
                                else {
                                    Static.alramMap.get(rest).number = number;
                                    Static.alramMap.get(rest).stationName = busStations.get(position).getStationName();
                                    result = (String) new Task("alram", rest,
                                            busStations.get(position).getStationId(),
                                            routeId, String.valueOf(busStations.get(position).getStationSeq()))
                                            .execute()
                                            .get();
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        catch (Exception e) {
                            Log.d("Exception",e.getMessage());
                        }
                        break;
                    case "non_member":
                        Toast.makeText(context, "비회원 이용불가.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private boolean deleteAlram(int position) throws Exception {

        String mStationId,mRouteId;

        for(int i=1;i<=3;i++)
        {
            mStationId = Static.alramMap.get(i).getStationId();
            mRouteId = Static.alramMap.get(i).getRouteId();
            if(!Static.alramMap.get(i).isCondition())
            {
                if( (busStations.get(position).getStationId().equals(mStationId)) && (mRouteId.equals(routeId)) )
                {
                    result = (String) new Task("close", i,
                            busStations.get(position).getStationId(),
                            routeId,String.valueOf(busStations.get(position).getStationSeq()))
                            .execute()
                            .get();
                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() { return busStations.size();}
}
