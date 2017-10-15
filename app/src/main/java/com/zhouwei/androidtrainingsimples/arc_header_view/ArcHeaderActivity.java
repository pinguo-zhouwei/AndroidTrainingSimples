package com.zhouwei.androidtrainingsimples.arc_header_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zhouwei.androidtrainingsimples.R;
import com.zhouwei.androidtrainingsimples.utils.StatusBarUtils;

/**
 * Created by zhouwei on 17/10/15.
 */

public class ArcHeaderActivity extends AppCompatActivity {
    private ArcHeaderView mArcHeaderView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arc_header_layout_activity);
        StatusBarUtils.setColor(this,getResources().getColor(R.color.start_color),0);

        initView();
    }

    private void initView() {
        mArcHeaderView = (ArcHeaderView) findViewById(R.id.header_view);
        mArcHeaderView.setColor(getResources().getColor(R.color.start_color),getResources().getColor(R.color.end_color));
    }
}
