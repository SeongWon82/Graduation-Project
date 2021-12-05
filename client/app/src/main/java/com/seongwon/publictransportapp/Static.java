package com.seongwon.publictransportapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Static {

    private static ArrayList<Bus> buses = new ArrayList<Bus>();
    private static ArrayList<Train> trains = new ArrayList<Train>();
    private static ArrayList<BusStation> busStations = new ArrayList<BusStation>();
    public static double st_x=-1,st_y=-1,ed_x=-1,ed_y=-1;
    private static String status="non_member";
    private static String userId = null;

    public static HashMap<String,Bus> favorites = new HashMap<String,Bus>();

    public static ConcurrentHashMap<Integer, Alram> alramMap = new ConcurrentHashMap<Integer, Alram>();

    public static ArrayList<BusStation> getBusStations() { return busStations; }
    public static void setBusStations(ArrayList<BusStation> busStations) { Static.busStations = busStations; }

    public static Bus getBusItem(int position) { return buses.get(position);}
    public static Train getTrainItem(int position) {return trains.get(position);}

    public static ArrayList<Bus> getBuses() {return buses;}
    public static ArrayList<Train> getTrains() {return trains;}

    public static double getSt_x() { return st_x; }
    public static void setSt_x(double st_x) { Static.st_x = st_x; }

    public static double getSt_y() { return st_y; }
    public static void setSt_y(double st_y) { Static.st_y = st_y; }

    public static double getEd_x() { return ed_x; }
    public static void setEd_x(double ed_x) { Static.ed_x = ed_x; }

    public static double getEd_y() { return ed_y; }
    public static void setEd_y(double ed_y) { Static.ed_y = ed_y; }

    public static String getStatus() { return status; }
    public static void setStatus(String status) { Static.status = status; }

    public static String getUserId() { return userId; }
    public static void setUserId(String userId) { Static.userId = userId; }

    public static void clearBuses() {buses.clear();}
    public static void clearTrains() {trains.clear();}
    public static void clearBusStations() {busStations.clear();}

    public static int getRestIndex(){
        for(int i=1;i<=3;i++)
        {
            if(Static.alramMap.get(i).isCondition())
                return i;
        }
        return -1;
    }

    public static void changeBusList()
    {
        clearBuses();
        for (Map.Entry<String, Bus> entrySet : favorites.entrySet())
            buses.add(entrySet.getValue());
    }
}
