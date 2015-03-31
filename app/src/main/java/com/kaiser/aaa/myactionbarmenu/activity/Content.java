package com.kaiser.aaa.myactionbarmenu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.kaiser.aaa.myactionbarmenu.R;
import com.kaiser.aaa.myactionbarmenu.adapter.Content_Info_adapter;
import com.kaiser.aaa.myactionbarmenu.adapter.Content_Viewpager_Adapter;
import com.kaiser.aaa.myactionbarmenu.entity.ContentBean;
import com.kaiser.aaa.myactionbarmenu.entity.Content_Info;
import com.kaiser.aaa.myactionbarmenu.entity.FirstFragmentBean;
import com.kaiser.aaa.myactionbarmenu.entity.GetXmlAndParse;
import com.kaiser.aaa.myactionbarmenu.interfaces.CallBackJSONStr;
import com.kaiser.aaa.myactionbarmenu.Utils.HttpHelper;
import com.kaiser.aaa.myactionbarmenu.Utils.ParserJSONUtils;
import com.kaiser.aaa.myactionbarmenu.Utils.PathHelper;
import com.lidroid.xutils.view.annotation.ContentView;
import com.zxing.activity.CaptureActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

@ContentView(R.layout.activity_content)
public class Content extends ActionBarActivity implements View.OnClickListener{

   private MapView mapView_content;
    private BaiduMap mBaiduMap;
    //内容详细的顶部Viewpager

    private ViewPager viewpager_content;
    private ScrollView scrollView_content;
    private LinearLayout layout_content_top;
    //顶部Viewpager的数据源
    private List<View> list_view=new ArrayList<>();
    private Content_Viewpager_Adapter adapter=null;
    private PoiSearch poiSearch = null;
    private List<ContentBean> list_cBean=new ArrayList<>();
    private String id;
    private float myalph=1;
    private double lat;
    private double lng;
    // 小控件的数据
    private TextView textview_content_title;
    private TextView textview_content_content;
    private ImageView iv_content_center01;
    private ImageView iv_content_center02;
    private ImageView iv_content_center03;
    private ImageView iv_content_center04;
    private TextView tv_content_text01;
    private TextView tv_content_text02;
    private TextView tv_content_text03;
    private TextView tv_content_text04;
    private ImageView imageView_conten_buttom_left;
    private TextView TextView_content_Buttom;
    private ImageView imageView_content_buttom_right;
    private View upView;
    private PopupWindow upWindow;
    private View upView02;
    private PopupWindow upWindow02;
   // private TextView textView_popnemu_photo01;
    private ListView listView_content_popmenu;
    public static final int PARSESUCCWSS=0x2001;
    private Content_Info_adapter adapter_info;
    private List<Content_Info> list_info=new ArrayList<>();
    private View mypopview;
    private String name;
    private String forword;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PARSESUCCWSS://解析完成，拿到数据List<Content_Info>
                    list_info= (List<Content_Info>) msg.obj;
                    adapter_info.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
         // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        checkKey();
         // 装载布局文件
        setContentView(R.layout.activity_content);
        mapView_content= (MapView) findViewById(R.id.mapView_content);
        viewpager_content= (ViewPager) findViewById(R.id.viewpager_content);
        scrollView_content= (ScrollView) findViewById(R.id.scrollView_content);
        layout_content_top= (LinearLayout) findViewById(R.id.layout_content_top);
        mBaiduMap=mapView_content.getMap();
        //开启交通图
        mBaiduMap.setBaiduHeatMapEnabled(true);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mapView_content= (MapView) findViewById(R.id.mapView_content);
        Bundle bundle=getIntent().getExtras();
        FirstFragmentBean firstFragmentBead = (FirstFragmentBean) bundle.getSerializable("FirstFragmentBead");
        id=firstFragmentBead.getId();
      //  id= bundle.getString("id");
        lat = firstFragmentBead.getLat();
        lng = firstFragmentBead.getLng();
        name= firstFragmentBead.getName();
        forword= firstFragmentBead.getForeword();
        myMap();
        //加载viewPager的数据
        initData();
        //初始化中间和底部的小控件数据
        initView();
        //初始化底部菜单点击 弹出来的数据
        initPopWindow();
        initPopWindow02();
        poiSearch = PoiSearch.newInstance();

