package com.seongwon.publictransportapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BusStationFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private static BusStationAdapter busStationAdapter=null;

    private String routeId,number;

    public BusStationFragment(Context context, String routeId,String number)
    {
        this.context = context;
        this.routeId = routeId;
        this.number = number;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bus_station,container,false);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        recyclerView = (RecyclerView) this.getView().findViewById(R.id.recycler_bus_station);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        busStationAdapter = new BusStationAdapter(context,routeId,number);
        recyclerView.setAdapter(busStationAdapter);

        if(busStationAdapter != null)
            busStationAdapter.notifyDataSetChanged();
    }
}
