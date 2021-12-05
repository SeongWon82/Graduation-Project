package com.seongwon.publictransportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TrainActivity extends AppCompatActivity {

    enum TOUCH_MODE {
        NONE,   // 터치 안했을 때
        SINGLE, // 한손가락 터치
        MULTI   //두손가락 터치
    }

    private ImageButton ib_route_back;
    private Button btn_train_alram;
    private TextView tv_route,tv_station;
    private String station_nm;
    private int status;

    private ImageView imageView;
    private TOUCH_MODE touchMode;

    private Matrix matrix;      //기존 매트릭스
    private Matrix savedMatrix; //작업 후 이미지에 매핑할 매트릭스

    private PointF startPoint;  //한손가락 터치 이동 포인트

    private PointF midPoint;    //두손가락 터치 시 중신 포인트
    private float oldDistance;  //터치 시 두손가락 사이의 거리

    private double oldDegree = 0; // 두손가락의 각도


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        matrix = new Matrix();
        savedMatrix = new Matrix();

        tv_route = (TextView) findViewById(R.id.tv_route);
        tv_route.setText(this.getIntent().getStringExtra("route"));

        station_nm = this.getIntent().getStringExtra("station");

        tv_station = (TextView) findViewById(R.id.tv_station);
        tv_station.setText(station_nm);

        imageView = findViewById(R.id.imageView);
        imageView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (v.equals(imageView))
                {
                    int action = event.getAction();
                    switch (action & MotionEvent.ACTION_MASK)
                    {
                        case MotionEvent.ACTION_DOWN:
                            touchMode = TOUCH_MODE.SINGLE;
                            donwSingleEvent(event);
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            if (event.getPointerCount() == 2) { // 두손가락 터치를 했을 때
                                touchMode = TOUCH_MODE.MULTI;
                                downMultiEvent(event);
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (touchMode == TOUCH_MODE.SINGLE) {
                                moveSingleEvent(event);
                            } else if (touchMode == TOUCH_MODE.MULTI) {
                                moveMultiEvent(event);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_POINTER_UP:
                            touchMode = TOUCH_MODE.NONE;
                            break;
                    }
                }
                return true;
            }
        });
        imageView.setScaleType(ImageView.ScaleType.MATRIX); // 스케일 타입을 매트릭스로 해줘야 움직인다.

        status = findAlram();

        btn_train_alram = (Button) findViewById(R.id.btn_train_alram);
        if(status==-1)
            btn_train_alram.setText("알람 받기");
        else
            btn_train_alram.setText("알람 취소");
        btn_train_alram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 지하철 알림 버튼
                if(addAlram())
                    btn_train_alram.setText("알람 취소");
                else
                    btn_train_alram.setText("알람 받기");
            }
        });

        ib_route_back = (ImageButton) findViewById(R.id.ib_route_back);
        ib_route_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private int findAlram()
    {
        String tmp;
        for(int i=1;i<=3;i++) {
            if(!Static.alramMap.get(i).isCondition()) {
                tmp = Static.alramMap.get(i).stationName;
                if (tmp != null && tmp.equals(station_nm)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean deleteAlram() throws Exception {

        int find = findAlram();

        if(find != -1)
        {
            new Task("trainClose", find, station_nm )
                    .execute()
                    .get();
            Toast.makeText(getApplicationContext(), "알람 취소", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    private boolean addAlram()
    {
        String result;

        switch (Static.getStatus())
        {
            case "native":
            case "kakao":
                try
                {
                    boolean isDelete = deleteAlram();
                    if(isDelete==false)
                    {
                        int rest = Static.getRestIndex();

                        if(rest == -1)
                            Toast.makeText(getApplicationContext(), "목록이 가득 찼습니다.", Toast.LENGTH_LONG).show();
                        else {
                            Static.alramMap.get(rest).stationName = station_nm;
                            result = (String) new Task("trainStart", rest, station_nm )
                                    .execute()
                                    .get();
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
                catch (Exception e) {
                    Log.d("Exception",e.getMessage());
                }
                break;
            case "non_member":
                Toast.makeText(getApplicationContext(), "비회원 이용불가", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    private PointF getMidPoint(MotionEvent e) {

        float x = (e.getX(0) + e.getX(1)) / 2;
        float y = (e.getY(0) + e.getY(1)) / 2;

        return new PointF(x, y);
    }

    private float getDistance(MotionEvent e) {
        float x = e.getX(0) - e.getX(1);
        float y = e.getY(0) - e.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
    private void donwSingleEvent(MotionEvent event) {
        savedMatrix.set(matrix);
        startPoint = new PointF(event.getX(), event.getY());
    }
    private void downMultiEvent(MotionEvent event) {
        oldDistance = getDistance(event);
        if (oldDistance > 5f) {
            savedMatrix.set(matrix);
            midPoint = getMidPoint(event);
            double radian = Math.atan2(event.getY() - midPoint.y, event.getX() - midPoint.x);
            oldDegree = (radian * 180) / Math.PI;
        }
    }
    private void moveSingleEvent(MotionEvent event) {
        matrix.set(savedMatrix);
        matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
        imageView.setImageMatrix(matrix);
    }
    private void moveMultiEvent(MotionEvent event) {
        float newDistance = getDistance(event);
        if (newDistance > 5f) {
            matrix.set(savedMatrix);
            float scale = newDistance / oldDistance;
            matrix.postScale(scale, scale, midPoint.x, midPoint.y);

            double nowRadian = Math.atan2(event.getY() - midPoint.y, event.getX() - midPoint.x);
            double nowDegress = (nowRadian * 180) / Math.PI;
            float degree = (float) (nowDegress - oldDegree);
            matrix.postRotate(degree, midPoint.x, midPoint.y);


            imageView.setImageMatrix(matrix);

        }
    }
}