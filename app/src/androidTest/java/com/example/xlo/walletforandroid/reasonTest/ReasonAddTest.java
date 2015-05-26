package com.example.xlo.walletforandroid.reasonTest;

import com.example.xlo.walletforandroid.R;
import com.example.xlo.walletforandroid.baseTool.TestCase;

import junit.framework.Assert;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class ReasonAddTest extends ReasonTest {

    public static void setFather(int index){
        TestCase.solo.pressSpinnerItem(0, index);
    }

    public static void enterName(String name){
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.add_tree_reason_action_name));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.add_tree_reason_action_name),name);
    }

    public static void enterMin(String min){
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.add_tree_reason_action_min));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.add_tree_reason_action_min),min);
    }

    public static void enterMax(String max){
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.add_tree_reason_action_max));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.add_tree_reason_action_max),max);
    }

    public static void enterRank(String rank){
        TestCase.solo.clearEditText((android.widget.EditText) TestCase.solo.getView(R.id.add_tree_reason_action_rank));
        TestCase.solo.enterText((android.widget.EditText) TestCase.solo.getView(R.id.add_tree_reason_action_rank),rank);
    }

    public static void addReason(){
        submit();
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
        TestCase.solo.clickOnButton("submit");
        Assert.assertTrue(TestCase.solo.waitForText("Please input type name."));
    }

    public void testRootName(){
        ReasonTest.toAdd();
        setFather(0);
        enterName("root");
        enterMin("1");
        enterMax("2");
        enterRank("3");
        TestCase.solo.clickOnButton("submit");
        Assert.assertTrue(TestCase.solo.waitForText("This name have exist."));
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
        TestCase.solo.clickOnButton("submit");
        Assert.assertTrue(TestCase.solo.waitForText("This name have exist."));
    }

    public void testNumberError(){
        ReasonTest.toAdd();
        setFather(0);
        enterName("reason one");
        enterMin("a");
        enterMax("b");
        enterRank("c");
        TestCase.solo.clickOnButton("submit");
        Assert.assertTrue(TestCase.solo.waitForText("Please input number."));
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
        TestCase.solo.clickOnButton("submit");
        Assert.assertTrue(TestCase.solo.waitForText("The value should be more than zero."));

        ReasonTest.toAdd();
        setFather(0);
        enterName("reason one");
        enterMin("2");
        enterMax("1");
        enterRank("0");
        TestCase.solo.clickOnButton("submit");
        Assert.assertTrue(TestCase.solo.waitForText("Min expenditure should be lower than the max."));
    }
}
