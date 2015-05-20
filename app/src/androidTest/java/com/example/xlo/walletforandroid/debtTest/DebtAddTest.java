package com.example.xlo.walletforandroid.debtTest;

import com.example.xlo.walletforandroid.R;
import com.example.xlo.walletforandroid.baseTool.MainPageTest;
import com.example.xlo.walletforandroid.baseTool.TestCase;
import com.example.xlo.walletforandroid.settingTest.SettingTest;
import com.example.xlo.walletforandroid.settingTest.TypeAddTest;
import com.example.xlo.walletforandroid.statusTest.StatusTest;

/**
 * Created by LT on 15-5-19.
 *
 */
public class DebtAddTest extends DebtTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        MainPageTest.changeToPage("setting");
        SettingTest.toAdd();
        TypeAddTest.addType("type one");
        MainPageTest.changeToPage("debt");
    }

    public static void setBorrowing(boolean flag) {
        if (flag) {
            solo.clickOnText("borrowing");
        }else{
            solo.clickOnText("loan");
        }
    }

    public static void setMoneyType(int type) {
        solo.pressSpinnerItem(0, type);
    }

    public static void setCreditor(String name) {
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.add_debt_action_creditor));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.add_debt_action_creditor), name);
    }

    public static void setValue(String value) {
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.add_debt_action_value));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.add_debt_action_value), value);
    }

    public static void setDeadline(String deadline) {
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.add_debt_action_deadline));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.add_debt_action_deadline), deadline);
    }

    public static void setRate(String value, int type) {
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.add_debt_action_rate));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.add_debt_action_rate), value);
        solo.pressSpinnerItem(1, type);
    }

    public static void addDebt() {
        submit();
    }

    public static void addDebt(boolean flag, int moneyType, String creditor, String value, String deadline, String rate, int rateType) {
        setBorrowing(flag);
        setMoneyType(moneyType);
        setCreditor(creditor);
        setValue(value);
        setDeadline(deadline);
        setRate(rate,rateType);
        submit();
    }

    public void testBaseFeature() {
        toAdd();
        addDebt(true, 0, "name", "100", "2100-1-1", "0", 0);
        checkBorrowing(0, 0, "name", 100, "2100-01-01");
        MainPageTest.changeToPage("status");
        StatusTest.checkStatusCell(0, "type one", 100);
    }

    public void testNumberSign() {
        toAdd();
        setBorrowing(true);
        setMoneyType(0);
        setCreditor("name");
        setValue("-1");
        setDeadline("2100-1-1");
        setRate("0", 0);
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("The value should be more than zero."));
        MainPageTest.changeToPage("status");
        StatusTest.checkStatusCell(0, "type one", 0);
    }

    public void testWrongNumber(){
        toAdd();
        setBorrowing(true);
        setMoneyType(0);
        setCreditor("name");
        setValue("a");
        setDeadline("2100-1-1");
        setRate("0", 0);
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Please input right."));
        MainPageTest.changeToPage("status");
        StatusTest.checkStatusCell(0, "type one", 0);
    }

    public void testOverExpenditure(){
        toAdd();
        addDebt(true, 0, "name", "100", "2100-1-1", "0", 0);
        toAdd();
        setBorrowing(false);
        setMoneyType(0);
        setCreditor("name");
        setValue("200");
        setDeadline("2100-1-1");
        setRate("0", 0);
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Don't have enough money."));
        MainPageTest.changeToPage("status");
        StatusTest.checkStatusCell(0, "type one", 100);
    }

}
