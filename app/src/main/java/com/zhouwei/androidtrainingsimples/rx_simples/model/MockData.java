package com.zhouwei.androidtrainingsimples.rx_simples.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/6/19.
 */

public class MockData {
    /**
     * 数据模拟
     * @param classId
     * @return
     */
    public static List<Student> getAllStudentInfoById(int classId){
        List<Student> students = new ArrayList<>();
        Student student = new Student("张三",1,getSources(1));
        students.add(student);

        Student student1 = new Student("张三",2,getSources(2));
        students.add(student1);

        Student student2 = new Student("张三",3,getSources(3));
        students.add(student2);

        return students;
    }

    private static List<Source> getSources(int id){

        List<Source> sources = new ArrayList<>();

        Source source = new Source(1,"语文",90+id);
        sources.add(source);

        Source source1 = new Source(1,"数学",80+id);
        sources.add(source1);

        Source source2 = new Source(1,"英语",85+id);
        sources.add(source2);


        return sources;
    }
}
