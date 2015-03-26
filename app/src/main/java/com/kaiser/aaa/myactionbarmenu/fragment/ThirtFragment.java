package com.kaiser.aaa.myactionbarmenu.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kaiser.aaa.myactionbarmenu.R;
import com.kaiser.aaa.myactionbarmenu.activity.LoginActivity;

/**
 * Created by aaa on 15-3-26.
 */
public class ThirtFragment extends Fragment implements View.OnClickListener{
    private ImageView imageView_home_hugh;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thirt_fragment, container, false);
        imageView_home_hugh = ((ImageView) view.findViewById(R.id.userfragment_imageView_home_hugh));
        imageView_home_hugh.setOnClickListener(this);
        Bitmap bitmap_home = BitmapFactory.decodeResource(getResources(), R.drawable.home_hugh);

        //获取图片的宽
        int width = bitmap_home.getWidth();
        //这个方法获得的bitmap，是先将图片加载到cache，然后
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        //获得一个Canvas，绘制在Bitmap上
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        //开启抗锯齿
        paint.setAntiAlias(true);
        //设置画笔的填充物
        paint.setShader(new BitmapShader(bitmap_home, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        //画一个圆形
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);
        //绘制圆环
//        paint.setARGB(255, 138, 43, 226);
//        paint.setStrokeWidth(4);
//        canvas.drawCircle(width / 2+10, width / 2+10, width / 2 + 6 , paint);
        imageView_home_hugh.setImageBitmap(bitmap);
        return view;
    }

    //点击用户图片之后跳转到登录界面

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.userfragment_imageView_home_hugh:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }

    }
}
