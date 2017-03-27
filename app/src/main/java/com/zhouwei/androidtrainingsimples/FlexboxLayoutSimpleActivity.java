package com.zhouwei.androidtrainingsimples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by zhouwei on 17/3/19.
 */

public class FlexboxLayoutSimpleActivity extends AppCompatActivity {
    private FlexboxLayout mFlexboxLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flexbox_layout);

        initView();
    }

    private void initView() {
        mFlexboxLayout = (FlexboxLayout) findViewById(R.id.flexbox_layout);

        // 通过代码向FlexboxLayout添加View
        TextView textView = new TextView(this);
        textView.setBackground(getResources().getDrawable(R.drawable.label_bg_shape));
        textView.setText("Test  Label");
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(30,0,30,0);
        textView.setTextColor(getResources().getColor(R.color.text_color));
        mFlexboxLayout.addView(textView);
        //通过FlexboxLayout.LayoutParams 设置子元素支持的属性
        ViewGroup.LayoutParams params = textView.getLayoutParams();
        if(params instanceof FlexboxLayout.LayoutParams){
            FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) params;
            layoutParams.setFlexBasisPercent(0.5f);
        }
    }
}
