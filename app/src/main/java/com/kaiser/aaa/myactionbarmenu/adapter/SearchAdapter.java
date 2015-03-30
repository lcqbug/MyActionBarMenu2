package com.kaiser.aaa.myactionbarmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaiser.aaa.myactionbarmenu.R;
import com.kaiser.aaa.myactionbarmenu.entity.SearchNode;

import java.util.List;

/**
 * Created by songsong-PC on 2015/3/30.
 */
public class SearchAdapter extends BaseAdapter {
    private Context context;
    private List<SearchNode> list;

    public SearchAdapter(Context context, List<SearchNode> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mholder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_searchactivity, parent, false);
            mholder = new ViewHolder(convertView);
            convertView.setTag(mholder);
        } else {
            mholder = (ViewHolder) convertView.getTag();
        }
        mholder.name.setText(list.get(position).getName());
        mholder.parentName.setText(list.get(position).getParentName());
        return convertView;
    }

    class ViewHolder {
        private View view;
        private TextView name;
        private TextView parentName;

        ViewHolder(View view) {
            this.view = view;
            name = ((TextView) view.findViewById(R.id.textView_item_searchactivity_Name));
            parentName = ((TextView) view.findViewById(R.id.textView_item_searchactivity_ParentName));
        }
    }
}
