package com.zhouwei.androidtrainingsimples.arc_header_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zhouwei.androidtrainingsimples.R;
import com.zhouwei.androidtrainingsimples.utils.StatusBarUtils;

/**
 * Created by zhouwei on 17/10/15.
 */

public class ArcHeaderImageActivity extends AppCompatActivity {
    public static final String URL = "http://7xi8d6.com1.z0.glb.clouddn.com/20171228085004_5yEHju_Screenshot.jpeg";
    private PerfectArcView mArcHeaderView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arc_header_image_layout_activity);
        StatusBarUtils.setColor(this,getResources().getColor(R.color.start_color),0);

        initView();
    }

    private void initView() {
        mArcHeaderView = (PerfectArcView) findViewById(R.id.header_view);
        mArcHeaderView.setImageUrl(URL);
        //mArcHeaderView.setColor(getResources().getColor(R.color.start_color),getResources().getColor(R.color.end_color));
    }
}
