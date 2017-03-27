package com.zhouwei.androidtrainingsimples;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhouwei.androidtrainingsimples.flex_layoutmanager.FlexLayoutManagerSimpleActivity;
import com.zhouwei.rvadapterlib.base.RVBaseCell;
import com.zhouwei.rvadapterlib.base.RVBaseViewHolder;

/**
 * Created by zhouwei on 17/3/20.
 */

public class MainItemCell extends RVBaseCell<String> implements View.OnClickListener{
    public MainItemCell(String s) {
        super(s);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new RVBaseViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_item_cell,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder rvBaseViewHolder, int position) {
        Log.e("FK","onBind....");
         rvBaseViewHolder.setText(R.id.item_name,mData);
         View view = rvBaseViewHolder.getView(R.id.item_name);
         view.setTag(position);
         view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        Intent intent = null;
        switch (tag){
            case 0:
               intent = new Intent(v.getContext(),FlexboxLayoutSimpleActivity.class);
                break;
            case 1:
                intent = new Intent(v.getContext(), FlexLayoutManagerSimpleActivity.class);
                break;
        }

        v.getContext().startActivity(intent);
    }
}
