package com.zhouwei.androidtrainingsimples.arc_header_view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhouwei.androidtrainingsimples.R;
import com.zhouwei.androidtrainingsimples.utils.StatusBarUtils;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/10/15.
 */

public class ArcHeaderViewPagerActivity extends AppCompatActivity {
    private ArcHeaderView mArcHeaderView;
    private MZBannerView mMZBannerView;
    public int mStartColor[] ;
    public int mEndColor[] ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arc_header_viewpager_layout_activity);
        StatusBarUtils.setColor(this,getResources().getColor(R.color.start_color),0);

        initView();
    }

    private void initView() {
        mStartColor = new int[]{getResources().getColor(R.color.start_color)
                ,getResources().getColor(R.color.page1_start_color)
                ,getResources().getColor(R.color.page2_start_color)
                ,getResources().getColor(R.color.page3_start_color)};
        mEndColor = new int[]{getResources().getColor(R.color.end_color)
                ,getResources().getColor(R.color.page1_end_color)
                ,getResources().getColor(R.color.page2_end_color)
                ,getResources().getColor(R.color.page3_end_color)};
        mMZBannerView = (MZBannerView) findViewById(R.id.arc_view_pager);
        mArcHeaderView = (ArcHeaderView) findViewById(R.id.header_view);
        mArcHeaderView.setColor(getResources().getColor(R.color.start_color),getResources().getColor(R.color.end_color));

        mMZBannerView.addPageChangeLisnter(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                StatusBarUtils.setColor(ArcHeaderViewPagerActivity.this,mStartColor[position],0);
                mArcHeaderView.setColor(mStartColor[position],mEndColor[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mMZBannerView.setPages(mockData(), new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

    }

    public static class BannerViewHolder implements MZViewHolder<MyData> {
        private TextView text1,text2;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.arc_view_pager_item,null);
            text1 = (TextView) view.findViewById(R.id.arc_title);
            text2 = (TextView) view.findViewById(R.id.arc_content);
            return view;
        }

        @Override
        public void onBind(Context context, int position, MyData data) {
            // 数据绑定
           // mImageView.setImageResource(data);
            text1.setText(data.title);
            text2.setText(data.content);
        }
    }


    public List<MyData> mockData(){
        List<MyData> datas = new ArrayList<>();
        for(int i=0;i<4;i++){
            MyData myData = new MyData();
            myData.title = "公众号: Android技术杂货铺";
            myData.content = "Find your\n"+"Plan"+i;
            datas.add(myData);
        }
        return datas;
    }

    public static class MyData{
        public  String title;
        public String content;
    }
}
