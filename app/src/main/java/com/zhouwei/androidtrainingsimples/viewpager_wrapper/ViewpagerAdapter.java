package com.zhouwei.androidtrainingsimples.viewpager_wrapper;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/3/28.
 */

public class ViewpagerAdapter extends PagerAdapter {
    List<View> mViews = new ArrayList<>();

    public void setViews(List<View> views) {
        mViews = views;
    }

    @Override
    public int getCount() {
        if (mViews != null) {
            return mViews.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

}
