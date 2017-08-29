package com.bignerdranch.android.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.bignerdranch.android.criminalintent.Crime;
import java.util.Date;
import java.util.UUID;

import static com.bignerdranch.android.criminalintent.database.CrimeDbSchema.*;

/**
 * Created by sae_antony on 28/08/2017.
 */

// 因为用query(...)方法查询数据库的返回值，是Cursor类型，取用的时候要写很多重复的代码，所以用创建一个CursorWrapper的子类，解决问题。

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);

        return crime;
    }
}
