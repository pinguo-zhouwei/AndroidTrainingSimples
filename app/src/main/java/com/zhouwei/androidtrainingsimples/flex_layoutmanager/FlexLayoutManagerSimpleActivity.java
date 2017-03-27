package com.zhouwei.androidtrainingsimples.flex_layoutmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zhouwei.androidtrainingsimples.R;

/**
 * Created by zhouwei on 17/3/20.
 */

public class FlexLayoutManagerSimpleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlexManagerFragment flexManagerFragment = new FlexManagerFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.activity_main,flexManagerFragment).commit();

    }
}
