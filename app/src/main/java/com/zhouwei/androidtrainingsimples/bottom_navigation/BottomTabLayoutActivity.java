package com.zhouwei.androidtrainingsimples.bottom_navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhouwei.androidtrainingsimples.R;

/**
 * Created by zhouwei on 17/4/23.
 */

public class BottomTabLayoutActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private Fragment []mFragmensts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_tab_layout_ac);
        mFragmensts = DataGenerator.getFragments("TabLayout Tab");

        initView();

    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.bottom_tab_layout);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());

               for (int i=0;i<mTabLayout.getTabCount();i++){
                   View view = mTabLayout.getTabAt(i).getCustomView();
                   ImageView icon = (ImageView) view.findViewById(R.id.tab_content_image);
                   TextView text = (TextView) view.findViewById(R.id.tab_content_text);
                   if(i == tab.getPosition()){
                       icon.setImageResource(DataGenerator.mTabResPressed[i]);
                       text.setTextColor(getResources().getColor(android.R.color.black));
                   }else{
                       icon.setImageResource(DataGenerator.mTabRes[i]);
                       text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                   }
               }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

            for(int i=0;i<4;i++){
             mTabLayout.addTab(mTabLayout.newTab().setCustomView(DataGenerator.getTabView(this,i)));
         }

    }

    private void onTabItemSelected(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = mFragmensts[0];
                break;
            case 1:
                fragment = mFragmensts[1];
                break;

            case 2:
                fragment = mFragmensts[2];
                break;
            case 3:
                fragment = mFragmensts[3];
                break;
        }
        if(fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
        }
    }
}
