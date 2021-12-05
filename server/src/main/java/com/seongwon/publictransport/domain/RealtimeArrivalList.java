package com.seongwon.publictransport.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RealtimeArrivalList {

	private String trainLineNm;
	private String arvlMsg2;
	private String arvlMsg3;
	
	public String getTrainLineNm() {
		return trainLineNm;
	}
	public void setTrainLineNm(String trainLineNm) {
		this.trainLineNm = trainLineNm;
	}
	public String getArvlMsg2() {
		return arvlMsg2;
	}
	public void setArvlMsg2(String arvlMsg2) {
		this.arvlMsg2 = arvlMsg2;
	}
	public String getArvlMsg3() {
		return arvlMsg3;
	}
	public void setArvlMsg3(String arvlMsg3) {
		this.arvlMsg3 = arvlMsg3;
	}

}
