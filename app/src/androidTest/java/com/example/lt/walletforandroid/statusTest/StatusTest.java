package com.example.lt.walletforandroid.statusTest;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lt.walletforandroid.reasonTest.ReasonTest;
import com.example.lt.walletforandroid.settingTest.SettingTest;
import com.example.lt.walletforandroid.settingTest.TypeAddTest;
import com.example.lt.walletforandroid.userTest.LoginTest;
import com.example.lt.walletforandroid.baseTool.MainPageTest;
import com.example.lt.walletforandroid.R;
import com.example.lt.walletforandroid.reasonTest.ReasonAddTest;
import com.example.lt.walletforandroid.baseTool.TestCase;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class StatusTest extends TestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        LoginTest.createUserAndLogin();
        MainPageTest.changeToPage("status");
    }

    public static void toUse(){
        solo.clickOnButton("use money");
        solo.waitForDialogToOpen();
    }

    public static void toTransfer(){
        solo.clickOnButton("transfer money");
        solo.waitForDialogToOpen();
    }

    public static void checkStatusCell(int row,String name,double value){
        TableLayout table= (TableLayout) solo.getView(R.id.status_table);
        TableRow cell= (TableRow) table.getChildAt(row);
        TextView v1,v2;
        v1= (TextView)cell.findViewById(R.id.status_money_type);
        v2= (TextView) cell.findViewById(R.id.status_money_type_value);
        String n=v1.getText().toString();
        double v=Double.valueOf(v2.getText().toString());
        assertEquals(name,n);
        assertEquals(value,v,1e-8);
    }

    public static int getItemCount(){
        TableLayout table= (TableLayout) solo.getView(R.id.status_table);
        return table.getChildCount();
    }
}
