package com.seongwon.publictransport.websocket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.websocket.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seongwon.publictransport.domain.BusArrivalItem;
import com.seongwon.publictransport.domain.BusArrivalList;
import com.seongwon.publictransport.domain.RealtimeArrivalList;
import com.seongwon.publictransport.domain.TrainArrivalList;

public class SubThread extends Thread{

	private static final String key = "pE7Hm4y1v4ifsLF8Gr1BtdCS6wl2b1rGFnL06ZT0cljhCkQhXt%2FAKZco%2FcRUn4r0VBMQB%2F4YVg9FOQeqgfZFlg%3D%3D";
	private Session session;
	private String stationId;
	private String routeId;
	private String staOrder;
	private String index;
	private String stationNM;
	private boolean isLoop=false;
	
	// 버스 알람
	public SubThread(Session session,String index,String stationId,String routeId,String staOrder)
	{
		this.session = session;
		this.index = index;
		this.stationId = stationId;
		this.routeId = routeId;
		this.staOrder = staOrder;
	}
	// 지하철 알람
	public SubThread(Session session,String index,String stationNM)
	{
		this.session = session;
		this.index = index;
		this.stationNM = stationNM;
	}
	
	private String getBusArrivalItem()
	{
        String serviceURL = "http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalItem";
        String requestURL = String.format("%s?serviceKey=%s&stationId=%s&routeId=%s&staOrder=%s",
                serviceURL,key,stationId,routeId,staOrder);
        return requestURL;
	}
	
	private String getRealtimeStationArrival() throws Exception
    {
        String serviceURL = "http://swopenAPI.seoul.go.kr/api/subway/4f737174496a617937346877494366/json/realtimeStationArrival/0/2/";
        
        String encode = URLEncoder.encode(stationNM,"UTF-8");
        String requestURL =  serviceURL + encode;
        
        return requestURL;
    }
	
	// api xml -> Object
	private BusArrivalList convertXmlToObject(InputStream stream) throws Exception
	{
		// jaxb 설정
		JAXBContext jaxbContext = JAXBContext.newInstance(BusArrivalList.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		
		// 마샬링
		return (BusArrivalList) unmarshaller.unmarshal(stream);
	}
	private BusArrivalList requestAPI() throws Exception
	{
		String requestURL = getBusArrivalItem();
		URL url = new URL(requestURL);
		
		return convertXmlToObject(url.openStream());
	}
	
	// api json -> Object
	private TrainArrivalList convertJsonToObject(InputStream stream)  throws Exception
	{
		ObjectMapper objectMapper = new ObjectMapper();
		TrainArrivalList trainList = objectMapper.readValue(stream,TrainArrivalList.class);
		return trainList;
	}
	
	private TrainArrivalList requestAPI2() throws Exception
	{
		String requestURL = getRealtimeStationArrival();
		URL url = new URL(requestURL);
		
		return convertJsonToObject(url.openStream());
	}
	
	private void process() throws Exception
	{
		BusArrivalList busArrivalList = this.requestAPI();
		
		if( (busArrivalList == null) || (busArrivalList.getMsgHeader() == null) ) 
		{
			session.getBasicRemote().sendText(index+",Fail");
			interruptLoop();
			return;
		}
		else if(busArrivalList.getMsgBody() == null)
		{
			session.getBasicRemote().sendText(index+",No_Content");
			interruptLoop();
			return;
		}
		else
		{
			switch(busArrivalList.getMsgHeader().getResultCode())
			{
				case 0:
				case 4:
					if(busArrivalList.getMsgBody().getBusArrivalItems().length==0)
					{
						session.getBasicRemote().sendText(index+",Fail");
						interruptLoop();
						return;
					}
					for(BusArrivalItem item : busArrivalList.getMsgBody().getBusArrivalItems())
					{
						// 루프돌면서 클라이언트에게 정보 전달
						ArrayList<String> response = new ArrayList<String>();
						response.add(index);
						
						if(item.getFlag()!=null)
							response.add("flag:"+item.getFlag());
						
						if(item.getLocationNo1()!=null)
							response.add("locationNo1:"+item.getLocationNo1());
						
						if(item.getPredictTime1()!=null)
							response.add("predictTime1:"+item.getPredictTime1());
						
						if(item.getRemainSeatCnt1()!=null)
							response.add("remainSeatCnt1:"+item.getRemainSeatCnt1());
						
						if(item.getLocationNo2()!=null)
							response.add("locationNo2:"+item.getLocationNo2());
						
						if(item.getPredictTime2()!=null)
							response.add("predictTime2:"+item.getPredictTime2());
						
						if(item.getRemainSeatCnt2()!=null)
							response.add("remainSeatCnt2:"+item.getRemainSeatCnt2());
						
						String tmp = String.join(",",response);
						
						if(response.size()!=0)
							session.getBasicRemote().sendText(tmp);
					}
					break;
				default:
					session.getBasicRemote().sendText(index+",Fail");
					interruptLoop();
					return;
			}
		}
	}
	private void process2() throws Exception
	{
		TrainArrivalList trainArrivalList = requestAPI2();
		
		if( (trainArrivalList == null) || (trainArrivalList.getErrorMessage()== null) ) 
		{
			session.getBasicRemote().sendText(index+",Fail");
			interruptLoop();
			return;
		}
		else if(trainArrivalList.getRealtimeArrivalList() == null)
		{
			session.getBasicRemote().sendText(index+",No_Content");
			interruptLoop();
			return;
		}
		else
		{
			if((trainArrivalList.getErrorMessage().getStatus()==200) && (trainArrivalList.getErrorMessage().getTotal()>=1) )
			{
				ArrayList<String> response = new ArrayList<String>();
				RealtimeArrivalList[] list=  trainArrivalList.getRealtimeArrivalList();
				
				response.add(index);
				
				for(int i=0;i<list.length;i++)
				{ 
					if(i==0)
					{
						response.add("trainLineNm1:"+list[i].getTrainLineNm()); // 지하철 노선명
						response.add("arvlMsg1:"+list[i].getArvlMsg2()); // 상태
						response.add("arvlMsg2:"+list[i].getArvlMsg3()); // 현재 위치
					}
					else
					{
						response.add("trainLineNm2:"+list[i].getTrainLineNm()); // 지하철 노선명
						response.add("arvlMsg3:"+list[i].getArvlMsg2()); // 상태
						response.add("arvlMsg4:"+list[i].getArvlMsg3()); // 현재 위치
					}
				}
				
				String tmp = String.join(",",response);
				if(response.size()!=0)
					session.getBasicRemote().sendText(tmp);
			}
			else
			{
				session.getBasicRemote().sendText(index+",Fail");
				interruptLoop();
				return;
			}
		}
	}
	
	public synchronized void loop() throws Exception
	{
		while(isLoop)
		{
			process();
			this.wait(60000); // 1분 대기
		}
	}
	// 지하철 루프
	public synchronized void loop2() throws Exception
	{
		while(isLoop)
		{
			process2();
			this.wait(60000); // 1분 대기
		}
	}
	
	public void interruptLoop() throws Exception
	{
		isLoop = false;
		this.interrupt();
	}
	
	@Override
	public void run() 
	{
		try 
		{	
			isLoop = true;
			if(stationNM == null)
				loop();
			else
				loop2();
		}
		catch(Exception e) {
			this.interrupt();
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s",session.getId(),stationId,routeId,staOrder,stationNM);
	}

}
