package com.udacity.capstone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import icepick.Icepick;

/**
 * Created by christosdemetriou on 27/04/2018.
 */

public class BaseActivity extends AppCompatActivity {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}
