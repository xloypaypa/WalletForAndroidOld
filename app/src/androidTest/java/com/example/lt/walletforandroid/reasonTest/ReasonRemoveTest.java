package com.example.lt.walletforandroid.reasonTest;

/**
 * Created by LT on 2015/3/24.
 *
 */
public class ReasonRemoveTest extends ReasonTest {
    public static void setReason(int reason){
        solo.pressSpinnerItem(0,reason);
    }

    public static void removeReason(){
        submit();
    }

    public static void removeReason(int reason){
        setReason(reason);
        removeReason();
    }

    public void testBaseFeature(){
        ReasonTest.toAdd();
        ReasonAddTest.addReason(0, "reason one", "0", "1", "2");
        ReasonTest.checkNormalCell(0,"reason one",0,0);
        ReasonTest.checkTreeCell(0,"root","reason one",2,0,1);
        ReasonTest.toRemove();
        removeReason(0);
        assertEquals(0, ReasonTest.getReasonCount());
    }
}
