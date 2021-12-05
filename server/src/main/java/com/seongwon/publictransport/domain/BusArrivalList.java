package com.seongwon.publictransport.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class BusArrivalList {
	
	@XmlElement(name = "msgHeader")
	private MsgHeader msgHeader;
	
	@XmlElement(name = "msgBody")
	private MsgBody msgBody;
	
	public MsgHeader getMsgHeader() {return msgHeader;}
	public void setMsgHeader(MsgHeader msgHeader) {this.msgHeader = msgHeader;}

	public MsgBody getMsgBody() {return msgBody;}
	public void setMsgBody(MsgBody msgBody) {this.msgBody = msgBody;}
}
