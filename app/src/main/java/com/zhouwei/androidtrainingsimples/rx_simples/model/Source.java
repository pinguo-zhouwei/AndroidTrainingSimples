package com.zhouwei.androidtrainingsimples.rx_simples.model;

/**
 * Created by zhouwei on 17/6/19.
 */

public class Source {
    public int sourceId;//id
    public String name;//课程名
    public int score;//成绩

    public Source(int sourceId, String name, int score) {
        this.sourceId = sourceId;
        this.name = name;
        this.score = score;
    }
}
