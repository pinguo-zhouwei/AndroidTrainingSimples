package com.zhouwei.androidtrainingsimples.snap_helper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zhouwei.androidtrainingsimples.R;

/**
 * Created by zhouwei on 17/3/29.
 */

public class SnapHelperSimpleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SnapHelperFragment fragment = new SnapHelperFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main,fragment).commit();
    }
}
