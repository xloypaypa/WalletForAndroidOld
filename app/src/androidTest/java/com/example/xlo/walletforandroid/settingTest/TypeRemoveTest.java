package com.example.xlo.walletforandroid.settingTest;

import com.example.xlo.walletforandroid.baseTool.MainPageTest;
import com.example.xlo.walletforandroid.statusTest.StatusTest;

/**
 * Created by LT on 2015/3/24.
 *
 */
public class TypeRemoveTest extends SettingTest {
    public static void setType(int type){
        solo.pressSpinnerItem(0,type);
    }

    public static void removeType(){
        solo.clickOnButton("submit");
        solo.waitForDialogToClose();
    }

    public static void removeType(int type){
        setType(type);
        removeType();
    }

    public void testBaseFeature(){
        SettingTest.toAdd();
        TypeAddTest.addType("type one");
        SettingTest.toRemove();
        removeType(0);
        MainPageTest.changeToPage("status");
        assertEquals(0, StatusTest.getItemCount());
    }
}
