package com.example.zy.topbarapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zy.topbarapplication.R;

import base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvFirst, tvSecond, tvThird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
        setListener();
    }


    private void intiView() {
        tvFirst = (TextView) findViewById(R.id.tv_first);
        tvSecond = (TextView) findViewById(R.id.tv_second);
        tvThird = (TextView) findViewById(R.id.tv_third);
    }

    private void setListener() {
        tvFirst.setOnClickListener(this);
        tvSecond.setOnClickListener(this);
        tvThird.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()) {
            case R.id.tv_first:
                 intent=new Intent(MainActivity.this,TobbarRadioActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_second:

                break;
            case R.id.tv_third:
                break;
        }
    }
}
