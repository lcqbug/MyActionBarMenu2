package com.kaiser.aaa.myactionbarmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaiser.aaa.myactionbarmenu.R;
import com.kaiser.aaa.myactionbarmenu.utils.BitmapHelper;
import com.kaiser.aaa.myactionbarmenu.entity.FirstFragmentBean;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaa on 15-3-23.第一个碎片的listview的适配器
 */
public class Firstfragment_lv_Adapter extends BaseAdapter {
    private Context context;
    private List<FirstFragmentBean> list=new ArrayList<>();

    public Firstfragment_lv_Adapter(Context context, List<FirstFragmentBean> list) {
        this.context = context;
      //  this.list = list;
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
            view= LayoutInflater.from(context).inflate(R.layout.first_fragment_item,viewGroup,false);
            mholder=new ViewHolder(view);
            view.setTag(mholder);
        }else {
            mholder= (ViewHolder) view.getTag();
        }
        mholder.text_Foreword.setText(list.get(i).getForeword());
        final String path_pic=list.get(i).getNewPic();
        mholder.text_name.setText(list.get(i).getName());

        BitmapUtils bitmapUtils= BitmapHelper.getUtils(context);

        // 加载网络图片
        bitmapUtils.display(mholder.image, path_pic);



        return view;
    }
    public void addAll(List<FirstFragmentBean> beans) {
        list.addAll(beans);
        notifyDataSetChanged();
    }
    class ViewHolder{
        private View itemView;
        @ViewInject(R.id.imageView_first_item_image)
        private ImageView image;
        @ViewInject(R.id.textView_first_item_Name)
        private TextView text_name;
        @ViewInject(R.id.textView_first_item_Foreword)
        private TextView text_Foreword;
        public ViewHolder(View itemView) {
            this.itemView = itemView;
            ViewUtils.inject(this, itemView);
        }
    }
}
