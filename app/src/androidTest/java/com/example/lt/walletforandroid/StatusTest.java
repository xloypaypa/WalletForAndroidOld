package com.example.lt.walletforandroid;

import android.widget.TableLayout;

import com.robotium.solo.Solo;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class StatusTest extends TestCase {

    public void test(){
        LoginTest.createUserAndLogin();
        solo.clickOnActionBarHomeButton();
        solo.clickOnText("reason");
        solo.sleep(3000);
        TableLayout tl= (TableLayout) activity.findViewById(R.id.reason_normal_table);
        assertNotNull(tl);
    }
}
