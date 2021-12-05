package com.seongwon.publictransport.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainArrivalList{
	
	private ErrorMessage errorMessage;
	
	private RealtimeArrivalList[] realtimeArrivalList;

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public RealtimeArrivalList[] getRealtimeArrivalList() {
		return realtimeArrivalList;
	}

	public void setRealtimeArrivalList(RealtimeArrivalList[] realtimeArrivalList) {
		this.realtimeArrivalList = realtimeArrivalList;
	}
	

}
