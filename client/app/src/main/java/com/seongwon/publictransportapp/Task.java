package com.seongwon.publictransportapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import tech.gusavila92.websocketclient.WebSocketClient;

public class Task extends AsyncTask<Void, Void, Object> {

    private ArrayList<Bus> list = null;
    private ArrayList<BusStation> bs = null;
    private ArrayList<Train> trains = null;
    private String method = null,tag,id,stationId,routeId,staOrder;
    private int position;
    private URL url;
    private DocumentBuilderFactory factory = null;
    private DocumentBuilder builder= null;
    private Document doc = null;
    private NodeList nList,childNodes;
    private Node nNode,childNode;
    private com.seongwon.publictransportapp.User user;
    private static WebSocketClient webSocketClient;
    private String domain = "mppta.cf";
    private String apiURL = "http://"+domain+"/api";
    private int index=0;
    private static StringBuffer sb=null;

    public Task(String tag) { this.tag = tag;}

    public Task(String tag,User user)
    {
        this.tag = tag;
        this.user= user;
    }
    public Task(String tag,String id)
    {
        this.tag = tag;
        this.id = id;
    }
    public Task(String tag,String id, int position)
    {
        this.tag =tag;
        this.id = id;
        this.position = position;
    }
    public Task(String tag, int index, String stationId, String routeId, String staOrder)
    {
        this.tag =tag; // 테그
        this.index = index;
        this.stationId = stationId;
        this.routeId = routeId;
        this.staOrder = staOrder;
    }
    public Task(String tag,int index,String method)
    {
        this.tag =tag;
        this.index = index;
        this.method = method;
    }

