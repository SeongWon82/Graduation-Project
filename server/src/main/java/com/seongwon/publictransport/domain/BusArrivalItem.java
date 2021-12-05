package com.seongwon.publictransport.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class BusArrivalItem {

	@XmlElement(name = "flag")
	private String flag;
	
	@XmlElement(name = "locationNo1")
	private String locationNo1;
	
	@XmlElement(name = "predictTime1")
    private String predictTime1; 
	
	@XmlElement(name = "remainSeatCnt1")
    private String remainSeatCnt1; 
    
	@XmlElement(name = "locationNo2")
    private String locationNo2;
	
	@XmlElement(name = "predictTime2")
    private String predictTime2;
	
	@XmlElement(name = "remainSeatCnt2")
    private String remainSeatCnt2;
    
    
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
