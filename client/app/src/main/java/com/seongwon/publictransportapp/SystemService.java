package com.seongwon.publictransportapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.kakao.sdk.talk.TalkApiClient;
import com.kakao.sdk.template.model.Link;
import com.kakao.sdk.template.model.TextTemplate;

public class SystemService extends Service {

    private static Context mContext;
    private static long index;


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        index = 1L;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null){
            // 서비스가 종료 시 자동으로 재시작 옵션
            return Service.START_STICKY;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    private static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static void alram(String title, String content)
    {
        try {
            NotificationCompat.Builder mBuilder = null;
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

            String channelID = null;
            index++;

            // 알람 진동 울리기
            Vibrator vibrator = (Vibrator)mContext.getSystemService(Context.VIBRATOR_SERVICE);
            if(Build.VERSION.SDK_INT >= 26)
            {
                vibrator.vibrate(VibrationEffect.createOneShot(500, 10));
            }
            else //26보다 낮으면
            {
                vibrator.vibrate(500);
            }

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                channelID="channel_"+index; //알림채널 식별자
                String channelName="MyChannel"+index; //알림채널의 이름(별명)

                NotificationChannel channel= new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);

                mBuilder = new NotificationCompat.Builder(mContext, channelID);
            }
            else
            {
                mBuilder = new NotificationCompat.Builder(mContext, channelID);
            }

            mBuilder.setSmallIcon(R.drawable.ic_route);
            mBuilder.setContentTitle(title+"\n");
            mBuilder.setContentText(content);
            mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));
            mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);

            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_alram);
            mBuilder.setLargeIcon(drawableToBitmap(drawable));
            mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            mBuilder.setAutoCancel(true);

            notificationManager.notify(1,mBuilder.build());
        }catch (Exception e){
            Log.d("Exception",e.getMessage());
        }
    }
    // 나에게 알람 보내기 메소드 (카카오)
    public static void sendMeKaKaoMsg(String title,String msg)
    {
        TextTemplate textTemplate = new TextTemplate(title+"\n\n"+msg,
                new Link("https://developers.kakao.com","https://developers.kakao.com"));

        TalkApiClient.getInstance().sendDefaultMemo(textTemplate, error -> {
            if (error != null) {
                // 메시지 전송 성공
            } else {
                // 메시지 전송 실패
            }
            return null;
        });
    }
}