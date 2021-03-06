package com.bignerdranch.android.criminalintent.database;

/**
 * Created by sae_antony on 28/08/2017.
 */

// 这个类(xxxSchema)定义了一个数据库中的表和字段

public class CrimeDbSchema {

    // 数据库中的一个表
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        // 列columns
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect"; // 这个是为了应用间跳转而存在的
        }
    }
}
