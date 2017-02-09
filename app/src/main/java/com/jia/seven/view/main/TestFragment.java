package com.jia.seven.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.jia.seven.R;
import com.jia.seven.view.base.BaseFragment;


public class TestFragment extends BaseFragment {

    private TextView mTextview;

    public TestFragment() {
    }

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        Button but = (Button) view.findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ;
            }
        });
        create();
        return view;
    }

    private void create() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d("TestFragment -> onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("TestFragment -> onDestroy: ");
    }
}
