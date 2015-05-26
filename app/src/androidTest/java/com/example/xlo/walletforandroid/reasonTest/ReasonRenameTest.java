package com.example.xlo.walletforandroid.reasonTest;

import com.example.xlo.walletforandroid.R;
import com.example.xlo.walletforandroid.baseTool.TestCase;

/**
 * Created by LT on 2015/3/24.
 *
 */
public class ReasonRenameTest extends ReasonTest {
    public static void setReason(int reason){
        solo.pressSpinnerItem(0,reason);
    }

    public static void setFather(int father){
        solo.pressSpinnerItem(1,father);
    }

    public static void enterNewName(String name){
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.rename_tree_reason_action_new_name));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.rename_tree_reason_action_new_name),name);
    }

    public static void enterNewMin(String min){
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.rename_tree_reason_action_min));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.rename_tree_reason_action_min),min);
    }

    public static void enterNewMax(String max){
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.rename_tree_reason_action_max));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.rename_tree_reason_action_max),max);
    }

    public static void enterNewRank(String rank){
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.rename_tree_reason_action_rank));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.rename_tree_reason_action_rank),rank);
    }

    public static void renameReason(){
        submit();
    }

    public static void renameReason(int reason,int father,String newName,String min,String max,String rank){
        setReason(reason);
        setFather(father);
        enterNewName(newName);
        enterNewMin(min);
        enterNewMax(max);
        enterNewRank(rank);
        renameReason();
    }

    public void testBaseFeature(){
        ReasonTest.toAdd();
        ReasonAddTest.addReason(0,"reason one","0","1","2");
        ReasonTest.checkNormalCell(0,"reason one",0,0);
        ReasonTest.checkTreeCell(0,"root","reason one",2,0,1);

        ReasonTest.toAdd();
        ReasonAddTest.addReason(1,"reason two","0","1","2");
        ReasonTest.checkNormalCell(1,"reason two",0,0);
        ReasonTest.checkTreeCell(1,"reason one","reason two",2,0,1);

        ReasonTest.toChange();
        renameReason(1,0,"reason three","1","2","3");
        ReasonTest.checkNormalCell(1,"reason three",0,0);
        ReasonTest.checkTreeCell(1,"root","reason three",3,1,2);
    }

    public void testNoChange(){
        ReasonTest.toAdd();
        ReasonAddTest.addReason(0,"reason one","0","1","2");
        ReasonTest.checkNormalCell(0,"reason one",0,0);
        ReasonTest.checkTreeCell(0,"root","reason one",2,0,1);

        ReasonTest.toAdd();
        ReasonAddTest.addReason(1,"reason two","0","1","2");
        ReasonTest.checkNormalCell(1,"reason two",0,0);
        ReasonTest.checkTreeCell(1,"reason one","reason two",2,0,1);

        ReasonTest.toChange();
        renameReason(1,0,"reason two","1","2","3");
        ReasonTest.checkNormalCell(1,"reason two",0,0);
        ReasonTest.checkTreeCell(1,"root","reason two",3,1,2);
    }

    public void testNoName(){
        ReasonTest.toAdd();
        ReasonAddTest.addReason(0,"reason one","0","1","2");
        ReasonTest.checkNormalCell(0,"reason one",0,0);
        ReasonTest.checkTreeCell(0,"root","reason one",2,0,1);

        ReasonTest.toChange();
        setReason(0);
        setFather(0);
        enterNewName("");
        enterNewMin("1");
        enterNewMax("2");
        enterNewRank("3");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Please input type name."));
    }

    public void testSameName(){
        ReasonTest.toAdd();
        ReasonAddTest.addReason(0,"reason one","0","1","2");
        ReasonTest.checkNormalCell(0,"reason one",0,0);
        ReasonTest.checkTreeCell(0,"root","reason one",2,0,1);

        ReasonTest.toAdd();
        ReasonAddTest.addReason(1,"reason two","0","1","2");
        ReasonTest.checkNormalCell(1,"reason two",0,0);
        ReasonTest.checkTreeCell(1,"reason one","reason two",2,0,1);

        ReasonTest.toChange();
        setReason(1);
        setFather(0);
        enterNewName("reason one");
        enterNewMin("1");
        enterNewMax("2");
        enterNewRank("3");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("This name have exist."));
    }

    public void testSameFather(){
        ReasonTest.toAdd();
        ReasonAddTest.addReason(0,"reason one","0","1","2");
        ReasonTest.checkNormalCell(0,"reason one",0,0);
        ReasonTest.checkTreeCell(0,"root","reason one",2,0,1);

        ReasonTest.toChange();
        setReason(0);
        setFather(1);
        enterNewName("reason two");
        enterNewMin("1");
        enterNewMax("2");
        enterNewRank("3");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("The father of node shouldn't be itself."));
    }

    public void testWrongNumber(){
        ReasonTest.toAdd();
        ReasonAddTest.addReason(0,"reason one","0","1","2");
        ReasonTest.checkNormalCell(0,"reason one",0,0);
        ReasonTest.checkTreeCell(0,"root","reason one",2,0,1);

        ReasonTest.toChange();
        setReason(0);
        setFather(0);
        enterNewName("reason two");
        enterNewMin("1");
        enterNewMax("a");
        enterNewRank("3");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Please input number."));
    }

    public void testMinMax(){
        ReasonTest.toAdd();
        ReasonAddTest.addReason(0,"reason one","0","1","2");
        ReasonTest.checkNormalCell(0,"reason one",0,0);
        ReasonTest.checkTreeCell(0,"root","reason one",2,0,1);

        ReasonTest.toChange();
        setReason(0);
        setFather(0);
        enterNewName("reason two");
        enterNewMin("3");
        enterNewMax("1");
        enterNewRank("2");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Min expenditure should be lower than the max."));

        ReasonTest.toChange();
        setReason(0);
        setFather(0);
        enterNewName("reason two");
        enterNewMin("-1");
        enterNewMax("2");
        enterNewRank("2");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("The value should be more than zero."));
    }

    public void testLoop(){
        ReasonTest.toAdd();
        ReasonAddTest.addReason(0,"reason one","0","1","2");
        ReasonTest.toAdd();
        ReasonAddTest.addReason(1,"reason two","0","1","2");

        ReasonTest.toChange();
        setReason(0);
        setFather(2);
        enterNewName("reason one");
        enterNewMin("0");
        enterNewMax("1");
        enterNewRank("2");
        solo.clickOnButton("submit");
        assertTrue(solo.waitForText("Reason shouldn't have loop."));
    }
}
