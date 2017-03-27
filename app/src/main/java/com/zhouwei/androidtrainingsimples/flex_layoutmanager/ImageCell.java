package com.zhouwei.androidtrainingsimples.flex_layoutmanager;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.zhouwei.androidtrainingsimples.R;
import com.zhouwei.rvadapterlib.base.RVBaseCell;
import com.zhouwei.rvadapterlib.base.RVBaseViewHolder;

/**
 * Created by zhouwei on 17/3/20.
 */

public class ImageCell extends RVBaseCell<Integer> {
    public static final int TYPE = 0;
    public ImageCell(Integer integer) {
        super(integer);
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder rvBaseViewHolder, int position) {
      ImageView mImageView =  rvBaseViewHolder.getImageView(R.id.image_src);
       mImageView .setImageResource(mData);

        ViewGroup.LayoutParams lp = mImageView.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp =
                    (FlexboxLayoutManager.LayoutParams) mImageView.getLayoutParams();
            flexboxLp.setFlexGrow(1.0f);
        }
    }
}
