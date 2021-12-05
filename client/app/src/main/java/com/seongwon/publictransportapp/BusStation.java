package com.seongwon.publictransportapp;

public class BusStation {

    private int stationSeq; // 정류장 순서
    private String stationName; // 정류장 이름
    private String stationId; // 정류소 Id 번호
    private String mobileNo; // 정류소 모바일 ID 번호
    private double x = -1; // 정류장 지도상 x 좌표 값
    private double y = -1; // 정류장 지도상 y 좌표 값

    public int getStationSeq() { return stationSeq; }
    public void setStationSeq(int stationSeq) { this.stationSeq = stationSeq; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public String getStationId() { return stationId; }
    public void setStationId(String stationId) { this.stationId = stationId; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public BusStation(){}
    public BusStation(int stationSeq,String stationName,String stationId,String mobileNo,double x,double y){
        this.stationSeq = stationSeq;
        this.stationName = stationName;
        this.stationId = stationId;
        this.mobileNo = mobileNo;
        this.x = x;
        this.y = y;
    }
}
