package com.seongwon.publictransportapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

public class BusMapFragment extends Fragment {

    private View view;
    private double center_X,center_Y;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { super.onCreate(savedInstanceState); }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bus_map,container,false);
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MapView mapView = new MapView(getContext());
        ViewGroup container = (ViewGroup) view.findViewById(R.id.ll_bus_map);

        center_X = ( Static.getSt_x() + Static.getEd_x() ) / 2.0;
        center_Y = ( Static.getSt_y() + Static.getEd_y() ) / 2.0;

        // 중심점 변경 + 줌 레벨 변경
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(center_Y, center_X),5, true);
        // 마커 추가(기점, 종점)
        setMarker(mapView);
        // 정류장 경로 그리기
        drawLineOnMap(mapView);

        container.addView(mapView);
    }
    // 선을 그림
    private void drawLineOnMap(MapView mapView)
    {
        MapPolyline mapPolyline = new MapPolyline(Static.getBusStations().size());
        for(BusStation bs : Static.getBusStations())
            mapPolyline.addPoint(MapPoint.mapPointWithGeoCoord(bs.getY(), bs.getX()));
        mapPolyline.setLineColor(Color.argb(0xFF,0x00,0x80,0xFF));
        mapView.addPolyline(mapPolyline);
    }
    // 종점, 기점 마킹
    private void setMarker(MapView mapView)
    {
        int size = Static.getBusStations().size();

        if(size <= 1) return;
        else if(Static.getSt_y() == -1 || Static.getSt_x() == -1) return;
        else if(Static.getEd_y() == -1 || Static.getEd_x() == -1) return;
        else {
            addMark(mapView, Static.getSt_y(), Static.getSt_x(), "start");
            addMark(mapView, Static.getEd_y(), Static.getEd_x(), "end");
        }
    }

    // 마커 추가 type : 1 기점 2 종점
    private void addMark(MapView mapView,double y,double x,String type)
    {
        // 위치 정보가 없는 경우
        if(y == -1 || x == -1 )
            return;
        else
        {
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("Default Marker");
            marker.setTag(0);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(y, x));
            switch (type)
            {
                case "start":
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    break;
                case "end":
                    marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                    break;
            }
            mapView.addPOIItem(marker);
        }
    }
}
