package com.example.lt.walletforandroid;

import android.widget.Spinner;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class ReasonAddTest extends ReasonTest {

    public static void setFather(int index){
        solo.pressSpinnerItem(0, index);
    }

    public static void enterName(String name){
        solo.clearEditText((android.widget.EditText) solo.getView(R.id.add_tree_reason_action_name));
        solo.enterText((android.widget.EditText) solo.getView(R.id.add_tree_reason_action_name),name);
    }

    public static void enterMin(String min){
        solo.clearEditText((android.widget.EditText) solo.getView(R.id.add_tree_reason_action_min));
        solo.enterText((android.widget.EditText) solo.getView(R.id.add_tree_reason_action_min),min);
    }

    public static void enterMax(String max){
        solo.clearEditText((android.widget.EditText) solo.getView(R.id.add_tree_reason_action_max));
        solo.enterText((android.widget.EditText) solo.getView(R.id.add_tree_reason_action_max),max);
    }

    public static void enterRank(String rank){
        solo.clearEditText((android.widget.EditText) solo.getView(R.id.add_tree_reason_action_rank));
        solo.enterText((android.widget.EditText) solo.getView(R.id.add_tree_reason_action_rank),rank);
    }

    public static void addReason(){
        solo.clickOnButton("submit");
        solo.waitForDialogToClose();
    }

    public static void addReason(int father,String name,String min,String max,String rank){
        setFather(father);
        enterName(name);
        enterMin(min);
        enterMax(max);
        enterRank(rank);
        addReason();
    }

    public void testBaseFeature(){
        ReasonTest.toAdd();
        addReason(0,"reason one","0","1","2");
        ReasonTest.checkNormalCell(0,"reason one",0,0);
        ReasonTest.checkTreeCell(0,"root","reason one",2,0,1);
    }

    public void testNoName(){
        ReasonTest.toAdd();
        setFather(0);
        enterName("");
        enterMin("1");
        enterMax("2");
        enterRank("3");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("please input name"));
    }

    public void testBadName(){
        ReasonTest.toAdd();
        setFather(0);
        enterName("[reason one]");
        enterMin("1");
        enterMax("2");
        enterRank("3");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Please don't use '['"));
    }

    public void testRootName(){
        ReasonTest.toAdd();
        setFather(0);
        enterName("root");
        enterMin("1");
        enterMax("2");
        enterRank("3");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("This reason have exist!"));
    }

    public void testExistName(){
        ReasonTest.toAdd();
        addReason(0,"reason one","0","1","2");
        ReasonTest.checkTreeCell(0,"root","reason one",2,0,1);

        ReasonTest.toAdd();
        setFather(0);
        enterName("reason one");
        enterMin("1");
        enterMax("2");
        enterRank("3");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("This reason have exist!"));
    }

    public void testNumberError(){
        ReasonTest.toAdd();
        setFather(0);
        enterName("reason one");
        enterMin("a");
        enterMax("b");
        enterRank("c");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("please input number!"));
    }

    public void testBigRankAndFatherChoose(){
        ReasonTest.toAdd();
        addReason(0,"reason one","0","0","10");
        ReasonTest.checkTreeCell(0,"root","reason one",5,0,0);

        ReasonTest.toAdd();
        addReason(1,"reason two","0","0","-10");
        ReasonTest.checkTreeCell(1,"reason one","reason two",0,0,0);
    }

    public void testMinMax(){
        ReasonTest.toAdd();
        setFather(0);
        enterName("reason one");
        enterMin("-1");
        enterMax("0");
        enterRank("0");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("value should be more than zero."));

        ReasonTest.toAdd();
        setFather(0);
        enterName("reason one");
        enterMin("2");
        enterMax("1");
        enterRank("0");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("min expenditure should be lower than the max."));
    }
}
