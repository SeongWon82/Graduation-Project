package com.seongwon.publictransportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.StringTokenizer;

public class BusItemAcitivity extends AppCompatActivity {

    private ImageButton ib_back;
    private Button btn_alram;
    private TextView tv_info_number,tv_startStation,tv_endStation,tv_info_region,tv_info_type;
    private TextView tv_info_first,tv_info_last,tv_info_company;
    private ImageView iv_busType;
    private String routeId;
    private int position;
    private ViewPager2 vp_info_viewpager;
    private TabLayout tl_info_tab;
    private StringTokenizer stringTokenizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        routeId = this.getIntent().getStringExtra("bus_routeId");
        position = this.getIntent().getIntExtra("bus_itemIndex",0);
        try {
            // [0] : tag, [1] : id, [2] : index
            new Task("busRouteInfoItem",routeId,position).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
            new Task("busRouteStationList",routeId,position).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (Exception e) {
            Log.d("Exception",e.getMessage());
        }

        vp_info_viewpager =  (ViewPager2) findViewById(R.id.vp_info_viewpager);
        FragmentManager fm = getSupportFragmentManager();
        FragmentAdapter2 adapter2 = new FragmentAdapter2(fm,getLifecycle(),routeId,this.getIntent().getStringExtra("bus_number"),getApplicationContext());
        vp_info_viewpager.setUserInputEnabled(false);
        vp_info_viewpager.setAdapter(adapter2);

        tl_info_tab = (TabLayout) findViewById(R.id.tl_info_tab);
        tl_info_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp_info_viewpager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        ib_back = (ImageButton) findViewById(R.id.ib_back);
        tv_info_number = (TextView) findViewById(R.id.tv_info_number);
        tv_info_number.setText(this.getIntent().getStringExtra("bus_number"));


        tv_startStation = (TextView) findViewById(R.id.tv_startStation);
        stringTokenizer = new StringTokenizer(Static.getBusItem(position).getStartStationName(),"(.*[,");
        tv_startStation.setText(stringTokenizer.nextToken());

        tv_endStation = (TextView) findViewById(R.id.tv_endStation);
        stringTokenizer = new StringTokenizer(Static.getBusItem(position).getEndStationName(),"(.*[,");
        tv_endStation.setText(stringTokenizer.nextToken());

        tv_info_region = (TextView) findViewById(R.id.tv_info_region);
        tv_info_region.setText(this.getIntent().getStringExtra("bus_region"));

        tv_info_type = (TextView) findViewById(R.id.tv_info_type);
        tv_info_type.setText(this.getIntent().getStringExtra("bus_typeName"));

        tv_info_first = (TextView) findViewById(R.id.tv_info_first);
        tv_info_first.setText(String.format("%s-%s",
                Static.getBusItem(position).getUpFirstTime(),
                Static.getBusItem(position).getDownFirstTime()));

        tv_info_last = (TextView) findViewById(R.id.tv_info_last);
        tv_info_last.setText(String.format("%s-%s",
                Static.getBusItem(position).getUpLastTime(),
                Static.getBusItem(position).getDownLastTime()));

        tv_info_company = (TextView) findViewById(R.id.tv_info_company);
        tv_info_company.setText(Static.getBusItem(position).getCompanyName());

        iv_busType = (ImageView) findViewById(R.id.iv_busType);
        switch (getIntent().getIntExtra("bus_type",1))
        {
            case 2:
                iv_busType.setImageResource(R.drawable.bus_yellow);
                break;
            case 3:
                iv_busType.setImageResource(R.drawable.bus_red);
                break;
            case 4:
                iv_busType.setImageResource(R.drawable.bus_blue);
                break;
            default:
                iv_busType.setImageResource(R.drawable.bus_green);
                break;
        }
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(intent);
            }
        });
    }
}