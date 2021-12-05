package com.seongwon.publictransport.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msgHeader")
public class MsgHeader
{
	@XmlElement(name = "resultCode")
	private int resultCode;

	public int getResultCode() {return resultCode;}

	public void setResultCode(int resultCode) {this.resultCode = resultCode;}
}
