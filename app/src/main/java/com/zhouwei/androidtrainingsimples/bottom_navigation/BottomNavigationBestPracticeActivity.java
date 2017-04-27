package com.zhouwei.androidtrainingsimples.bottom_navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zhouwei.androidtrainingsimples.R;

/**
 * Created by zhouwei on 17/4/24.
 */

public class BottomNavigationBestPracticeActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.botton_navigation_best_practice_ac);

        findViewById(R.id.tab_Layout_tab).setOnClickListener(this);
        findViewById(R.id.tab_bottom_navigation_view).setOnClickListener(this);
        findViewById(R.id.tab_fragment_tab_host).setOnClickListener(this);
        findViewById(R.id.tab_radio_group).setOnClickListener(this);
        findViewById(R.id.tab_custom_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.tab_Layout_tab:
                intent = new Intent(this,BottomTabLayoutActivity.class);
                break;
            case R.id.tab_bottom_navigation_view:
                intent = new Intent(this,BottomNavigationViewActivity.class);
                break;
            case R.id.tab_fragment_tab_host:
                intent = new Intent(this,FragmentTabHostActivity.class);
                break;
            case R.id.tab_radio_group:
                intent = new Intent(this,RadioGroupTabActivity.class);
                break;
            case R.id.tab_custom_view:
                intent = new Intent(this,CustomTabActivity.class);
                break;
        }
        if(intent!=null){
            startActivity(intent);
        }


    }
}
