package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by sae_antony on 15/08/2017.
 */

// extends Fragment是表示：CrimeFragment是Fragment的子类吗？
// 有两个Fragment的，要选择(android.support.v4.app)这个
public class CrimeFragment extends Fragment {
    private Crime mCrime;

    // 区别：在activity中，onCreate()方法是protected；在这里，是public，因为要被其他activity调用，所以是public。
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 用inflate()方法连接代码和xml中的控件？
        // 第一个参数：resource ID
        // 第二个参数：该视图的父类
        // 第三个参数：因为在activity中以代码方式将fragment和activity联系起来，所以这里填false
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        return v;
    }
}
