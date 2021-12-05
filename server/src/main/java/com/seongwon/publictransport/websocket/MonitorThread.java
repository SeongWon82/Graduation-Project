package com.seongwon.publictransport.websocket;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.seongwon.publictransport.domain.BusArrivalItem;
import com.seongwon.publictransport.domain.BusArrivalList;

public class MonitorThread implements Runnable{

	private static final List<SubThread> activeTasks = Collections.synchronizedList(new ArrayList<>());
	private SubThread thread;
	
	public MonitorThread(SubThread thread){this.thread = thread;}
	
	@Override
	public void run() 
	{
		activeTasks.add(thread);
		thread.run();
		activeTasks.remove(thread);
	}
	
	public static void interrupt(String comp) throws Exception
	{
		for(int i=0;i<activeTasks.size();i++)
		{
			if(activeTasks.get(i).toString().equals(comp))
			{
				activeTasks.get(i).interruptLoop();
				return;
			}
		}
	}
	
	public static void shutdown() throws Exception
	{
		for(int i=0;i<activeTasks.size();i++)
		{
			activeTasks.get(i).interruptLoop();
		}
	}
}
