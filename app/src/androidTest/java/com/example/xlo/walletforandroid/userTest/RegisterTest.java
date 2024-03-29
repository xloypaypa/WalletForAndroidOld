package com.example.xlo.walletforandroid.userTest;

import com.example.xlo.walletforandroid.R;
import com.example.xlo.walletforandroid.baseTool.TestCase;

import database.operator.UserPublicData;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class RegisterTest extends TestCase {
    public static void enterUserName(String name){
        solo.clearEditText((android.widget.EditText) solo.getView(R.id.register_action_name));
        solo.enterText((android.widget.EditText) solo.getView(R.id.register_action_name),name);
    }

    public static void enterPassword(String password){
        solo.clearEditText((android.widget.EditText) solo.getView(R.id.register_action_password));
        solo.enterText((android.widget.EditText) solo.getView(R.id.register_action_password),password);
    }

    public static void enterRepeat(String repeat){
        solo.clearEditText((android.widget.EditText) solo.getView(R.id.register_action_repeat));
        solo.enterText((android.widget.EditText) solo.getView(R.id.register_action_repeat),repeat);
    }

    public static void selectUserType(String type){
        if (type.equals("tree")){
            solo.pressSpinnerItem(0,1);
        }else{
            solo.pressSpinnerItem(0,0);
        }
    }

    public static void back(){
        solo.clickOnButton("back");
    }

    public static void register(){
        solo.clickOnButton("register");
    }

    public static void register(String name,String password,String type){
        LoginTest.toRegister();
        enterUserName(name);
        enterPassword(password);
        enterRepeat(password);
        selectUserType(type);
        register();
        while (isShown());
    }

    public static boolean isShown(){
        return solo.searchText("register")&&(!solo.searchText("login"));
    }

    public static void checkUserExist(String name){
        assertTrue("user not register!", UserPublicData.userExist(name));
    }

    public void testBaseFeature(){
        register("name","pass","tree");
        checkUserExist("name");
    }

    public void testSameUser(){
        register("name","pass","tree");
        checkUserExist("name");
        LoginTest.toRegister();
        enterUserName("name");
        enterPassword("pass");
        enterRepeat("pass");
        selectUserType("tree");
        register();
        assertTrue(solo.waitForText("This user have exist."));
    }

    public void testNoNameUser(){
        LoginTest.toRegister();
        enterUserName("");
        enterPassword("pass");
        enterRepeat("pass");
        selectUserType("tree");
        register();
        assertTrue(solo.waitForText("Please input user name."));
    }

    public void testNoPassword(){
        LoginTest.toRegister();
        enterUserName("name");
        enterPassword("");
        enterRepeat("");
        selectUserType("tree");
        register();
        assertTrue(solo.waitForText("Please input password."));
    }

    public void testRepeatWrong(){
        LoginTest.toRegister();
        enterUserName("name");
        enterPassword("pass1");
        enterRepeat("pass2");
        selectUserType("tree");
        register();
        assertTrue(solo.waitForText("Two password not same."));
    }
}
