package com.seongwon.publictransportapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Bus{

    private String routeId; // 버스 아이디
    private String stationId; // 정류소 아이디

    // 버스 노선 조회 데이터 getBusRouteList
    private String districtCd; // 관할지역 1:서울,2:경기,3:인천
    private String regionName; // 지역이름
    private String routeName; // 노선번호
    private String routeTypeCd; // 노선 유형
    private String routeTypeName; // 노선 유형명

    //버스 노선 정보 항목
    private String startStationName; // 기점 정류소 명
    private String endStationName; // 종점 정류소 명
    private String upFirstTime; // 평일 첫자 기점
    private String downFirstTime; // 평일 첫자 종점
    private String upLastTime; // 평일 막차 기점
    private String downLastTime; // 평일 막차 종점
    private String companyName; // 운수 업체 명

    // 버스 위치 조회 데이터
    private String stationSeq; // 현재 버스의 정류소 순번
    private String endBus; // 막차 여부
    private String lowPlate; // 저상버스 여부 0:일반버스,1:저상버스
    private String plateNo; //차량 번호
    private String plateType; // 차종 0:정보없음 1:소형승합차 2:중형승합차 3:대형승합차
    private String remainSeatCnt; // 남은 좌석 수 (-1:정보없음 0~:빈자리 수)

    // 버스 도착 정보 데이터
    // 첫번쨰 도착 버스
    private String locationNo1; //  남은 정류장 수
    private String predictTime1; // 도착 예정 시간(분 단위)
    private String lowPlate1; // 저상버스 여부
    private String plateNo1;  // 차량 번호
    private String remainSeatCnt1; // 남은 좌석 수
    // 두번쨰 도착 버스
    private String locationNo2; //  남은 정류장 수
    private String predictTime2;  // 도착 예정 시간(분 단위)
    private String lowPlate2; // 저상버스 여부
    private String plateNo2; // 차량 번호
    private String remainSeatCnt2; // 남은 좌석 수

    private String staOrder; // 정류소 순번
    private String flag; // 운행 상태  ( RUN:운행중 PASS:운행중 STOP:운행종료 WAIT:회차지대기 )

    public Bus(){}

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getDistrictCd() {
        return districtCd;
    }

    public void setDistrictCd(String districtCd) {
        this.districtCd = districtCd;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteTypeCd() {
        return routeTypeCd;
    }

    public void setRouteTypeCd(String routeTypeCd) {
        this.routeTypeCd = routeTypeCd;
    }

    public String getRouteTypeName() {
        return routeTypeName;
    }

    public void setRouteTypeName(String routeTypeName) {
        this.routeTypeName = routeTypeName;
    }

    public String getStationSeq() {
        return stationSeq;
    }

    public void setStationSeq(String stationSeq) {
        this.stationSeq = stationSeq;
    }

    public String getEndBus() {
        return endBus;
    }

    public void setEndBus(String endBus) {
        this.endBus = endBus;
    }

    public String getLowPlate() {
        return lowPlate;
    }

    public void setLowPlate(String lowPlate) {
        this.lowPlate = lowPlate;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public String getRemainSeatCnt() {
        return remainSeatCnt;
    }

    public void setRemainSeatCnt(String remainSeatCnt) {
        this.remainSeatCnt = remainSeatCnt;
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

    public String getLowPlate1() {
        return lowPlate1;
    }

    public void setLowPlate1(String lowPlate1) {
        this.lowPlate1 = lowPlate1;
    }

    public String getPlateNo1() {
        return plateNo1;
    }

    public void setPlateNo1(String plateNo1) {
        this.plateNo1 = plateNo1;
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

    public String getLowPlate2() {
        return lowPlate2;
    }

    public void setLowPlate2(String lowPlate2) {
        this.lowPlate2 = lowPlate2;
    }

    public String getPlateNo2() {
        return plateNo2;
    }

    public void setPlateNo2(String plateNo2) {
        this.plateNo2 = plateNo2;
    }

    public String getRemainSeatCnt2() {
        return remainSeatCnt2;
    }

    public void setRemainSeatCnt2(String remainSeatCnt2) {
        this.remainSeatCnt2 = remainSeatCnt2;
    }

    public String getStaOrder() {
        return staOrder;
    }

    public void setStaOrder(String staOrder) {
        this.staOrder = staOrder;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public String getUpFirstTime() {
        return upFirstTime;
    }

    public void setUpFirstTime(String upFirstTime) {
        this.upFirstTime = upFirstTime;
    }

    public String getDownFirstTime() {
        return downFirstTime;
    }

    public void setDownFirstTime(String downFirstTime) {
        this.downFirstTime = downFirstTime;
    }

    public String getUpLastTime() {
        return upLastTime;
    }

    public void setUpLastTime(String upLastTime) {
        this.upLastTime = upLastTime;
    }

    public String getDownLastTime() {
        return downLastTime;
    }

    public void setDownLastTime(String downLastTime) {
        this.downLastTime = downLastTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
