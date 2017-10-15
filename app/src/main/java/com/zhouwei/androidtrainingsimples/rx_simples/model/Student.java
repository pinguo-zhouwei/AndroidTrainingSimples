package com.zhouwei.androidtrainingsimples.rx_simples.model;

import java.util.List;

/**
 * Created by zhouwei on 17/6/19.
 */

public class Student {
    public String name;//学生名字
    public int id;
    public List<Source> mSources;//每个学生的所有课程

    public Student(String name, int id, List<Source> sources) {
        this.name = name;
        this.id = id;
        mSources = sources;
    }
}
