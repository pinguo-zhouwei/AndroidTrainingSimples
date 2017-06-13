package com.zhouwei.androidtrainingsimples.viewpager_transform;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhouwei.androidtrainingsimples.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/5/21.
 */

public class VPAdapter extends FragmentPagerAdapter {
    public static final int []RES = new int[]{R.drawable.skin2,R.drawable.skin3,R.drawable.skin4
    ,R.drawable.skin5,R.drawable.skin6,R.drawable.skin7,R.drawable.skin8,R.drawable.skin9,R.drawable.skin10,R.drawable.skin11};

    public static final String TITLE[] = new String[]{"哥特萝莉","小红帽","安妮梦游仙境","舞会公主","冰爽烈焰","安博斯与提妮",
    "科学怪熊的新娘","你见过我的熊猫吗？安妮","甜心宝贝","海克斯科技"};
    private List<Fragment> mFragments = new ArrayList<>();

    public VPAdapter(FragmentManager fm){
        super(fm);
        for(int i=0;i<RES.length;i++){
            mFragments.add(ItemFragment.newInstance(RES[i]));
        }
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }

   /* @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }*/

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position];
    }

    /* @Override
    public Object instantiateItem(ViewGroup container, int position) {
        *//*View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_pager_muti_layout,null);
        PolygonView imageView = (PolygonView) view.findViewById(R.id.item_image);
        imageView.setImageResource(RES[position]);
        container.addView(view);
        return view;*//*
        return mFragments.get(position);
    }*/


}
