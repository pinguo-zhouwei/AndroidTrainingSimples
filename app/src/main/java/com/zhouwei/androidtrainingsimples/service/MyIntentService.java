package com.zhouwei.androidtrainingsimples.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by zhouwei on 17/5/11.
 */

public class MyIntentService extends IntentService {
    public static final String TAG ="MyIntentService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
      // 这里已经是工作线程，在这里执行操作就行

       boolean isMainThread =  Thread.currentThread() == Looper.getMainLooper().getThread();
        Log.i(TAG,"is main thread:"+isMainThread);

        // 执行耗时下载操作
        mockDownload();
    }

    /**
     * 模拟执行下载
     */
    private void mockDownload(){
       try {
           Thread.sleep(5000);
           Log.i(TAG,"下载完成...");
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy...");
    }
}
