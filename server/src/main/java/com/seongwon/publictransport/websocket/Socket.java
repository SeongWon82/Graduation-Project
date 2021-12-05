package com.seongwon.publictransport.websocket;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.seongwon.publictransport.domain.BusArrivalItem;
import com.seongwon.publictransport.domain.BusArrivalList;

@Component
@ServerEndpoint(value = "/websocket") 
public class Socket 
{
	private Session session;
	private ThreadPoolExecutor threadPoolExecutor;

    @OnOpen //클라이언트가 소켓에 연결되때 마다 호출
    public void onOpen(Session session) 
    {
        this.session = session;
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    }

    @OnClose //클라이언트와 소켓과의 연결이 닫힐때 
    public void onClose(Session session) {
    	try {
    		if(!threadPoolExecutor.isShutdown())
    			threadPoolExecutor.shutdown();
    		this.session.close();
		} catch (IOException e) {
			System.out.println("에러발생: "+e.getMessage());
		}
    }

    // 메시지 수신
    @OnMessage
    public void onMessage(String message) 
    {	
		try 
		{
			String[] split = message.split(",");
			String comp;
			switch(split.length)
			{
				// index,stationNM
				case 2:
					threadPoolExecutor.execute(new MonitorThread(new SubThread(session,split[0],split[1])));
					break;
				// index,stationNM,close
				case 3:
					comp = String.format("%s,null,null,null,%s",session.getId(),split[1]);
					MonitorThread.interrupt(comp);
					break;
				// index,stationId,routeId,staOrder
				case 4:
					threadPoolExecutor.execute(new MonitorThread(new SubThread(session,split[0],split[1],split[2],split[3])));
					break;
				// index,stationId,routeId,staOrder,close
				case 5:
					comp = String.format("%s,%s,%s,%s,null",session.getId(),split[1],split[2],split[3]);
					MonitorThread.interrupt(comp);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //에러 발생
    @OnError
    public void onError(Session session, Throwable throwable) 
    {
    	//threadPoolExecutor.shutdownNow();
    	if(!threadPoolExecutor.isShutdown())
			threadPoolExecutor.shutdown();
    }
}