    @Override
    protected Object doInBackground(Void... strings)
    {
        int code = -1;
        switch(tag)
        {
            case "busRouteList":
                list = Static.getBuses();
                method = API.getBusRouteList(id); // number
                executeBusRouteList();
                break;
            case "busRouteInfoItem":
                list = Static.getBuses();
                method = API.getBusRouteInfoItem(id); // routeId
                executeBusRouteInfoItem();
                break;
            case "busRouteStationList":
                list = Static.getBuses();
                bs = Static.getBusStations();
                method = API.getBusRouteStationList(id); // routeId
                executeBusRouteStationList();
                break;
            case "SearchInfoBySubwayNameService":
                trains = Static.getTrains();
                if(id.endsWith("호선"))
                    method = API.getSearchInfoBySubwayNameService2(id);
                else
                    method = API.getSearchInfoBySubwayNameService(id);
                executeSearchInfoBySubwayNameService();
                break;
            case "join":
                method = apiURL+ "/join";
                code = executeAuthUser();
                break;
            case "login":
                method = apiURL+ "/login";
                code = executeAuthUser();
                break;
            case "oauth":
                method = apiURL+ "/oauth";
                code = executeAuthUser();
                break;
            case "init":
                this.createWebSocketClient();
                break;
            case "alram":
                if(index != 0 && Static.alramMap.get(index).isCondition()) {
                    sb.setLength(0);
                    sb.append("알람 목록 추가");
                    webSocketClient.send(String.format("%s,%s,%s,%s", index, stationId, routeId, staOrder));
                    Static.alramMap.get(index).setCondition(false);
                    Static.alramMap.get(index).setStationId(stationId);
                    Static.alramMap.get(index).setRouteId(routeId);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return sb.toString();
            case "close":
                if(index != 0) {
                    sb.setLength(0);
                    sb.append("알람 목록 삭제");
                    webSocketClient.send(String.format("%s,%s,%s,%s,%s", index, stationId, routeId, staOrder, "close"));
                    Static.alramMap.get(index).setCondition(true);
                    Static.alramMap.get(index).setLocationNo1("-1");
                    Static.alramMap.get(index).setPredictTime1("-1");
                    Static.alramMap.get(index).setLocationNo2("-1");
                    Static.alramMap.get(index).setPredictTime2("-1");
                }
                return sb.toString();
            case "trainStart":
                if(index != 0 && Static.alramMap.get(index).isCondition()) {
                    sb.setLength(0);
                    sb.append("알람 목록 추가");
                    webSocketClient.send(String.format("%s,%s", index, method));
                    Static.alramMap.get(index).setCondition(false);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return sb.toString();
            case "trainClose":
                sb.setLength(0);
                sb.append("알람 목록 삭제");
                webSocketClient.send(String.format("%s,%s,close", index, method));
                Static.alramMap.get(index).setCondition(true);
                Static.alramMap.get(index).setTrainLineNm("---");
                Static.alramMap.get(index).setTrainLineNm2("---");
                break;
        }
        if(code == 1)
            return new Boolean(Boolean.TRUE);
        else if(code == 0)
            return new Boolean(Boolean.FALSE);
        else
            return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

        int time = -1;
        StringBuilder content = new StringBuilder();
        switch (Static.getStatus())
        {
            case "kakao": case "native":
                for(int i=1;i<=3;i++)
                {
                   if(Static.alramMap.get(i).isCondition()==false)
                   {
                       if(Static.alramMap.get(i).getTrainLineNm()==null)
                       {
                           time = Integer.parseInt(Static.alramMap.get(i).getPredictTime1());
                           if (time <= 5) {
                               content.append(String.format("%s(%s)\n", Static.alramMap.get(i).number, Static.alramMap.get(i).stationName));
                               if (time <= 1)
                                   content.append("-- 곧 도착 합니다. --");
                               else {
                                   content.append(String.format("- 남은 정류장 수: %s\n- 예상 시간: %s",
                                           Static.alramMap.get(i).getLocationNo1(),
                                           Static.alramMap.get(i).getPredictTime1()));
                               }
                           }
                       }
                       else
                       {
                           content.append(String.format("%s(%s)\n",Static.alramMap.get(i).stationName,Static.alramMap.get(i).getTrainLineNm()));
                           content.append(String.format("- 남은 정류장: %s\n- 현재 위치: %s",
                                   Static.alramMap.get(i).getLocationNo1(),
                                   Static.alramMap.get(i).getPredictTime1()));
                       }
                   }
                }
                if( content.toString().length() != 0  )
                {
                    if(Static.getStatus().equals("kakao"))
                        SystemService.sendMeKaKaoMsg("알람 수신",content.toString());
                    SystemService.alram("알람 수신",content.toString());
                }
                break;
        }
        AlramFragment.update();
    }

    private void setBusItem(int index, String name)
    {
        switch(name)
        {
            // busRouteList items
            case "districtCd":
                list.get(index).setDistrictCd(childNode.getFirstChild().getNodeValue());
                break;
            case "regionName":
                list.get(index).setRegionName(childNode.getFirstChild().getNodeValue());
                break;
            case "routeId":
                list.get(index).setRouteId(childNode.getFirstChild().getNodeValue());
                break;
            case "routeTypeCd":
                list.get(index).setRouteTypeCd(childNode.getFirstChild().getNodeValue());
                break;
            case "routeName":
                list.get(index).setRouteName(childNode.getFirstChild().getNodeValue());
                break;
            case "routeTypeName":
                list.get(index).setRouteTypeName(childNode.getFirstChild().getNodeValue());
                break;

            // busRouteInfoItem items
            case "startStationName":
                list.get(index).setStartStationName(childNode.getFirstChild().getNodeValue());
                break;
            case "endStationName":
                list.get(index).setEndStationName(childNode.getFirstChild().getNodeValue());
                break;
            case "upFirstTime":
                list.get(index).setUpFirstTime(childNode.getFirstChild().getNodeValue());
                break;
            case "downFirstTime":
                list.get(index).setDownFirstTime(childNode.getFirstChild().getNodeValue());
                break;
            case "upLastTime":
                list.get(index).setUpLastTime(childNode.getFirstChild().getNodeValue());
                break;
            case "downLastTime":
                list.get(index).setDownLastTime(childNode.getFirstChild().getNodeValue());
                break;
            case "companyName":
                list.get(index).setCompanyName(childNode.getFirstChild().getNodeValue());
                break;
        }
    }

    private void setTrainItem(int index, String name)
    {
        switch (name)
        {
            case "STATION_CD":
                trains.get(index).setStation_cd(childNode.getFirstChild().getNodeValue());
                break;
            case "STATION_NM":
                trains.get(index).setStation_nm(childNode.getFirstChild().getNodeValue());
                break;
            case "LINE_NUM":
                trains.get(index).setLine_num(childNode.getFirstChild().getNodeValue());
                break;
        }
    }

    private void init(String tagName) throws Exception
    {
        url = new URL(method);
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        doc = builder.parse(url.openStream());
        nList = doc.getElementsByTagName(tagName);
    }
    private void executeBusRouteList()
    {
        try
        {
            // 변수 설정
            init(tag);
            // 버스 데이터 목록 생성
            for(int i = 0; i < nList.getLength(); i++)
            {
                nNode = nList.item(i);
                childNodes=nNode.getChildNodes();
                list.add(new Bus());
                // API 호출 결과 리스트 삽입
                for(int j=0;j<childNodes.getLength();j++)
                {
                    childNode = childNodes.item(j);
                    if(childNode.getNodeType() == Node.ELEMENT_NODE)
                        setBusItem(i,childNode.getNodeName());
                }
            }
        }
        catch(Exception e)
        {
            Log.d("Exception",e.getMessage());
        }
    }
    private void executeBusRouteInfoItem()
    {
        try
        {
            // 변수 설정
            init(tag);
            // 호출 결과 데이터 노드 가져오기
            nNode = nList.item(0);
            childNodes=nNode.getChildNodes();
            // API 호출 결과 리스트 삽입
            for(int i=0;i<childNodes.getLength();i++)
            {
                childNode = childNodes.item(i);
                if(childNode.getNodeType() == Node.ELEMENT_NODE)
                    setBusItem(position,childNode.getNodeName());
            }
        }
        catch (Exception e)
        {
            Log.d("exception",e.getMessage());
        }
    }
    private void executeBusRouteStationList()
    {
        String startStationName = Static.getBuses().get(position).getStartStationName();
        String endStationName = Static.getBuses().get(position).getEndStationName();
        String stationName;
        int st_index=0,ed_index=0;
        try
        {
            init(tag);
            for(int i = 0; i < nList.getLength(); i++)
            {
                nNode = nList.item(i);
                childNodes=nNode.getChildNodes();
                bs.add(new BusStation());
                // API 호출 결과 리스트 삽입
                for(int j=0;j<childNodes.getLength();j++)
                {
                    childNode = childNodes.item(j);
                    if(childNode.getNodeType() == Node.ELEMENT_NODE)
                    {
                        switch(childNode.getNodeName())
                        {
                            case "stationId":
                                bs.get(i).setStationId(childNode.getFirstChild().getNodeValue());
                                break;
                            case "stationSeq":
                                bs.get(i).setStationSeq(Integer.parseInt(childNode.getFirstChild().getNodeValue()));
                                break;
                            case "stationName":
                                stationName = childNode.getFirstChild().getNodeValue();
                                if(stationName.equals(startStationName))
                                    st_index = i;
                                else if(stationName.equals(endStationName))
                                    ed_index = i;
                                bs.get(i).setStationName(stationName);
                                break;
                            case "mobileNo":
                                bs.get(i).setMobileNo(childNode.getFirstChild().getNodeValue());
                                break;
                            case "x":
                                bs.get(i).setX(Double.parseDouble(childNode.getFirstChild().getNodeValue()));
                                break;
                            case "y":
                                bs.get(i).setY(Double.parseDouble(childNode.getFirstChild().getNodeValue()));
                                break;
                        }
                    }
                }
            }
            Static.setSt_x(bs.get(st_index).getX());
            Static.setSt_y(bs.get(st_index).getY());
            Static.setEd_x(bs.get(ed_index).getX());
            Static.setEd_y(bs.get(ed_index).getY());
        }
        catch(Exception e)
        {
            Log.d("Exception",e.getMessage());
        }
    }
    private int executeAuthUser()
    {
        int result = 0;
        try
        {
            // REST API 설정
            url = new URL(method);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            // BODY 부분 정의 & API 요청
            OutputStream os = conn.getOutputStream();
            String jsonData = new Gson().toJson(user);
            byte[] requestData = jsonData.getBytes("utf-8");
            os.write(requestData);
            os.flush();

            // 응답
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                result = 1;

            //자원 반납
            os.close();
            conn.disconnect();
        }
        catch (Exception e){
            Log.d("exception",e.getMessage());
        }
        return result;
    }
    private void executeSearchInfoBySubwayNameService()
    {
        try {
            init("row");
            for(int i = 0; i < nList.getLength(); i++)
            {
                nNode = nList.item(i);
                childNodes=nNode.getChildNodes();
                trains.add(new Train());
                // API 호출 결과 리스트 삽입
                for(int j=0;j<childNodes.getLength();j++)
                {
                    childNode = childNodes.item(j);
                    if(childNode.getNodeType() == Node.ELEMENT_NODE)
                        setTrainItem(i,childNode.getNodeName());
                }
            }
        }catch (Exception e){
            Log.d("train",e.getMessage());
        }

    }

    private void createWebSocketClient()
    {

        if(Static.getStatus().equals("non_member"))
            return;

        URI uri;
        try {
            uri = new URI("ws://"+domain+"/websocket");
        }
        catch (URISyntaxException e) {
            Log.e("URISyntaxException",e.getMessage());
            return;
        }
        webSocketClient = new WebSocketClient(uri)
        {
            @Override
            public void onOpen() {
                sb = new StringBuffer();
            }

            @Override
            public void onTextReceived(String message)
            {
                String[] split = message.split(",");
                String[] tmp;

                int cur = Integer.parseInt(split[0]);

                switch(split[1])
                {
                    case "Fail": case "No_Content":
                        sb.setLength(0);
                        sb.append("정보를 찾지 못했습니다.");
                        Static.alramMap.get(cur).setCondition(true);
                        return;
                }

                for(String sub : split)
                {
                    tmp = sub.split(":");
                    if (tmp.length == 2) {
                        switch (tmp[0]) {
                            case "flag":
                                Static.alramMap.get(cur).setFlag(tmp[1]);
                                break;
                            case "locationNo1":  case "arvlMsg1":
                                Static.alramMap.get(cur).setLocationNo1(tmp[1]);
                                break;
                            case "predictTime1":  case "arvlMsg2":
                                Static.alramMap.get(cur).setPredictTime1(tmp[1]);
                                break;
                            case "remainSeatCnt1":
                                Static.alramMap.get(cur).setRemainSeatCnt1(tmp[1]);
                                break;
                            case "locationNo2": case "arvlMsg3":
                                Static.alramMap.get(cur).setLocationNo2(tmp[1]);
                                break;
                            case "predictTime2": case "arvlMsg4":
                                Static.alramMap.get(cur).setPredictTime2(tmp[1]);
                                break;
                            case "remainSeatCnt2":
                                Static.alramMap.get(cur).setRemainSeatCnt2(tmp[1]);
                                break;
                            case "trainLineNm1":
                                Static.alramMap.get(cur).setTrainLineNm(tmp[1].split(" ")[0]);
                                break;
                            case "trainLineNm2":
                                Static.alramMap.get(cur).setTrainLineNm2(tmp[1].split(" ")[0]);
                                break;
                        }
                    }
                }
                publishProgress();
                return;
            }

            @Override
            public void onBinaryReceived(byte[] data) {
                Log.d("webSocket","onBinaryReceived");
            }

            @Override
            public void onPingReceived(byte[] data) {
                Log.d("webSocket","onPingReceived");
            }

            @Override
            public void onPongReceived(byte[] data) {
                Log.d("webSocket","onPongReceived");
            }

            @Override
            public void onException(Exception e) {
                Log.e("webSocket",e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                Log.d("webSocket","onCloseReceived");
                webSocketClient.close();
            }
        };
        webSocketClient.setConnectTimeout(100000);
        webSocketClient.connect();
    }
}
