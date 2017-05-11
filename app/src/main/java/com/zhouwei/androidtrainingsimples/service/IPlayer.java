package com.zhouwei.androidtrainingsimples.service;

/**
 * Created by zhouwei on 17/5/11.
 */

public interface IPlayer {
    // 播放
    public void play();
    // 暂停
    public void pause();
    // 停止
    public void stop();
    // 获取播放进度
    public int getProgress();
    // 获取时长
    public int getDuration();
}
