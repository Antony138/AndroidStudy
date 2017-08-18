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

    // 点击cell的时候，用于传递数据？
    // putting an extra
    public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crima_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
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
