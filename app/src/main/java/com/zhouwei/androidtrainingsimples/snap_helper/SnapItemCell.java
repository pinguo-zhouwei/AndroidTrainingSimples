package com.zhouwei.androidtrainingsimples.snap_helper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhouwei.androidtrainingsimples.R;
import com.zhouwei.androidtrainingsimples.utils.DisplayUtils;
import com.zhouwei.rvadapterlib.base.RVBaseCell;
import com.zhouwei.rvadapterlib.base.RVBaseViewHolder;

/**
 * Created by zhouwei on 17/3/29.
 */

public class SnapItemCell extends RVBaseCell<Integer> {
    public SnapItemCell(Integer integer) {
        super(integer);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RVBaseViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.snap_item_layout,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder rvBaseViewHolder, final int i) {
        rvBaseViewHolder.getImageView(R.id.snap_image).setImageResource(mData);
        View view = rvBaseViewHolder.getView(R.id.image_card_view);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = DisplayUtils.getScreenWidth(rvBaseViewHolder.getItemView().getContext())- DisplayUtils.dpToPx(rvBaseViewHolder.getItemView().getContext(),20) * 2 ;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SnapItemCell5","Click position:"+i);
            }
        });
    }
}
