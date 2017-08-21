package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * Created by sae_antony on 21/08/2017.
 */

public class CrimePagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        // 链接pagerView
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        // 拿到data set
        mCrimes = CrimeLab.get(this).getCrimes();

        // 和RecyclerView一样，ViewPager也需要一个adapter：
        // 创建FragmentStatePagerAdapter，需要用到FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // set adapter
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
    }
}
