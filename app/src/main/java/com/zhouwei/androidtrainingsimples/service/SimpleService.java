package com.zhouwei.androidtrainingsimples.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhouwei on 17/5/10.
 */

public class SimpleService extends Service {
    public static final String TAG = "SimpleService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"call onBind...");
        //返回IBinder 接口对象
        return new MyBinder();
    }



    @Override
    public void onDestroy() {
        Log.i(TAG,"call onDestroy...");
    }

    // 添加一个类继承Binder
    public  class MyBinder extends Binder{
        // 添加要与外界交互的方法
        public String  getStringInfo(){
          return "调用了服务中的方法";
        }
    }

}
