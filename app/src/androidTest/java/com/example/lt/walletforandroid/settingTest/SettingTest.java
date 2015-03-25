package com.example.lt.walletforandroid.settingTest;

import com.example.lt.walletforandroid.baseTool.MainPageTest;
import com.example.lt.walletforandroid.baseTool.TestCase;
import com.example.lt.walletforandroid.userTest.LoginTest;

/**
 * Created by LT on 2015/3/24.
 *
 */
public class SettingTest extends TestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        LoginTest.createUserAndLogin();
        MainPageTest.changeToPage("setting");
    }

    public static void toAdd(){
        solo.clickOnButton("add money type");
        solo.waitForDialogToOpen();
    }

    public static void toRename(){
        solo.clickOnButton("rename money type");
        solo.waitForDialogToOpen();
    }

    public static void toRemove(){
        solo.clickOnButton("remove money type");
        solo.waitForDialogToOpen();
    }
}
