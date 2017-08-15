package com.bignerdranch.android.criminalintent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// AppCompatActivity要改为FragmentActivity吗？
public class CrimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
    }
}