        poiSearch
                .setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {

                    @Override
                    public void onGetPoiResult(PoiResult result) {
                        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                            mBaiduMap.clear();// 刷新百度地图
                            // 给泡泡添加数据
                            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
                            overlay.setData(result);
                            overlay.addToMap();// 添加到地图上。
                            // 给泡泡监听
                            mBaiduMap.setOnMarkerClickListener(overlay);
                        }
                    }

                    @Override
                    public void onGetPoiDetailResult(PoiDetailResult arg0) {

                    }
                });

        scrollView_content.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    myalph=myalph-0.01f;
                  //  layout_content_top.setAlpha(myalph);
                    //可以监听到ScrollView的滚动事件
//                    int w=viewpager_content.getWidth();
//                    int h=viewpager_content.getHeight();
//                    viewpager_content.setLayoutParams(new ViewGroup.LayoutParams(w,h/3));
                }
                return false;
            }
        });

    }

    public void myMap(){
        //定义Maker坐标点
        LatLng point = new LatLng(lat, lng);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_launcher);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
    //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    public void initView(){
        //顶部
        textview_content_title= (TextView) findViewById(R.id.textview_content_title);
        textview_content_content= (TextView) findViewById(R.id.textview_content_content);
       //设置标题
        textview_content_title.setText(name);
        //设置内容
      //  textview_content_content.setText(forword);

        //中间 加点击监听
        iv_content_center01= (ImageView) findViewById(R.id.iv_content_center01);
        iv_content_center02= (ImageView)  findViewById(R.id.iv_content_center02);
        iv_content_center03= (ImageView)  findViewById(R.id.iv_content_center03);
        iv_content_center04= (ImageView)  findViewById(R.id.iv_content_center04);
        tv_content_text01= (TextView)  findViewById(R.id.tv_content_text01);
        tv_content_text02= (TextView)  findViewById(R.id.tv_content_text02);
        tv_content_text03= (TextView)  findViewById(R.id.tv_content_text03);
        tv_content_text04= (TextView)  findViewById(R.id.tv_content_text04);
        //底部 加点击监听
        imageView_conten_buttom_left= (ImageView) findViewById(R.id.imageView_conten_buttom_left);
        TextView_content_Buttom= (TextView) findViewById(R.id.TextView_content_Buttom);
        imageView_content_buttom_right= (ImageView) findViewById(R.id.imageView_content_buttom_right);
        iv_content_center01.setOnClickListener(this);
        iv_content_center02.setOnClickListener(this);
        iv_content_center03.setOnClickListener(this);
        iv_content_center04.setOnClickListener(this);
        tv_content_text01.setOnClickListener(this);
        tv_content_text02.setOnClickListener(this);
        tv_content_text03.setOnClickListener(this);
        tv_content_text04.setOnClickListener(this);
        imageView_conten_buttom_left.setOnClickListener(this);
        TextView_content_Buttom.setOnClickListener(this);
        imageView_content_buttom_right.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){//一个ImageView和TextView是一个控件事件
            case R.id.iv_content_center01:
            case R.id.tv_content_text01:
                //全景
                Toast.makeText(getApplicationContext(),"全景",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_content_center02:
            case R.id.tv_content_text02:
                // 乐在题中
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(),ExamActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            case R.id.iv_content_center03:
            case R.id.tv_content_text03:
                //查看评论
                Toast.makeText(getApplicationContext(),"查看评论",Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_content_center04:
            case R.id.tv_content_text04:
                //必玩推荐
                Toast.makeText(getApplicationContext(),"必玩推荐",Toast.LENGTH_SHORT).show();
                break;

            // 底部三个菜单
            case R.id.imageView_conten_buttom_left:
                Toast.makeText(getApplicationContext()," 菜单",Toast.LENGTH_SHORT).show();
                break;
            case R.id.TextView_content_Buttom:
                Toast.makeText(getApplicationContext()," popWindow",Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView_content_buttom_right:
                upWindow02.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;

        }
    }
    //底部中间的菜单
    private void initPopWindow() {
        upView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.popmenu, null);
//        upWindow = new PopupWindow(upView, LayoutParams.MATCH_PARENT,
//                LayoutParams.MATCH_PARENT);
         upWindow = new PopupWindow(upView,LayoutParams.MATCH_PARENT,300,true);
        // 需要设置一下此参数，点击外边可消失
        upWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置此参数获得焦点，否则无法点击
        upWindow.setFocusable(true);
        upWindow.setAnimationStyle(R.style.AnimationFade);
        /** 寻找控件 */
        listView_content_popmenu= (ListView) upView.findViewById(R.id.listView_content_popmenu);
       GetXmlAndParse xml=new GetXmlAndParse(handler);
        xml.getXml();//handler加载数据，返回发送一个集合回来。
        adapter_info=new Content_Info_adapter(getApplicationContext(),list_info);
        listView_content_popmenu.setAdapter(adapter_info);
        upView.findViewById(R.id.button01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "You click OK", Toast.LENGTH_SHORT).show();
            }
        });
        upView.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (upWindow != null && upWindow.isShowing()) {
                    upWindow.dismiss();
                    upWindow = null;
                }
            }
        });
    }
    //底部右边的菜单
    private void initPopWindow02() {
        upView02 = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.popmenu02, null);
        upWindow02 = new PopupWindow(upView02, LayoutParams.MATCH_PARENT,
                600,true);
        // 需要设置一下此参数，点击外边可消失
        upWindow02.setBackgroundDrawable(new BitmapDrawable());
        // 设置此参数获得焦点，否则无法点击
        upWindow02.setFocusable(true);
        upWindow02.setAnimationStyle(R.style.AnimationFade);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        upView02.setBackgroundDrawable(dw);
        /** 寻找控件 */
        //textView_popnemu_photo01= (TextView) upView02.findViewById(R.id.textView_popnemu_photo01);
       //相机拍照
        upView02.findViewById(R.id.textView_popnemu_photo01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(),CamrayActivity.class);
                startActivity(intent);
            }
        });
        //二维码
        upView02.findViewById(R.id.textView_popnemu_rich01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCameraIntent = new Intent(getApplicationContext(),CaptureActivity.class);
                startActivity(openCameraIntent);
                //  startActivityForResult(openCameraIntent, 0);
            }
        });
        //评论
        upView02.findViewById(R.id.textView_popnemu_comment01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(),CommentActivity.class);
                startActivity(intent);
            }
        });
        //微博分享
        upView02.findViewById(R.id.textView_popnemu_photo02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
            }
        });
        //空间分享
        upView02.findViewById(R.id.textView_popnemu_rich02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
            }
        });
        //xx分享
        upView02.findViewById(R.id.textView_popnemu_comment02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
            }
        });
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


    class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            // 点击第几个泡泡就返回第几个泡泡的详情。
            PoiInfo info = getPoiResult().getAllPoi().get(index);
            if (info.hasCaterDetails) {// 说明点击开以点开
                // 设置明细
                poiSearch.searchPoiDetail(new PoiDetailSearchOption()
                        .poiUid(info.uid));
            }
            return true;

        }
    }

    public void initData(){ //加载viewPager的数据
        HttpHelper.getJSONStr(PathHelper.contentpath(id),new CallBackJSONStr() {
            @Override
            public void getJSONStr(String jsonStr) {
                List<JSONObject> list_json = ParserJSONUtils.parserContentJson(jsonStr);
                for (int i = 0; i <list_json.size() ; i++) {
                    ContentBean b=new ContentBean();
                    b.getPareseJson(list_json.get(i));
                    //装图片的容器
                    ImageView view=new ImageView(getApplicationContext());
                    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    view.setBackgroundResource(R.drawable.loadingimg);
                    // 加载图片
                    HttpHelper.getBitmapUtils(getApplicationContext()).display(view,b.getNewPath());
                    list_view.add(view);
                }
                adapter.notifyDataSetChanged();
            }
        });
        adapter=new Content_Viewpager_Adapter(list_view);
        viewpager_content.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mapView_content.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView_content.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView_content.onDestroy();
    }



    private void checkKey() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter
                .addAction(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE);
        //动态注册广播。
        registerReceiver(new MyReceiver(), intentFilter);
    }

    // 广播
    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 广播监听 key是否错误。
            if (intent
                    .getAction()
                    .equals(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)) {
                setTitle("秘钥错误，无法正常加载地图");

            }

        }

    }
}
