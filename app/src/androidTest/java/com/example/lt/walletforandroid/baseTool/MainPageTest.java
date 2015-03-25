package com.example.lt.walletforandroid.baseTool;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class MainPageTest extends TestCase {
    public static void openChoice(){
        solo.clickOnActionBarHomeButton();
    }

    public static void changeToPage(String page){
        openChoice();
        solo.clickOnText(page);
        solo.sleep(1500);
    }

}
