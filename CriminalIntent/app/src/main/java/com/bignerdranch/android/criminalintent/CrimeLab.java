package com.bignerdranch.android.criminalintent;

import android.app.ListActivity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by sae_antony on 15/08/2017.
 */

public class CrimeLab {
    // s开头的变量，表示：static
    private static CrimeLab sCrimeLab;

    // 创建一个空白数组
    private List<Crime> mCrimes;

    // 创建一个singleton
    // 那么简单就创建一个singleton了？
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        // 随机创建100个对象
        for (int i = 0; i <100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Ctrime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    // get方法。不用set方法吗？
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    // 根据具体Id返回某个crime对象
    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }

        return null;
    }
}
