package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by sae_antony on 15/08/2017.
 */

public class Crime {

    // read-only(只需要getter)
    private UUID mId;

    // read-write(所以需要getter和setter)
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime(UUID id) {
        // Generate unique identifier
        // 生成一个随机的UUID
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
