package com.zhouwei.androidtrainingsimples.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhouwei on 17/5/11.
 */

public class MusicService extends Service implements IPlayer{
    public static final String TAG = "MusicService";
    private LocalService mBinder = new LocalService();
    public class LocalService extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void play() {
        Log.i(TAG,"music play...");
    }

    @Override
    public void pause() {
        Log.i(TAG,"music pause...");
    }

    @Override
    public void stop() {
        Log.i(TAG,"music stop...");
    }

    @Override
    public int getProgress() {
        return 100;
    }

    @Override
    public int getDuration() {
        return 10240;
    }
}
