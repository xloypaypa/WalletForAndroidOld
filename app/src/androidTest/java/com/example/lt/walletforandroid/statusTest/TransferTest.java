package com.example.lt.walletforandroid.statusTest;

import com.example.lt.walletforandroid.R;
import com.example.lt.walletforandroid.baseTool.MainPageTest;
import com.example.lt.walletforandroid.baseTool.TestCase;
import com.example.lt.walletforandroid.reasonTest.ReasonAddTest;
import com.example.lt.walletforandroid.reasonTest.ReasonTest;
import com.example.lt.walletforandroid.settingTest.SettingTest;
import com.example.lt.walletforandroid.settingTest.TypeAddTest;

/**
 * Created by LT on 2015/3/26.
 *
 */
public class TransferTest extends StatusTest {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        MainPageTest.changeToPage("reason");
        ReasonTest.toAdd();
        ReasonAddTest.addReason(0, "reason one", "100", "200", "0");
        MainPageTest.changeToPage("setting");
        SettingTest.toAdd();
        TypeAddTest.addType("type one");
        SettingTest.toAdd();
        TypeAddTest.addType("type two");
        MainPageTest.changeToPage("status");

        StatusTest.toUse();
        UseMoneyTest.useMoney(0,false,"100",0);
    }

    public static void setFromType(int type){
        solo.pressSpinnerItem(0,type);
    }

    public static void setAimType(int type){
        solo.pressSpinnerItem(1,type);
    }

    public static void enterValue(String value){
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.transfer_action_value));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.transfer_action_value), value);
    }

    public static void transfer(){
        submit();
    }

    public static void transfer(int from,int to,String value){
        setFromType(from);
        setAimType(to);
        enterValue(value);
        transfer();
    }

    public void testBaseFeature(){
        toTransfer();
        transfer(0,1,"30");
        StatusTest.checkStatusCell(0,"type one",70);
        StatusTest.checkStatusCell(1,"type two",30);
    }

    public void testSameType(){
        toTransfer();
        setFromType(0);
        setAimType(0);
        enterValue("10");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("two type should not same!"));
        StatusTest.checkStatusCell(0,"type one",100);
        StatusTest.checkStatusCell(1,"type two",0);
    }

    public void testWrongNumber(){
        toTransfer();
        setFromType(0);
        setAimType(1);
        enterValue("30b");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("please input number!"));
        StatusTest.checkStatusCell(0,"type one",100);
        StatusTest.checkStatusCell(1,"type two",0);
    }

    public void testBigValue(){
        toTransfer();
        setFromType(0);
        setAimType(1);
        enterValue("200");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("don't have enough money!"));
        StatusTest.checkStatusCell(0,"type one",100);
        StatusTest.checkStatusCell(1,"type two",0);
    }
}
