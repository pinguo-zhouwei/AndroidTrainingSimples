package com.zhouwei.androidtrainingsimples.rx_simples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhouwei.androidtrainingsimples.R;
import com.zhouwei.androidtrainingsimples.rx_simples.model.MockData;
import com.zhouwei.androidtrainingsimples.rx_simples.model.Source;
import com.zhouwei.androidtrainingsimples.rx_simples.model.Student;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouwei on 17/6/19.
 */

public class RxJavaSimpleActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = "RxJavaSimpleActivity";
    private TextView mTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rx_test);
        findViewById(R.id.test_start).setOnClickListener(this);
        mTextView = (TextView) findViewById(R.id.result_text_rx);

    }

    private void flatMapTest(){
        Observable.fromArray(1,2,3,4,5)
                .concatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(@NonNull Integer integer) throws Exception {

                        int delay = 0;
                        if(integer == 3){
                            delay = 500;//延迟500ms发射
                        }
                        return Observable.just(integer *10).delay(delay, TimeUnit.MILLISECONDS);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e("zhouwei","accept:"+integer);
            }
        });

    }

    @Override
    public void onClick(View v) {
        flatMapTest();

       // printName();

        printSourceRx();
    }



    public void printName(){
        // 从服务器获取班级所有同学信息
        List<Student> students = MockData.getAllStudentInfoById(0);
        // 打印每个学生的名字
        for(int i=0;i<students.size();i++){
            Log.i("zhouwei","student name:"+ students.get(i).name);
        }

        // 需求二:打印每个同学的成绩

        //开启一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 从服务器获取班级所有同学信息
                List<Student> students = MockData.getAllStudentInfoById(0);

                for(int i=0;i<students.size();i++){
                    List<Source> sources = students.get(i).mSources;

                    for (int index=0;index<sources.size();index++){
                        final Source source = sources.get(index);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //主线程更改UI
                                String content = "sourceName:"+source.name +" source score:"+source.score;
                                content = mTextView.getText().toString()+"\n"+content;
                                mTextView.setText(content);
                                Log.i(TAG,content);
                            }
                        });

                    }
                }
            }
        }).start();


    }

    public void printSourceRx(){
        Flowable.fromIterable(MockData.getAllStudentInfoById(0))
            .flatMap(new Function<Student, Publisher<Source>>() {
                @Override
                public Publisher<Source> apply(@NonNull Student student) throws Exception {
                    return Flowable.fromIterable(student.mSources);
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Source>() {
                @Override
                public void accept(@NonNull Source source) throws Exception {
                    String content = "sourceName:"+source.name +" source score:"+source.score;
                    content = mTextView.getText().toString()+"\n"+content;
                    mTextView.setText(content);
                    Log.i(TAG,content);

                }
            });

    }

}
