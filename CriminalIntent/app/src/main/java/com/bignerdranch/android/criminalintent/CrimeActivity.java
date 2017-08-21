package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

// AppCompatActivity要改为FragmentActivity吗？
public class CrimeActivity extends SingleFragmentActivity {

    // 静态字符串, 用于传递参数时作为key用
    // 一开始直接传递的时候(不用argument)，这个常量是public的
    // 用了之后，就可以改为private了
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crima_id";

    // 实现创建Intent的方法，供其他类调用，以便传递参数
    // 意思就是：你这个activity要什么(Intent)，你自己负责，其他类没有责任帮你实现
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        // 创建新的Intent
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        // Intent包含的参数
        // 如果要传两个以上的参数呢？1.调用多次putExtra()方法实现； 2.传递一个array,然后array里面放入所有参数.
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
//        return new CrimeFragment();

        // 通过getSerializableExtra()拿回参数值(是在创建CrimeFragment的时候put进去的)
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }

    // 将下面这部分代码，解耦到SingleFragmentActivity了
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);
//
//        // 获取FragmentManager对象
//        FragmentManager fm = getSupportFragmentManager();
//
//        // Fragment transactions
//        // 通过ID拿到CrimeFragment，如果有在fragament list中，就会返回。
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//
//        // 如果fragment为null(不在fragment list中)，需要创建。
//        if (fragment == null) {
//            fragment = new CrimeFragment();
//            // 以下这句是创建和commits一个fragment transaction
//            fm.beginTransaction()
//                    .add(R.id.fragment_container, fragment)
//                    .commit();
//        }
//    }
}
