package com.bignerdranch.android.criminalintent;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch.android.criminalintent.database.CrimeCursorWrapper;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bignerdranch.android.criminalintent.database.CrimeDbSchema.*;

/**
 * Created by sae_antony on 15/08/2017.
 */

public class CrimeLab {
    // s开头的变量，表示：static
    private static CrimeLab sCrimeLab;

    // 创建一个空白数组
//    private List<Crime> mCrimes;

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

//        mCrimes = new ArrayList<>();
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
        // 没有用SQLite前，直接加入array中
//        mCrimes.add(c);

        // 利用ContentValues新增一条数据
        ContentValues values = getContentValues(c);
        // 插入数据到数据库
        // 参数一：插入哪个表
        // 参数二：插入的
        // 参数三：插入的数据
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    // get方法。不用set方法吗？
    public List<Crime> getCrimes() {
        // 没有用数据库前，直接返回array
//        return mCrimes;

        List<Crime> crimes = new ArrayList<>();

        // 拿回所有的数据？
        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return crimes;
    }

    // 根据具体Id返回某个crime对象
    public Crime getCrime(UUID id) {
//        for (Crime crime : mCrimes) {
//            if (crime.getId().equals(id)) {
//                return crime;
//            }
//        }

        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    // 更新(数据库中的)数据（在需要的时候（onPause()）调用）
    // 传入Crime参数，知道要更新哪个
    public void updateCrime(Crime crime) {
        // 拿到crime的uuid字符串
        String uuidString = crime.getId().toString();
        // 通过ContentValues拿到值
        ContentValues values = getContentValues(crime);

        // 利用update方法更新数据
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " =?", new String[] {uuidString});
    }

    // 通过ContentValues实现数据的写入，更新
    private static ContentValues getContentValues(Crime crime) {
        // 创建ContentValues
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());

        return values;
    }

    // 查询、获取数据库的数据
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        // 丢raw value进去CrimeCursorWrapper，就返回一个Crime对象了。
        return new CrimeCursorWrapper(cursor);
    }
}
