package com.zhouwei.androidtrainingsimples.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by zhouwei on 17/5/11.
 */

public class DownloadService extends Service {
    public static final String IMAGE = "iamge_url";
    public static final String RECEIVER_ACTION = "com.zhouwei.simpleservice";
    private static final String TAG = "DownloadService";
    public static final String ACTION_START_SERVICER = "com.zhouwei.startservice";
    public static final String ACTION_DOWNLOAD = "com.zhouwei.startdownload";
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // 工作线程做耗时下载
            String url = (String) msg.obj;
            Bitmap bitmap = null;
            try {
                bitmap = Picasso.with(getApplicationContext()).load(url).get();
                Intent intent = new Intent();
                intent.putExtra("bitmap",bitmap);
                intent.setAction(RECEIVER_ACTION);
                // 通知显示
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }


            //工作完成之后，停止服务
            stopSelf();
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        // 开启一个工作线程做耗时工作
        HandlerThread thread = new HandlerThread("ServiceHandlerThread", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        // 获取工作线程的Looper
        mServiceLooper = thread.getLooper();
        // 创建工作线程的Handler
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"call onStartCommand...");
        if(intent.getAction().equals(ACTION_DOWNLOAD)){
            handleCommand(intent);
        }else if(intent.getAction().equals(ACTION_START_SERVICER)){
            //do nothing
        }

        return START_STICKY;
    }

    private void handleCommand(Intent intent){
        String url = intent.getStringExtra(IMAGE);
        // 发送消息下载
        Message message = mServiceHandler.obtainMessage();
        message.obj = url;
        mServiceHandler.sendMessage(message);
    }
}
