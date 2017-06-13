package com.zhouwei.androidtrainingsimples.viewpager_transform;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhouwei.androidtrainingsimples.R;
import com.zhouwei.androidtrainingsimples.utils.StatusBarUtils;
import com.zhouwei.blurlibrary.EasyBlur;
import com.zhouwei.indicatorview.CircleIndicatorView;

/**
 * Created by zhouwei on 17/5/21.
 */

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private VPAdapter mVPAdapter;
    private ImageView mImageBg;
    private CircleIndicatorView mCircleIndicatorView;
    private TextView mTitle,mDesc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_transform_layout);
        View view = findViewById(R.id.toolbar);
        StatusBarUtils.setTranslucentImageHeader(this, 0,view);
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.my_viewpager);
        mImageBg = (ImageView) findViewById(R.id.activity_bg);
        mCircleIndicatorView = (CircleIndicatorView) findViewById(R.id.indicatorView);
        mTitle = (TextView) findViewById(R.id.title_name);
        mDesc = (TextView) findViewById(R.id.skin_desc);

        mTitle.setText("黑暗之女");
        mViewPager.setPageTransformer(false,new CustomViewPagerTransformer(this));
        // 添加监听器
        mViewPager.addOnPageChangeListener(onPageChangeListener);
        mVPAdapter = new VPAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mVPAdapter);
        mViewPager.setOffscreenPageLimit(3);
        //  Indicator 和ViewPager 建立关联
        mCircleIndicatorView.setUpWithViewPager(mViewPager);
        // 首次进入展示第二页
        mViewPager.setCurrentItem(1);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mViewPager.onTouchEvent(event);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Bitmap source = BitmapFactory.decodeResource(getResources(),VPAdapter.RES[position]);
            Bitmap bitmap = EasyBlur.with(getApplicationContext())
                    .bitmap(source)
                    .radius(20)
                    .blur();
            mImageBg.setImageBitmap(bitmap);

            mDesc.setText(mVPAdapter.getPageTitle(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
