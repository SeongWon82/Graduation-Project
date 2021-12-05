package com.seongwon.publictransportapp;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter2 extends FragmentStateAdapter {

    private String routeId,routeNum;
    private Context context;


    public FragmentAdapter2(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String routeId, String routeNum, Context context) {
        super(fragmentManager, lifecycle);
        this.routeId = routeId;
        this.routeNum = routeNum;
        this.context = context;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new BusStationFragment(context,routeId,routeNum);
            case 1:
                return new BusMapFragment();
        }
        return new BusStationFragment(context,routeId,routeNum);
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}
