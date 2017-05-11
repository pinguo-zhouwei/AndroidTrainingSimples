package com.zhouwei.androidtrainingsimples.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

/**
 * Created by zhouwei on 17/5/11.
 */

public class MessengerService extends Service{

    private Messenger mMessenger = new Messenger(new ServiceHandler());

    /**
     * handler 处理客户端发来的消息
     */
    public class ServiceHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
