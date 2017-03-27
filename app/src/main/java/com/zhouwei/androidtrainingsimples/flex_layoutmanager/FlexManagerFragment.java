package com.zhouwei.androidtrainingsimples.flex_layoutmanager;

import android.support.v7.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.zhouwei.androidtrainingsimples.R;
import com.zhouwei.rvadapterlib.base.Cell;
import com.zhouwei.rvadapterlib.fragment.AbsBaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhouwei on 17/3/20.
 */

public class FlexManagerFragment extends AbsBaseFragment<Integer> {
    private static final Integer []images = new Integer[]{
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10,
            R.drawable.image11,
            R.drawable.image12,
            R.drawable.image13
    };
    private static final Integer []CATS = new Integer[]{
            R.drawable.cat_1,
            R.drawable.cat_2,
            R.drawable.cat_3,
            R.drawable.cat_4,
            R.drawable.cat_5,
            R.drawable.cat_6,
            R.drawable.cat_7,
            R.drawable.cat_8,
            R.drawable.cat_9,
            R.drawable.cat_10,
            R.drawable.cat_11,
            R.drawable.cat_12,
            R.drawable.cat_13,
            R.drawable.cat_14,
            R.drawable.cat_15,
            R.drawable.cat_16,
            R.drawable.cat_17,
            R.drawable.cat_18,
            R.drawable.cat_19,
    } ;
    @Override
    public void onRecyclerViewInitialized() {
        mBaseAdapter.setData(getCells(Arrays.asList(CATS)));
    }

    @Override
    public void onPullRefresh() {
      mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected RecyclerView.LayoutManager initLayoutManger() {
        FlexboxLayoutManager manager = new FlexboxLayoutManager();
        //设置主轴排列方式
        manager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setAlignItems(AlignItems.STRETCH);
        return manager;
      //  return super.initLayoutManger();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected List<Cell> getCells(List<Integer> list) {
        List<Cell> cells = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            cells.add(new ImageCell(list.get(i)));
        }
        return cells;
    }
}
