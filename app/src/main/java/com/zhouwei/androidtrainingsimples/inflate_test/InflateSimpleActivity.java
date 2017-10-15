package com.zhouwei.androidtrainingsimples.inflate_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhouwei.androidtrainingsimples.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/7/24.
 */

public class InflateSimpleActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inflate_test_layout);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_inflate_test);
        RVAdapter adapter = new RVAdapter();
        adapter.setData(mockData());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private List<String> mockData(){
        List<String> datas = new ArrayList<>();
        for(int i=0;i<100;i++){
            datas.add("这是第"+i+ "个item ");
        }

        return datas;
    }


    public static class RVAdapter extends RecyclerView.Adapter{
        private List<String> mData;

        public void setData(List<String> data) {
            mData = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new InflateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_test_item,parent,false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
             InflateViewHolder viewHolder = (InflateViewHolder) holder;
             ((InflateViewHolder) holder).mTextView.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0:mData.size();
        }

        public static class InflateViewHolder extends RecyclerView.ViewHolder{
            private TextView mTextView;
            public InflateViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.text_item);
            }
        }
    }
}
