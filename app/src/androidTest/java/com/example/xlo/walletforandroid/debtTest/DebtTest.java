package com.example.xlo.walletforandroid.debtTest;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.xlo.walletforandroid.R;
import com.example.xlo.walletforandroid.baseTool.MainPageTest;
import com.example.xlo.walletforandroid.baseTool.TestCase;
import com.example.xlo.walletforandroid.userTest.LoginTest;

/**
 * Created by LT on 15-5-19.
 *
 */
public class DebtTest extends TestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        LoginTest.createUserAndLogin();
        MainPageTest.changeToPage("debt");
    }

    public static void toAdd() {
        solo.clickOnButton("add debt");
        solo.waitForDialogToOpen();
    }

    public static void toRepay() {
        solo.clickOnButton("repay");
        solo.waitForDialogToOpen();
    }

    public static void checkBorrowing(int row,int id, String creditor, double value, String deadline){
        TableLayout table= (TableLayout) solo.getView(R.id.borrow_debt_table);
        checkTable(row, id, creditor, value, deadline, table);
    }

    public static void checkLoan(int row,int id, String creditor, double value, String deadline){
        TableLayout table= (TableLayout) solo.getView(R.id.loan_debt_table);
        checkTable(row, id, creditor, value, deadline, table);
    }

    private static void checkTable(int row, int id, String creditor, double value, String deadline, TableLayout table) {
        TableRow cell= (TableRow) table.getChildAt(row);
        TextView v1,v2,v3,v4;
        v1= (TextView)cell.findViewById(R.id.debt_id);
        v2= (TextView) cell.findViewById(R.id.debt_creditor);
        v3= (TextView) cell.findViewById(R.id.debt_value);
        v4= (TextView) cell.findViewById(R.id.debt_deadline);
        double val=Double.valueOf(v3.getText().toString());
        assertEquals(id+"", v1.getText().toString());
        assertEquals(creditor, v2.getText().toString());
        assertEquals(val, value, 1e-8);
        assertEquals(deadline, v4.getText().toString());
    }

}
