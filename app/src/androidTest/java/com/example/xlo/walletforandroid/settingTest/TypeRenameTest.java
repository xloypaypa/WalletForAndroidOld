package com.example.xlo.walletforandroid.settingTest;

import com.example.xlo.walletforandroid.R;
import com.example.xlo.walletforandroid.baseTool.MainPageTest;
import com.example.xlo.walletforandroid.baseTool.TestCase;
import com.example.xlo.walletforandroid.statusTest.StatusTest;

/**
 * Created by LT on 2015/3/24.
 *
 */
public class TypeRenameTest extends SettingTest {
    public static void setPastName(int past){
        solo.pressSpinnerItem(0,past);
    }

    public static void enterNewName(String name){
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.rename_type_action_new_name));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.rename_type_action_new_name),name);
    }

    public static void renameType(){
        solo.clickOnButton("submit");
        solo.waitForDialogToClose();
    }

    public static void renameType(int index,String name){
        setPastName(index);
        enterNewName(name);
        renameType();
    }

    public void testBaseFeature(){
        SettingTest.toAdd();
        TypeAddTest.addType("type one");
        SettingTest.toRename();
        renameType(0,"type two");
        MainPageTest.changeToPage("status");
        StatusTest.checkStatusCell(0,"type two",0);
    }

    public void testSameTest(){
        SettingTest.toAdd();
        TypeAddTest.addType("type one");

        SettingTest.toAdd();
        TypeAddTest.addType("type two");

        SettingTest.toRename();
        setPastName(1);
        enterNewName("type one");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("This name have exist."));

        MainPageTest.changeToPage("status");
        StatusTest.checkStatusCell(0,"type one",0);
        StatusTest.checkStatusCell(1,"type two",0);
    }

    public void testNoNmae(){
        SettingTest.toAdd();
        TypeAddTest.addType("type one");
        SettingTest.toRename();
        setPastName(0);
        enterNewName("");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Please input type name."));

        MainPageTest.changeToPage("status");
        StatusTest.checkStatusCell(0,"type one",0);
    }

    public void testBadName(){
        SettingTest.toAdd();
        TypeAddTest.addType("type one");
        SettingTest.toRename();
        setPastName(0);
        enterNewName("type [two]");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Please don't use '['!"));

        MainPageTest.changeToPage("status");
        StatusTest.checkStatusCell(0,"type one",0);
    }

    public void testNoTypeForChange(){
        SettingTest.toRename();
        assertTrue(solo.waitForText("No type for rename!"));
    }
}
