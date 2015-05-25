package com.example.xlo.walletforandroid.baseTool;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.example.xlo.walletforandroid.MainActivity;
import com.robotium.solo.Solo;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class TestCase extends ActivityInstrumentationTestCase2<MainActivity> {
    public TestCase(){
        super(MainActivity.class);
    }

    protected static Activity activity;
    protected static Solo solo;

    public static void submit(){
        solo.clickOnButton("submit");
        solo.waitForDialogToClose();
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        TestTool.cleanFile();
        activity=getActivity();
        solo = new Solo(getInstrumentation(), getActivity());
        getInstrumentation().waitForIdleSync();
    }

    @Override
    public void tearDown() throws Exception {
        Thread.sleep(3000);
        super.tearDown();
    }
}
