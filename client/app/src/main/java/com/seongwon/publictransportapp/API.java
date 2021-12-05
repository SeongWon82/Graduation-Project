package com.seongwon.publictransportapp;

import java.util.ArrayList;

public class API {

    private static final String key = "pE7Hm4y1v4ifsLF8Gr1BtdCS6wl2b1rGFnL06ZT0cljhCkQhXt%2FAKZco%2FcRUn4r0VBMQB%2F4YVg9FOQeqgfZFlg%3D%3D";
    private static final String trainKey="697146745a6a61793730724e48634f";


    // REST API
    // 버스 기본정보,배차정보 조회
    public static String getBusRouteList(String number)
    {
        String serviceURL = "http://apis.data.go.kr/6410000/busrouteservice/getBusRouteList";
        String requestURL = String.format("%s?serviceKey=%s&keyword=%s",serviceURL,key,number);
        return requestURL;
    }
    // 버스 디테일한 정보 조회
    public static String getBusRouteInfoItem(String routeId)
    {
        String serviceURL = "http://apis.data.go.kr/6410000/busrouteservice/getBusRouteInfoItem";
        String requestURL =String.format("%s?serviceKey=%s&routeId=%s",serviceURL,key,routeId);
        return requestURL;
    }
    // 버스가 지나는 모든 정류장 정보 조회
    public static String getBusRouteStationList(String routeId)
    {
        String serviceURL ="http://apis.data.go.kr/6410000/busrouteservice/getBusRouteStationList";
        String requestURL = String.format("%s?serviceKey=%s&routeId=%s",serviceURL,key,routeId);
        return requestURL;
    }

    // 해당 이름 - 역명 검색
    public static String getSearchInfoBySubwayNameService(String name)
    {
        String serviceURL = "http://openapi.seoul.go.kr:8088";
        return String.format("%s/%s/xml/SearchInfoBySubwayNameService/1/99/%s",serviceURL,trainKey,name);
    }
    // 특정 노선의 모든 정류장 검색
    public static String getSearchInfoBySubwayNameService2(String name)
    {
        String serviceURL = "http://openapi.seoul.go.kr:8088/697146745a6a61793730724e48634f/xml/SearchSTNBySubwayLineInfo/1/99/%20/%20";
        return String.format("%s/%s",serviceURL,name);
    }

    // type
    // 1(green) : 순환, 시내 버스
    // 2(red) : 마을, 농어촌 버스
    // 3(blue) : 공항, 시외 버스
    public static int getTypeCodeNumber(int Code)
    {
        switch (Code)
        {
            // 시내,순환 버스(green)
            case 11: case 12: case 13: case 14: case 15: case 16:
                return 1;
            // 마을, 농어촌(yellow)
            case 21: case 22: case 23: case 30:
                return 2;
            // 시외버스(red)
            case 41: case 42: case 43:
                return 3;
            // 공항버스(blue)
            case 51: case 52: case 53:
                return 4;
        }
        return -1; // 잘못된 입력
    }
}
