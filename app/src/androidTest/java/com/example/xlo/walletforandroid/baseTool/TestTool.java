package com.example.xlo.walletforandroid.baseTool;

import android.os.Environment;

import java.io.File;

import database.HHD;

/**
 * Created by LT on 2015/3/23.
 */
public class TestTool {

    public static void createFile() {
        HHD.createFolder(getSDPath(), "Wallet");
        HHD.createFile(getSDPath() + "/wallet/", "wallet root file.txt");
        HHD.writeFile(getSDPath() + "/wallet/wallet root file.txt", getSDPath() + "/wallet/");
    }

    public static void cleanFile() {
        HHD.cleanFile(getSDPath() + "/Wallet/all user.txt");
        HHD.cleanFile(getSDPath() + "/Wallet/name/debt.txt");
        HHD.cleanFile(getSDPath() + "/Wallet/name/money.txt");
        HHD.cleanFile(getSDPath() + "/Wallet/name/detail.txt");
        HHD.cleanFile(getSDPath() + "/Wallet/name/reason.txt");
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }

        return sdDir.toString();
    }
}
