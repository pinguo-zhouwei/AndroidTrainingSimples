package com.zhouwei.androidtrainingsimples.glide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhouwei.androidtrainingsimples.R;

/**
 * Created by zhouwei on 17/6/16.
 */

public class GlideSimpleActivity extends AppCompatActivity {
    private ImageView mImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glide_simple_layout);
       initView();
    }

    private void initView(){
        mImageView = (ImageView) findViewById(R.id.glide_image);
        Glide.with(this)
                .load(R.drawable.image1)

                .into(mImageView);

        RequestOptions requestOptions = new RequestOptions();
    }
}
