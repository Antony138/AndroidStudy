package com.bignerdranch.android.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * Created by sae_antony on 28/08/2017.
 */

// 这个类用于初始化数据库

public class CrimeBaseHelper extends SQLiteOpenHelper {
    // 版本号
    private static final int VERSION = 1;
    // 数据库名称
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // "create table"是SQLite语句？
        // 创建数据库
        // 根据CrimeDbSchema类中定义的表格进行创建
        sqLiteDatabase.execSQL("create table" + CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
