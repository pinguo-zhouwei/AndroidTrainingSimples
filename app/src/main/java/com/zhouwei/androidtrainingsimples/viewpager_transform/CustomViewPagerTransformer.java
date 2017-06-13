package com.zhouwei.androidtrainingsimples.viewpager_transform;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by zhouwei on 17/5/24.
 */

public class CustomViewPagerTransformer implements ViewPager.PageTransformer {
    private int maxTranslateOffsetX;
    private ViewPager viewPager;
    private static final float MIN_SCALE = 0.75f;


    public CustomViewPagerTransformer(Context context) {
        this.maxTranslateOffsetX = dp2px(context, 160);
    }

    public void transformPage(View view, float position) {
        // position的可能性的值有，其实从官方示例的注释就能看出：
        //[-Infinity,-1)  已经看不到了
        // (1,+Infinity] 已经看不到了
        // [-1,1]
        // 而我们从写PageTransformer，操作View动画的重点区间就在[-1,1]
        Log.e("fuck","view:"+view+"  position:"+position);
        if (viewPager == null) {
            viewPager = (ViewPager) view.getParent();
        }
        int leftInScreen = view.getLeft() - viewPager.getScrollX();
        int centerXInViewPager = leftInScreen + view.getMeasuredWidth() / 2;
        int offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
        float offsetRate = (float) offsetX * 0.38f / viewPager.getMeasuredWidth();
        float scaleFactor = 1 - Math.abs(offsetRate);
        if (scaleFactor > 0) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setTranslationX(-maxTranslateOffsetX * offsetRate);
        }

    }

    /**
     * dp和像素转换
     */
    private int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }
}
