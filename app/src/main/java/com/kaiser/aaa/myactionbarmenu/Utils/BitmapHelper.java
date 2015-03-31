package com.kaiser.aaa.myactionbarmenu.Utils;

import android.content.Context;
import android.os.Environment;

import com.kaiser.aaa.myactionbarmenu.R;
import com.lidroid.xutils.BitmapUtils;

import java.io.File;

public class BitmapHelper {
	private static BitmapUtils utils;
	public static void initUtils(Context context){
		utils = new BitmapUtils(context, 
				new File(Environment.getExternalStorageDirectory(), "HappyTravel").getAbsolutePath(),
				0.25f);
		utils.configDefaultLoadingImage(R.drawable.loadingimg);
		utils.configDefaultLoadFailedImage(R.drawable.loadingimg);
		utils.configDefaultBitmapMaxSize(100, 100);
		utils.configDefaultCacheExpiry(1000 * 60 * 60 * 24);
		utils.configDefaultConnectTimeout(5000);
		utils.configDefaultReadTimeout(10000);
	}
	public static BitmapUtils getUtils(Context context) {
        if (utils==null){
            initUtils(context);
           // utils=new BitmapUtils(context);
        }
		return utils;
	}
	
}
