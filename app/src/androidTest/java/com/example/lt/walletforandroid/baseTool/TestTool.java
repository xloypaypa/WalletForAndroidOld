package com.example.lt.walletforandroid.baseTool;

import android.os.Environment;

import java.io.File;

import database.HHD;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class TestTool {
    public static void cleanFile(){
        HHD.deleteFolder(getSDPath() + "/Wallet/");
    }

    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if   (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }

        return sdDir.toString();
    }
}
