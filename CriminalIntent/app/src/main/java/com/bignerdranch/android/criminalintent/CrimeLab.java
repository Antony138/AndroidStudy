package com.bignerdranch.android.criminalintent;

import android.app.ListActivity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;

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

    // 用于数据库的上下文？
    private Context mContext;

    // 数据库
    private SQLiteDatabase mDatabase;

    // 创建一个singleton
    // 那么简单就创建一个singleton了？
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        // 获取上下文
        mContext = context.getApplicationContext();

        // 调用getWritableDatabase()方法，会执行如下动作：
        // 1.打开/data/data/com.bignerdranch.android.criminalintent/databases/crimeBase.db路径下的数据库(如果不存在，就会创建)
        // 2.如果是第一次创建的database，会调用onCreate(SQLiteDatabase)方法，然后保存版本号
        // 3.如果不是第一次创建的database，检查版本号。如果CrimeOpenHelper中的版本更高，调用onUpgrade(SQLiteDatabase, int, int)方法
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

        mCrimes = new ArrayList<>();
        // 随机创建100个对象
//        for (int i = 0; i <100; i++) {
//            Crime crime = new Crime();
//            crime.setTitle("Ctrime #" + i);
//            crime.setSolved(i % 2 == 0);
//            mCrimes.add(crime);
//        }
    }

    // 新增一条数据
    public void addCrime(Crime c) {
        mCrimes.add(c);
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
