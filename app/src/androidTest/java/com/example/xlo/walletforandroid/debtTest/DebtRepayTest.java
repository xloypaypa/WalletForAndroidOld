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
public class DebtRepayTest extends DebtTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        MainPageTest.changeToPage("setting");
        SettingTest.toAdd();
        TypeAddTest.addType("type one");
        MainPageTest.changeToPage("debt");
        toAdd();
        DebtAddTest.addDebt(true, 0, "name", "100", "2100-1-1", "0", 0);
        toAdd();
        DebtAddTest.addDebt(false, 0, "name", "50", "2100-1-1", "0", 0);
    }

    public static void setType(int type) {
        solo.pressSpinnerItem(0, type);
    }

    public static void setID(int id) {
        solo.pressSpinnerItem(1, id);
    }

    public static void setValue(String value) {
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.repay_debt_action_value));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.repay_debt_action_value), value);
    }

    public static void repayDebt() {
        submit();
    }

    public static void repayDebt(int type, int id, String value) {
        setType(type);
        setID(id);
        setValue(value);
        repayDebt();
    }

    public void testBaseFeature(){
        toRepay();
        repayDebt(0, 0, "50");
        checkBorrowing(0, 0, "name", 50, "2100-01-01");
        toRepay();
        repayDebt(0, 1, "50");
        toRepay();
        repayDebt(0, 0, "50");
        MainPageTest.changeToPage("status");
        StatusTest.checkStatusCell(0, "type one", 0);
    }

    public void testNumberSign() {
        toRepay();
        setType(0);
        setID(0);
        setValue("-1");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("The value should be more than zero."));
        checkBorrowing(0, 0, "name", 100, "2100-01-01");
    }

    public void testWrongNumber(){
        toRepay();
        setType(0);
        setID(0);
        setValue("a");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Please input right."));
        checkBorrowing(0, 0, "name", 100, "2100-01-01");
    }

    public void testOverExpenditure(){
        toRepay();
        setType(0);
        setID(0);
        setValue("100");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Don't have enough money."));
        checkBorrowing(0, 0, "name", 100, "2100-01-01");
    }

    public void testTooManyRepay() {
        toRepay();
        setType(0);
        setID(1);
        setValue("100");
        solo.clickOnButton("submit");
        checkLoan(0, 1, "name", 50, "2100-01-01");
    }

}
