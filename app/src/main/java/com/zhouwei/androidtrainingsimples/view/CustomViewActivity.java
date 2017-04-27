package com.zhouwei.androidtrainingsimples.view;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;

import com.zhouwei.androidtrainingsimples.R;

import java.util.Calendar;

/**
 * Created by zhouwei on 17/4/11.
 */

public class CustomViewActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_layout);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.custom_view_toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.inflateMenu(R.menu.setting_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                 // todo
                //showTimeDilog();
                showDialog();
                return true;
            }
        });
    }

    private void showTimeDilog(){
        Calendar calendar = Calendar.getInstance();
        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.Theme_AppCompat_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
        timePickerDialog.setTitle("选择时间");
        timePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                timePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText("确定");
                timePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText("取消");
            }
        });
        timePickerDialog.show();
    }

    private void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("这是一个对话框!")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create();
        alertDialog.show();


    }
}
