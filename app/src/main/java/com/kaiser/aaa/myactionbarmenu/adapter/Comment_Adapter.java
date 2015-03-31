package com.kaiser.aaa.myactionbarmenu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by aaa on 15-3-31.
 */
public class Comment_Adapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> list;

    public Comment_Adapter(Context context, List<Map<String, String>> list) {
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
        return null;
    }
   public class ViewHolder{

   }
}
