package com.seongwon.publictransportapp;

public class Alram {

    public Alram(boolean condition) {
        this.condition = condition;
    }

    public Alram() {}


    public boolean isCondition() {
        return condition;
    }
    public void setCondition(boolean condition) {
        this.condition = condition;
    }
    private boolean condition;

    public String number;
    public String stationName;

    private String stationId;
    private String routeId;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    private String flag;  // 운행 상태  ( RUN:운행중 PASS:운행중 STOP:운행종료 WAIT:회차지대기 )

    private String locationNo1; // 첫번째차량 위치 정보
    private String predictTime1; // 예상시간
    private String remainSeatCnt1; // 남은 좌석

    private String locationNo2;
    private String predictTime2;
    private String remainSeatCnt2;

    private String trainLineNm; // 지하철 노선명1
    private String trainLineNm2; // 지하철 노선명2

    public String getTrainLineNm() { return trainLineNm; }
    public void setTrainLineNm(String trainLineNm) { this.trainLineNm = trainLineNm; }

    public String getTrainLineNm2() { return trainLineNm2; }
    public void setTrainLineNm2(String trainLineNm2) { this.trainLineNm2 = trainLineNm2; }

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getLocationNo1() {
        return locationNo1;
    }
    public void setLocationNo1(String locationNo1) {
        this.locationNo1 = locationNo1;
    }
    public String getPredictTime1() {
        return predictTime1;
    }
    public void setPredictTime1(String predictTime1) {
        this.predictTime1 = predictTime1;
    }
    public String getRemainSeatCnt1() {
        return remainSeatCnt1;
    }
    public void setRemainSeatCnt1(String remainSeatCnt1) {
        this.remainSeatCnt1 = remainSeatCnt1;
    }
    public String getLocationNo2() {
        return locationNo2;
    }
    public void setLocationNo2(String locationNo2) {
        this.locationNo2 = locationNo2;
    }
    public String getPredictTime2() {
        return predictTime2;
    }
    public void setPredictTime2(String predictTime2) {
        this.predictTime2 = predictTime2;
    }
    public String getRemainSeatCnt2() {
        return remainSeatCnt2;
    }
    public void setRemainSeatCnt2(String remainSeatCnt2) {
        this.remainSeatCnt2 = remainSeatCnt2;
    }
}
