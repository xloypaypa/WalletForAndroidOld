package com.example.lt.walletforandroid;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class TestCase extends ActivityInstrumentationTestCase2<MainActivity> {
    public TestCase(){
        super(MainActivity.class);
    }

    static Activity activity;
    static Solo solo;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity=getActivity();
        solo = new Solo(getInstrumentation(), getActivity());
        getInstrumentation().waitForIdleSync();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        TestTool.cleanFile();
    }
}
