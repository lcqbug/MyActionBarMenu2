package com.kaiser.aaa.myactionbarmenu.Utils;

import android.content.Context;
import android.os.Environment;
import android.widget.ImageView;

import com.kaiser.aaa.myactionbarmenu.R;
import com.kaiser.aaa.myactionbarmenu.interfaces.CallBackJSONStr;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.io.File;

/**
 * HttpUtils
 * 
 * @author
 *
 */
public class HttpHelper {
	static HttpHandler<String> strHandler;
	static HttpUtils httpUtils;
	static BitmapUtils bitmapUtils;

	public static HttpUtils getHttpUtils() {
		if (httpUtils == null) {
			httpUtils = new HttpUtils();
		}
		return httpUtils;
	}

	public static BitmapUtils getBitmapUtils(Context context) {
		if (bitmapUtils == null) {
			//bitmapUtils = new BitmapUtils(context);
            initUtils(context);
		}
		return bitmapUtils;
	}
    public static void initUtils(Context context){
        bitmapUtils = new BitmapUtils(context,
                new File(Environment.getExternalStorageDirectory(), "HappyTravel").getAbsolutePath(),
                0.25f);
        bitmapUtils.configDefaultLoadingImage(R.drawable.loadingimg);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.loadingimg);
        bitmapUtils.configDefaultBitmapMaxSize(100, 100);
        bitmapUtils.configDefaultCacheExpiry(1000 * 60 * 60 * 24);
        bitmapUtils.configDefaultConnectTimeout(5000);
        bitmapUtils.configDefaultReadTimeout(10000);
    }

	/**
	 * 获取json数据并解析
	 * 
	 * @param url
	 * @param //backList
	 *            回调数据的接口
	 */
	public static void getJSONStr(String url, final CallBackJSONStr backJSONStr) {
		httpUtils = getHttpUtils();
		strHandler = httpUtils.send(HttpMethod.GET, url,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String jsonStr = responseInfo.result;
						backJSONStr.getJSONStr(jsonStr);

					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}

	/**
	 * 显示网络图片
	 * 
	 * @param iv
	 * @param pic
	 * @param context
	 */
	public static void displayImg(ImageView iv, String pic, Context context) {
		bitmapUtils = getBitmapUtils(context);
		bitmapUtils.display(iv, pic);
	}

}
