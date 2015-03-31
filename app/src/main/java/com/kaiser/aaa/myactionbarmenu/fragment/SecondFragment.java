package com.kaiser.aaa.myactionbarmenu.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaiser.aaa.myactionbarmenu.R;
import com.kaiser.aaa.myactionbarmenu.Utils.HttpHelper;
import com.kaiser.aaa.myactionbarmenu.Utils.ParserJSONUtils;
import com.kaiser.aaa.myactionbarmenu.Utils.PathHelper;
import com.kaiser.aaa.myactionbarmenu.entity.SecondFragmentBean;
import com.kaiser.aaa.myactionbarmenu.interfaces.CallBackJSONStr;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by aaa on 15-3-26.
 */
public class SecondFragment extends Fragment {
     @ViewInject(R.id.imageView_secondfm_place)
     private ImageView secondfm_place;
    @ViewInject(R.id.imageView_secondfm_play_pause)
    private ImageView left_play_pause;
    @ViewInject(R.id.imageView_second_right)
    private ImageView second_right;
    @ViewInject(R.id.textView_second_person)
    private TextView textView_second_person;





    @Override//在这个生命周期里网络加载数据， 只会在fragment提上activity上时加载数据
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        HttpHelper.getJSONStr(PathHelper.path_second,new CallBackJSONStr() {
            @Override
            public void getJSONStr(String jsonStr) {
                List<JSONObject> list = ParserJSONUtils.parseSecondFragmentJson(jsonStr);
                for (int i = 0; i <1 ; i++) {
                    SecondFragmentBean sfBean=new SecondFragmentBean();
                    sfBean.parserJson(list.get(i));
                    // 加载图片并显示在控件上
                  //  Log.i("info","-------------"+second_right);
                    //HttpHelper.getBitmapUtils(getActivity()).display(secondfm_place, sfBean.getCover());
                   // HttpHelper.getBitmapUtils(getActivity()).display(second_right,sfBean.getEditorPic());


                }
            }
        });
    }

    @Override//这个方法值碰一个view
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second_fragment,container,false);
    }

    @Override//在这个生命周期里初始化控件等数据
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewUtils.inject(this,view);


    }
}
