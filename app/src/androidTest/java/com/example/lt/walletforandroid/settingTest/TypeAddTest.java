package com.example.lt.walletforandroid.settingTest;

import com.example.lt.walletforandroid.R;
import com.example.lt.walletforandroid.baseTool.MainPageTest;
import com.example.lt.walletforandroid.baseTool.TestCase;
import com.example.lt.walletforandroid.statusTest.StatusTest;

/**
 * Created by LT on 2015/3/24.
 *
 */
public class TypeAddTest extends SettingTest {
     public static void enterTypeNmae(String name){
         TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.add_type_action_new_name));
         TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.add_type_action_new_name),name);
     }

    public static void addType(){
        TestCase.solo.clickOnButton("submit");
        TestCase.solo.waitForDialogToClose();
    }

    public static void addType(String name){
        enterTypeNmae(name);
        addType();
    }

    public void testBaseFeature(){
        SettingTest.toAdd();
        addType("type one");
        MainPageTest.changeToPage("status");
        StatusTest.checkStatusCell(0,"type one",0);
    }

    public void testBadName(){
        SettingTest.toAdd();
        enterTypeNmae("type [one]");
        TestCase.solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Please don't use '['!"));
    }

    public void testNoName(){
        SettingTest.toAdd();
        enterTypeNmae("");
        TestCase.solo.clickOnButton("submit");
        assertTrue(solo.waitForText("please input type!"));
    }

    public void testSameName(){
        SettingTest.toAdd();
        addType("type one");
        MainPageTest.changeToPage("status");
        StatusTest.checkStatusCell(0,"type one",0);

        MainPageTest.changeToPage("setting");
        SettingTest.toAdd();
        enterTypeNmae("type one");
        TestCase.solo.clickOnButton("submit");
        assertTrue(solo.waitForText("This type have exist!"));
    }
}
