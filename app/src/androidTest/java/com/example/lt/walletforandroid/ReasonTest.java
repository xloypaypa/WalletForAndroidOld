package com.example.lt.walletforandroid;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class ReasonTest extends TestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        LoginTest.createUserAndLogin();
        MainPageTest.changeToPage("reason");
    }

    public static void toAdd(){
        solo.clickOnButton("add");
        solo.waitForDialogToOpen();
    }

    public static void toChange(){
        solo.clickOnButton("rename");
        solo.waitForDialogToOpen();
    }

    public static void toRemove(){
        solo.clickOnButton("remove");
        solo.waitForDialogToOpen();
    }

    public static void checkNormalCell(int row,String name,double income,double expenditure){
        TableLayout table= (TableLayout) solo.getView(R.id.reason_normal_table);
        TableRow cell= (TableRow) table.getChildAt(row);
        TextView v1,v2,v3;
        v1= (TextView)cell.findViewById(R.id.reason_name);
        v2= (TextView) cell.findViewById(R.id.reason_income);
        v3= (TextView) cell.findViewById(R.id.reason_expenditure);
        String n=v1.getText().toString();
        double i=Double.valueOf(v2.getText().toString());
        double e=Double.valueOf(v3.getText().toString());
        assertEquals(name,n);
        assertEquals(i,income,1e-8);
        assertEquals(e,expenditure,1e-8);
    }

    public static void checkTreeCell(int row,String father,String name,int rank,double min,double max){
        TableLayout table= (TableLayout) solo.getView(R.id.reason_tree_table);
        TableRow cell= (TableRow) table.getChildAt(row);
        TextView v1,v2,v3,v4,v5;
        v1= (TextView)cell.findViewById(R.id.reason_father);
        v2= (TextView) cell.findViewById(R.id.reason_name2);
        v3= (TextView) cell.findViewById(R.id.reason_rank);
        v4= (TextView) cell.findViewById(R.id.reason_min);
        v5= (TextView) cell.findViewById(R.id.reason_max);
        String f=v1.getText().toString();
        String n=v2.getText().toString();
        int r=Integer.valueOf(v3.getText().toString());
        double i=Double.valueOf(v4.getText().toString());
        double a=Double.valueOf(v5.getText().toString());
        assertEquals(f,father);
        assertEquals(n,name);
        assertEquals(r,rank);
        assertEquals(i,min,1e-8);
        assertEquals(a,max,1e-8);
    }
}
