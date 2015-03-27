package com.kaiser.aaa.myactionbarmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kaiser.aaa.myactionbarmenu.activity.LoginActivity;
import com.kaiser.aaa.myactionbarmenu.activity.SearchActivity;
import com.kaiser.aaa.myactionbarmenu.adapter.ChatperAdapter;
import com.kaiser.aaa.myactionbarmenu.mytopmenu.SlidingTabLayout;
import com.kaiser.aaa.myactionbarmenu.utils.DbHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.zxing.activity.CaptureActivity;
//主页面 黄科泽

@ContentView(R.layout.activity_main)
public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private ResideMenu resideMenu;
    //主框架 顶部实现抽屉式的效果。的对象
   // private MenuActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;
    private ResideMenuItem myLogin;
    //主框架的内部框架 初始化
    @ViewInject(R.id.tile_main)
    private SlidingTabLayout tile_main;
    @ViewInject(R.id.viewpager_main)
    private ViewPager viewpager_main;
    //二维码
    @ViewInject(R.id.image_twocode_main)
    private ImageView image_twocode_main;
    //底部的搜索
    @ViewInject(R.id.image_search_main)
    private ImageView image_search_main;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewUtils.inject(this);

          myTopMenu();
          initView();
        DbHelper.intiUtils(this);

    }
    //主框架的内部框架
    public void initView(){
        viewpager_main.setAdapter(new ChatperAdapter(getSupportFragmentManager()));
        tile_main.setViewPager(viewpager_main);
        image_twocode_main.setOnClickListener(this);
        image_search_main.setOnClickListener(this);


    }


    //主框架 顶部实现抽屉式的效果。
    public void myTopMenu(){
        //-----------------
        resideMenu = new ResideMenu(this);

        resideMenu.setBackground(R.drawable.home_bg);
        resideMenu.attachToActivity(this);
        //设置打开抽屉的监听事件
        resideMenu.setMenuListener(menuListener);
        //禁止菜单向右滑动
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        // 取消背景图片
        resideMenu.setShadowVisible(false);
        //这样子在ignored_view操作的区域就不允许用手势滑动操作菜单.
        resideMenu.addIgnoredView(viewpager_main);

        myLogin     = new ResideMenuItem(this, R.drawable.home_hugh," ");
        itemHome     = new ResideMenuItem(this, R.drawable.home_location);
        itemProfile  = new ResideMenuItem(this, R.drawable.home_me);
        itemCalendar = new ResideMenuItem(this, R.drawable.home_barcode);
        itemSettings = new ResideMenuItem(this, R.drawable.home_search);

        myLogin.setOnClickListener(this);
        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(myLogin, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);


        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
    }
    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
           // Toast.makeText(MainActivity.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
         //   Toast.makeText(MainActivity.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 只需要这句话，让父类不拦截触摸事件就可以了。
        return resideMenu.dispatchTouchEvent(ev);
    }
    @Override
    public void onClick(View view) {
        if (view==myLogin){
            Intent intent=new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
        }else if (view == itemHome){
           // changeFragment(new HomeFragment());
        }else if (view == itemProfile){
            //我的
            viewpager_main.setCurrentItem(2);
        }else if (view == itemCalendar){
            //扫一扫
            Intent openCameraIntent = new Intent(MainActivity.this,CaptureActivity.class);
            startActivity(openCameraIntent);
        }else if (view == itemSettings){
            //跳转到搜索页面
            Intent intent=new Intent();
            intent.setClass(this,SearchActivity.class);
            startActivity(intent);


        }
        switch (view.getId()){
            case R.id.image_twocode_main:
                Intent openCameraIntent = new Intent(MainActivity.this,CaptureActivity.class);
                startActivity(openCameraIntent);
              //  startActivityForResult(openCameraIntent, 0);
                break;
            case R.id.image_search_main:
                //跳转到搜索页面
                Intent intent=new Intent();
                intent.setClass(this,SearchActivity.class);
                startActivity(intent);
                break;

        }
        resideMenu.closeMenu();
    }
    //主框架内部内容框架的改变事件。
    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.main_fragment, targetFragment, "fragment")
//                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toast.makeText(this,scanResult,Toast.LENGTH_SHORT).show();
          //  resultTextView.setText(scanResult);
        }
    }

}
