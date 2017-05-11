package com.zhouwei.androidtrainingsimples.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zhouwei.androidtrainingsimples.R;

/**
 * Created by zhouwei on 17/5/10.
 */

public class ServiceSimpleActivity extends AppCompatActivity implements View.OnClickListener{
    private MusicService.LocalService mLocalService;
    private MusicService mMusicService;
    // 绑定/解除绑定 Service 回调接口
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //1 ,获取Binder接口对象
            mLocalService = (MusicService.LocalService) service;
            //2, 获取MusicService 实例
            mMusicService = mLocalService.getService();

            // 只要拿到Music Service 实例之后，就可以调用接口方法了
            // 可以通过它来播放／暂停音乐，还可以通过它来获取当前播放音乐的进度，时长等等

            mMusicService.play();

            mMusicService.pause();

            mMusicService.stop();

            int progress = mMusicService.getProgress();
            Log.i(MusicService.TAG,"progress:"+progress);
            int duration = mMusicService.getDuration();
            Log.i(MusicService.TAG,"duration:"+duration);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
           // 解除绑定后回调
            mMusicService = null;
            mLocalService = null;
        }
    };
    private ImageView mImageView;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 显示图片
            Bitmap bitmap = intent.getParcelableExtra("bitmap");
            mImageView.setImageBitmap(bitmap);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_simple_layout);

        findViewById(R.id.start_service).setOnClickListener(this);
        findViewById(R.id.stop_service).setOnClickListener(this);
        findViewById(R.id.bind_service).setOnClickListener(this);
        findViewById(R.id.unbind_service).setOnClickListener(this);
        findViewById(R.id.start_download).setOnClickListener(this);
        mImageView = (ImageView) findViewById(R.id.test_image);
        // 注册Receiver
        IntentFilter intentFilter = new IntentFilter(DownloadService.RECEIVER_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        start_service();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_service:
                //start_service();
                startItentService();
                break;
            case R.id.stop_service:
                Intent service = new Intent(this,SimpleService.class);
                // 停止服务
                stopService(service);
                break;
            case R.id.start_download:
                startDownload();
                break;
            case R.id.bind_service:
                Intent intent = new Intent(this,MusicService.class);
                // 绑定服务
                bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                // 解除绑定服务
                unbindService(mConnection);

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    private void start_service(){
        Intent intent = new Intent(this,DownloadService.class);
        // 启动服务
        intent.setAction(DownloadService.ACTION_START_SERVICER);
        startService(intent);
    }

    /**
     * 启动下载
     */
    private void startDownload(){
        Intent intent = new Intent(this,DownloadService.class);
        // 启动服务
        intent.putExtra(DownloadService.IMAGE,"http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg");
        intent.setAction(DownloadService.ACTION_DOWNLOAD);
        startService(intent);
    }

    private void startItentService(){
        Intent service = new Intent (this,MyIntentService.class);
        startService(service);
    }
}
