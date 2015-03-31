package com.kaiser.aaa.myactionbarmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaiser.aaa.myactionbarmenu.R;
import com.kaiser.aaa.myactionbarmenu.entity.Content_Info;

import java.util.List;

/**
 * Created by aaa on 15-3-30.底部中间的菜单弹出的popmenu的listView的适配器
 */
public class Content_Info_adapter extends BaseAdapter {
    private List<Content_Info> list=null;
    private Context context;

    public Content_Info_adapter(Context context, List<Content_Info> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mholder=null;
        if (view==null){
            mholder=new ViewHolder(view);
            view= LayoutInflater.from(context).inflate(R.layout.content_info_item,viewGroup,false);
            view.setTag(mholder);
        }else{
            mholder= (ViewHolder) view.getTag();
        }
        mholder.textView_content_info01.setText(list.get(i).getName());
        return view;
    }
    class ViewHolder{
        private TextView textView_content_info01;
        private TextView textView_content_info02;
        public ViewHolder(View view){
            this.textView_content_info01= (TextView) view.findViewById(R.id.textView_content_info01);
            this.textView_content_info02= (TextView) view.findViewById(R.id.textView_content_info02);
        }
    }
}
