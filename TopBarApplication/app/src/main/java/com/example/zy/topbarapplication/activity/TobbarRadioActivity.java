package com.example.zy.topbarapplication.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.zy.topbarapplication.R;
import com.example.zy.topbarapplication.activity.fragment.TestFragment;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.BaseActivity;

public class TobbarRadioActivity extends BaseActivity {

    private LinearLayout addLayout;
    private List<String> titleList;
    private RadioGroup myRadioGroup;
    private HorizontalScrollView mHorizontalScrollView;
    private ViewPager mViewPager;
    private List<Fragment> fragments;
    private int _id = 1000;
    private int lastValue = -1;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private View bottomLine;
    private int currentSelect=0;
    private LinearLayout.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topbarradio);
        //获取容器R.id.lay
        initView();
        //获取title列表
        getTitleInfo();
        //创建top
        initGroup();

        iniListener();

//        lp = (LinearLayout.LayoutParams) bottomLine.getLayoutParams();
    }

    private void initView() {
        addLayout = (LinearLayout) findViewById(R.id.lay);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        bottomLine = findViewById(R.id.bottom_line);
    }

    private void getTitleInfo() {
        titleList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            titleList.add("牛牛" + i);
        }
        titleList.add("我老婆么么哒");
    }

    private void initGroup() {

        myRadioGroup = new RadioGroup(this);
        myRadioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);

        //如果这个需要多次更改上面的滚动栏，先青空之前的
        addLayout.removeAllViews();
        //将我们创建的radiogroup放入到addLayout
        addLayout.addView(myRadioGroup);

        //循环创建radiobutton
        for (int i = 0; i < titleList.size(); i++) {
            RadioButton radioButton = new RadioButton(this);

            //color也可以selector设置
            radioButton.setTextColor(getResources().getColorStateList(R.color.community_top_title_color));
            radioButton.setBackgroundResource(R.drawable.community_top_title_bg_color);
            //默认设置0为选中项
            if (i == 0) {
                radioButton.setChecked(true);
            }

            radioButton.setButtonDrawable(android.R.color.transparent);
            RadioGroup.LayoutParams l = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            l.leftMargin=AutoUtils.getPercentWidthSize(20);
            l.rightMargin=AutoUtils.getPercentWidthSize(20);
            radioButton.setLayoutParams(l);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, AutoUtils.getPercentWidthSize(60));

            radioButton.setPadding(AutoUtils.getPercentWidthSize(33), AutoUtils.getPercentWidthSize(20), AutoUtils.getPercentWidthSize(30), AutoUtils.getPercentWidthSize(30));
            radioButton.setId(_id + i);
            radioButton.setText(titleList.get(i));

            myRadioGroup.addView(radioButton);

        }


        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Map<String, Object> map = (Map<String, Object>) group.getChildAt(checkedId).getTag();
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) TobbarRadioActivity.this.findViewById(radioButtonId);
                Map<String, Object> selectMap = (Map<String, Object>) rb.getTag();
                AnimationSet animationSet = new AnimationSet(true);
                TranslateAnimation translateAnimation;
                translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillBefore(true);
                animationSet.setFillAfter(true);
                animationSet.setDuration(300);

//                mImageView.startAnimation(animationSet);//开始上面蓝色横条图片的动画切换
                mViewPager.setCurrentItem(radioButtonId - _id);//让下方ViewPager跟随上面的HorizontalScrollView切换
                mCurrentCheckedRadioLeft = rb.getLeft();//更新当前蓝色横条距离左边的距离
                mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft-100, 0);
                Toast.makeText(TobbarRadioActivity.this,"选中的左边距:"+mCurrentCheckedRadioLeft,Toast.LENGTH_SHORT).show();
//                mImageView.setLayoutParams(new LinearLayout.LayoutParams(rb.getRight() - rb.getLeft(), 4));

            }
        });

        iniVariable();
    }

    private void iniVariable() {
        fragments = new ArrayList<Fragment>();
        for (int i = 0; i < titleList.size(); i++) {
            fragments.add(TestFragment.getInstance(titleList.get(i)));
        }
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myPagerAdapter);//设置ViewPager的适配器
    }

    private void iniListener() {
        // TODO Auto-generated method stub
        mViewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener());
    }

    /**
     * ViewPager的适配器
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }




    }

    /**
     * ViewPager的PageChangeListener(页面改变的监听器)
     */
    private class MyPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {


        }

        @Override
        public void onPageScrolled(int position, float offset, int offsetPixels) {
            lp = (LinearLayout.LayoutParams) bottomLine.getLayoutParams();

            if (currentSelect==position&&offset==0){
                lp.leftMargin=myRadioGroup.getChildAt(position).getLeft();
                lp.width=myRadioGroup.getChildAt(position).getWidth();
            }else
            if (position==currentSelect){
                //当前页向左滑动，右面漏出部分
                //off为屏幕滑动百分比 向左滑动offset 0->1 让下面的红线动起来，通过leftMargin
                lp.leftMargin= (int) (myRadioGroup.getChildAt(currentSelect).getLeft()+(myRadioGroup.getChildAt(currentSelect+1).getLeft()-myRadioGroup.getChildAt(currentSelect).getLeft())*offset);
                lp.width= (int) (myRadioGroup.getChildAt(currentSelect).getWidth()+(myRadioGroup.getChildAt(currentSelect+1).getWidth()-myRadioGroup.getChildAt(currentSelect).getWidth())*offset);
            }else if (position<currentSelect){
                //当前页向右滑动，左面漏出部分
                //off为屏幕滑动百分比 向左滑动offset 1->0
                lp.leftMargin= (int) (myRadioGroup.getChildAt(currentSelect).getLeft()+(myRadioGroup.getChildAt(currentSelect-1).getLeft()-myRadioGroup.getChildAt(currentSelect).getLeft())*(1-offset));
                lp.width= (int) (myRadioGroup.getChildAt(currentSelect).getWidth()+(myRadioGroup.getChildAt(currentSelect-1).getWidth()-myRadioGroup.getChildAt(currentSelect).getWidth())*(1-offset));
            }
//            Log.i("php","当前选择页："+currentSelect+"现在的position："+position+"滑动的百分比是:"+offset);

            bottomLine.setLayoutParams(lp);
            Log.i("php",bottomLine.getWidth()+"");
        }

        /**
         * 滑动ViewPager的时候,让上方的HorizontalScrollView自动切换
         */
        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) findViewById(_id + position);
            radioButton.performClick();
            currentSelect=position;
        }
    }

}


