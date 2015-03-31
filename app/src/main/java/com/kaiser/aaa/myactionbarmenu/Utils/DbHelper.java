package com.kaiser.aaa.myactionbarmenu.Utils;

import android.content.Context;

import com.lidroid.xutils.DbUtils;

/**
 * Created by jash on 15-3-11.
 */
public class DbHelper {
    private static DbUtils utils;
    public static void intiUtils(Context context){
        utils = DbUtils.create(context, "MyHappyTravel.db");
        //debug开关
        utils.configDebug(true);
        //事务开关
        utils.configAllowTransaction(true);
    }

    public static DbUtils getUtils() {
        return utils;
    }
}
