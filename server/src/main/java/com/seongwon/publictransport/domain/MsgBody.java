package com.seongwon.publictransport.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msgBody")
public class MsgBody
{
	@XmlElement(name = "busArrivalItem")
	private BusArrivalItem[] busArrivalItems;

	public BusArrivalItem[] getBusArrivalItems() {return busArrivalItems;}
	public void setBusArrivalItems(BusArrivalItem[] busArrivalItems) {this.busArrivalItems = busArrivalItems;}
}