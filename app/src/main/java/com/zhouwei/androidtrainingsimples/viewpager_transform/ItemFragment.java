package com.zhouwei.androidtrainingsimples.viewpager_transform;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhouwei.androidtrainingsimples.R;

/**
 * Created by zhouwei on 17/5/25.
 */

public class ItemFragment extends Fragment {
    private PolygonView mPolygonView;
    public static ItemFragment newInstance(int resId){
        ItemFragment itemFragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("resId",resId);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_muti_layout,null);
        mPolygonView = (PolygonView) view.findViewById(R.id.item_image);
        // 做一个属性动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(mPolygonView,"rotation",0f,10f);
        animator.setDuration(10);
        animator.start();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int resId = getArguments().getInt("resId");
        mPolygonView.setImageResource(resId);// 设置图片
    }
}
