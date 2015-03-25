package com.example.lt.walletforandroid.statusTest;

import com.example.lt.walletforandroid.R;

/**
 * Created by LT on 2015/3/24.
 *
 */
public class UseMoneyTest extends StatusTest {
    public static void setMoneyType(int type){
        solo.pressSpinnerItem(0,type);
    }

    public static void setInOut(boolean flag){
        if (flag){
            solo.clickOnText("expenditure");
        }else{
            solo.clickOnText("income");
        }
    }

    public void test(){
        StatusTest.toUse();
        setInOut(false);
        solo.sleep(3000);
    }
}
