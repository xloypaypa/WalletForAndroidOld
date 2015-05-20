package com.example.xlo.walletforandroid.statusTest;

import com.example.xlo.walletforandroid.R;
import com.example.xlo.walletforandroid.baseTool.MainPageTest;
import com.example.xlo.walletforandroid.baseTool.TestCase;
import com.example.xlo.walletforandroid.reasonTest.ReasonAddTest;
import com.example.xlo.walletforandroid.reasonTest.ReasonTest;
import com.example.xlo.walletforandroid.settingTest.SettingTest;
import com.example.xlo.walletforandroid.settingTest.TypeAddTest;

/**
 * Created by LT on 2015/3/24.
 *
 */
public class UseMoneyTest extends StatusTest {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        MainPageTest.changeToPage("reason");
        ReasonTest.toAdd();
        ReasonAddTest.addReason(0, "reason one", "100", "200", "0");
        MainPageTest.changeToPage("setting");
        SettingTest.toAdd();
        TypeAddTest.addType("type one");
        MainPageTest.changeToPage("status");
    }

    public static void setMoneyType(int type){
        solo.pressSpinnerItem(0, type);
    }

    public static void setInOut(boolean flag){
        if (flag){
            solo.clickOnText("expenditure");
        }else{
            solo.clickOnText("income");
        }
    }

    public static void enterValue(String value){
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.use_action_value));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.use_action_value), value);
    }

    public static void setReason(int reason){
        solo.pressSpinnerItem(1, reason);
    }

    public static void useMoney(){
        submit();
    }

    public static void useMoney(int type,boolean flag,String value,int reason){
        setMoneyType(type);
        setInOut(flag);
        enterValue(value);
        setReason(reason);
        useMoney();
    }

    public void testBaseFeature(){
        StatusTest.toUse();
        useMoney(0, false, "100", 0);
        StatusTest.checkStatusCell(0, "type one", 100);
    }

    public void testNumberSign() {
        StatusTest.toUse();
        setMoneyType(0);
        setInOut(false);
        enterValue("-100");
        setReason(0);
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("The value should be more than zero."));
        StatusTest.checkStatusCell(0,"type one",0);
    }

    public void testWrongNumber(){
        StatusTest.toUse();
        setMoneyType(0);
        setInOut(false);
        enterValue("a");
        setReason(0);
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Please input number."));
        StatusTest.checkStatusCell(0,"type one",0);
    }

    public void testOverExpenditure(){
        StatusTest.toUse();
        useMoney(0,false,"1000",0);
        StatusTest.checkStatusCell(0,"type one",1000);

        StatusTest.toUse();
        setMoneyType(0);
        setInOut(true);
        enterValue("300");
        setReason(0);
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("This reason is over-expenditure."));
        StatusTest.checkStatusCell(0,"type one",1000);
    }

    public void testNoMoney(){
        StatusTest.toUse();
        useMoney(0,false,"50",0);
        StatusTest.checkStatusCell(0,"type one",50);

        StatusTest.toUse();
        setMoneyType(0);
        setInOut(true);
        enterValue("100");
        setReason(0);
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Don't have enough money."));
        StatusTest.checkStatusCell(0,"type one",50);
    }
}
