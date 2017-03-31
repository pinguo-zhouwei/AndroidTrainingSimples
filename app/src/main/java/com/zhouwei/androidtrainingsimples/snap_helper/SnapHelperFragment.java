package com.zhouwei.androidtrainingsimples.snap_helper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhouwei.androidtrainingsimples.R;
import com.zhouwei.rvadapterlib.base.Cell;
import com.zhouwei.rvadapterlib.fragment.AbsBaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  snap
 * Created by zhouwei on 17/3/29.
 */

public class SnapHelperFragment extends AbsBaseFragment<Integer> {
    public static final Integer []RES_ID = new Integer[]{
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
    @Override
    public void onRecyclerViewInitialized() {
        mBaseAdapter.setData(getCells(Arrays.asList(RES_ID)));

        StartSnapHelper snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onPullRefresh() {
      mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    protected RecyclerView.LayoutManager initLayoutManger() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        return manager;
    }

    @Override
    public void onLoadMore() {
       hideLoadMore();
    }

    @Override
    protected List<Cell> getCells(List<Integer> list) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0;i<list.size();i++){
            cells.add(new SnapItemCell(list.get(i)));
        }
        return cells;
    }
}
