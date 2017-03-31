package com.zhouwei.androidtrainingsimples.viewpager_wrapper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearSnapHelper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhouwei.androidtrainingsimples.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/3/28.
 */

public class VActivity extends AppCompatActivity implements ViewPagerWrapLayout.OnRightLayoutExpandListener{
    private ViewPager mViewPager;
    private ViewpagerAdapter mAdapter;
    private List<View> views = new ArrayList<>();

    private ViewPagerWrapLayout mWrapLayout;

    private LayoutAnimationController mAnimationController;

    private LinearLayout mRightLayout;
    public static final String content[] = new String []{"First","Second","Third","Four"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page_layout);

        initView();

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mRightLayout = (LinearLayout) findViewById(R.id.right_layout);
        mWrapLayout = (ViewPagerWrapLayout) findViewById(R.id.wrap_layout);
        mWrapLayout.setExpandListener(this);

        //初始化动画
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.text_alpha);
        mAnimationController = new LayoutAnimationController(animation);
        mAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);

        for(int i=0;i<content.length;i++){
           // View view = LayoutInflater.from(this).inflate(R.layout.view_pager_item_layout,null);
            View view1 = View.inflate(this,R.layout.view_pager_item_layout,null);
            TextView text = (TextView) view1.findViewById(R.id.text_content);
            text.setText(content[i]);
            views.add(view1);
        }

        mAdapter = new ViewpagerAdapter();
        mAdapter.setViews(views);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                 if(position == views.size() - 1){
                     mWrapLayout.setLastPage(true);
                 }else{
                     mWrapLayout.setLastPage(false);
                 }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        LinearSnapHelper helper = new LinearSnapHelper();

    }

    @Override
    public void onExpand() {
        startAnim();
    }

    @Override
    public void onClose() {
       mRightLayout.setVisibility(View.GONE);
    }

    private void startAnim(){
      mRightLayout.setVisibility(View.VISIBLE);
      mRightLayout.setLayoutAnimation(mAnimationController);
    }
}
