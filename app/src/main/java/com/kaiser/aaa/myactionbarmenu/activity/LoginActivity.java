package com.kaiser.aaa.myactionbarmenu.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kaiser.aaa.myactionbarmenu.R;
import com.kaiser.aaa.myactionbarmenu.Utils.HttpURLConnHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

//登陆界面
//songsong
@ContentView(R.layout.activity_login)
public class LoginActivity extends ActionBarActivity implements Handler.Callback, PlatformActionListener {

    //初始化editText
    @ViewInject(R.id.login_edittext_email)
    private EditText edittext_email;
    @ViewInject(R.id.login_edittext_password)
    private EditText edittext_password;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
    }

    //得到edittext里面的数据，用post方法提交给服务器。返回json数据
    private void login() {
        //获取里面的数据

        if (!TextUtils.isEmpty(edittext_email.getText()) && !TextUtils.isEmpty(edittext_password.getText())) {
            //提交给服务器
            email = edittext_email.getText().toString();
            password = edittext_password.getText().toString();
//             email = "806948209@qq.com";
//             password = "wanggwcs380217";
            //Toast.makeText(this, email+password, Toast.LENGTH_SHORT).show();
            byte[] bytes=null;
            //将@编码
            //email.replace("@", "%40");
            try {
                bytes = HttpURLConnHelper.doPostSubmit("http://webapi.yilule.com:5580/api/User?email=" + URLEncoder.encode(email, "UTF-8") + "&password=" + password, "");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (bytes != null) {
                String result = new String(bytes);
                Log.i("TAG", "--->" + result);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                //将返回的json串解析
            }else {
                Toast.makeText(this, "用户名或者密码输入错误", Toast.LENGTH_SHORT).show();
            }

        } else {
            //如果里面没有数据则吐司
            Log.i("TAG", "--->输入邮箱");
            Toast.makeText(this, "输入邮箱", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickButton(View view) {
        //第一件事就是初始化
        ShareSDK.initSDK(this);
        switch (view.getId()) {
            //点击QQ、微信、新浪分别实现第三方登录
            case R.id.login_imageView_qq:
                //showShare();
                //从QQ登录
                authorize(new QQ(this));
                break;
            case R.id.login_imageView_weixin:
                //authorize(new W);
                break;
            case R.id.login_imageView_sina:
                authorize(new SinaWeibo(this));
                break;
            case R.id.login_button_login:
                login();
                break;
        }
    }


    //分享内容
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    /**
     * @param plat 希望用那个平台登录，这个地方就传递那个平台的对象
     */
    private void authorize(Platform plat) {
        if (plat == null) {
            // 如果没有指定平台，那么现在强制选择新浪微博
            plat = new SinaWeibo(this);
        }

        //判断指定平台是否已经完成授权
        // 没有授权的时候，进行授权操作 最终会执行 showUser() 这个方法
        // 作用就是授权并且获取用户信息
        if (plat.isValid()) {
            String userId = plat.getDb().getUserId();
            if (userId != null) {
                // 用于发送注册/登陆成功的消息
                UIHandler.sendEmptyMessage(1, this);
                // login(plat.getName(), userId, null);

                Log.d("Login", plat.getName());
                Log.d("Login", userId);

                return;
            }
        }
        // 用于检测用户在登录时操作的状态。
        // 当用户授权成功，会进行回调，回调内部会传递一些用户的信息
        plat.setPlatformActionListener(this);
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(true);
        //获取用户资料
        plat.showUser(null);
    }


    /**
     * 如果用户信息取到了，那么会发送消息到这个方法。
     *
     * @param msg
     * @return
     */
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    //////// 授权认证的时候，回调的方法

    /**
     * 授权成功，可以获取用户信息
     *
     * @param platform
     * @param i
     * @param hashMap
     */
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        // 获取哪一个平台的登录授权信息
        String name = platform.getName();

        // 获取特定平台下面的用户数据库
        PlatformDb db = platform.getDb();

        // 授权的用户名称
        String userName = db.getUserName();


        Log.d("Login", name + " -> " + userName);
    }

    /**
     * 授权出错
     *
     * @param platform
     * @param i
     * @param throwable
     */
    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    /**
     * 用户取消了授权
     *
     * @param platform
     * @param i
     */
    @Override
    public void onCancel(Platform platform, int i) {

    }
}

