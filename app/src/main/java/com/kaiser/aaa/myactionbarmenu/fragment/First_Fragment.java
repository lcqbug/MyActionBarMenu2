package com.kaiser.aaa.myactionbarmenu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kaiser.aaa.myactionbarmenu.R;
import com.kaiser.aaa.myactionbarmenu.activity.Content;
import com.kaiser.aaa.myactionbarmenu.adapter.Firstfragment_lv_Adapter;
import com.kaiser.aaa.myactionbarmenu.entity.FirstFragmentBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaa on 15-3-23.
 */
public class First_Fragment extends Fragment implements PullToRefreshBase.OnRefreshListener2<ListView>{
    @ViewInject(R.id.pullToRefresh_firstfm)
    private PullToRefreshListView pullToRefresh_firstfm;

    private ListView mlistView;
    private HttpUtils http;
    private String urlString="http://webapi.yilule.com:5580/api/TourData?pageSize=20&pageIndex=1&lat=116.342894&lng=40.046066&parentId=59&order=6";
    private FirstFragmentBean chapterBead;
    private List<FirstFragmentBean> totailist=new ArrayList<>();
    private Firstfragment_lv_Adapter adapter=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.first_fragment,container,false);
        ViewUtils.inject(this, view);
        mlistView= pullToRefresh_firstfm.getRefreshableView();
        //mlistView.
       //mlistView.addHeaderView();
        adapter=new Firstfragment_lv_Adapter(getActivity(),totailist);
        mlistView.setAdapter(adapter);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), Content.class);
                Bundle bundle=new Bundle();
                bundle.putString("id",totailist.get(i).getId());
                intent.putExtras(bundle);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        http=new HttpUtils();
        eertrd();
        return view;
    }
    // 顶部的viewPager
    //mlistView.addHeaderView();
    private void initViews_mylogin(Context context){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      //  inflater.inflate(R.layout.mylogin, this);
//        iv_icon = (ImageView) findViewById(R.id.imageView_mylogin);
//        button_mylogin= (Button) findViewById(R.id.button_mylogin);
    }

    public void eertrd() {
        http.send(HttpMethod.GET, urlString, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException arg0, String arg1) {
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {

                try {
                    JSONArray jsonArray = new JSONArray(arg0.result);
                    for (int i = 0; i <10; i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        chapterBead = new FirstFragmentBean();
                        chapterBead.getparseJson(jsonobject);
                        totailist.add(chapterBead);
                    }
                    adapter.addAll(totailist);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        eertrd();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }
//item的点击事件。

}
