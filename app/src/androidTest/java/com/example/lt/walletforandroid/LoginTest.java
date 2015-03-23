package com.example.lt.walletforandroid;

import android.support.v4.view.ViewPager;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class LoginTest extends TestCase {

    public static void enterUserName(String name){
        solo.clearEditText((android.widget.EditText) solo.getView(R.id.login_action_name));
        solo.enterText((android.widget.EditText) solo.getView(R.id.login_action_name),name);
    }

    public static void enterPassword(String password){
        solo.clearEditText((android.widget.EditText) solo.getView(R.id.login_action_password));
        solo.enterText((android.widget.EditText) solo.getView(R.id.login_action_password),password);
    }

    public static void toRegister(){
        solo.clickOnButton("register");
    }

    public static void login(){
        solo.clickOnButton("login");
    }

    public static boolean isShown(){
        return solo.searchText("log in");
    }

    public static void login(String name,String password){
        enterUserName(name);
        enterPassword(password);
        login();
        solo.waitForDialogToClose();
    }

    public static void createUserAndLogin(){
        RegisterTest.register("name","pass","tree");
        login("name","pass");
    }

    public void testBaseFeature(){
        assertTrue("Could not find the dialog!", isShown());

        RegisterTest.register("name","pass","tree");
        login("name","pass");

        assertFalse("the dialog not close!", isShown());

        ViewPager pager= (ViewPager) activity.findViewById(R.id.status_page);
        assertNotNull(pager);
    }

    public void testWrong(){
        RegisterTest.register("name","pass","tree");
        enterUserName("name");
        enterPassword("pass2");
        login();
        assertTrue(solo.waitForText("user name or password wrong!"));
        login("name","pass");
        assertFalse("the dialog not close!", isShown());
    }
}
