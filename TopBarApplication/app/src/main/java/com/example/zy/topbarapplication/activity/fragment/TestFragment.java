package com.example.zy.topbarapplication.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zy.topbarapplication.R;

/**
 * Created by zhangyong on 16/2/29.
 */
public class TestFragment extends Fragment {

        private String name;
    public static TestFragment getInstance(String title){
        TestFragment testFragment = new TestFragment();
        Bundle bundle =new Bundle();
        bundle.putString("title", title);
        testFragment.setArguments(bundle);
        return testFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        TextView textView= (TextView) view.findViewById(R.id.text_view);
        textView.setText("当前页标题："+getArguments().getString("title"));
    }
}
