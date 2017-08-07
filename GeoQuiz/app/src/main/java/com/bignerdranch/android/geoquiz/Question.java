package com.bignerdranch.android.geoquiz;

/**
 * Created by sae_antony on 07/08/2017.
 */

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;

    // constructor:构造器？和iOS的set方法有点类似？(iOS的getter和setter方法)
    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
