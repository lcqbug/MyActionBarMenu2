package com.kaiser.aaa.myactionbarmenu.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kaiser.aaa.myactionbarmenu.R;

import java.io.IOException;

public class CamrayActivity extends ActionBarActivity implements SurfaceHolder.Callback {

    private Camera camera;
    private SurfaceView surface;
    private Button button;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camray);
        surface = (SurfaceView) findViewById(R.id.surface_camray);
        camera = Camera.open();
        surface.getHolder().addCallback(this);
        button = (Button) findViewById(R.id.button_camray);
        image = (ImageView) findViewById(R.id.image_camray);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 拍照
                camera.takePicture(null, null, new Camera.PictureCallback() {

                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        // 参数：第一个是图片的数据，第二个就是哪个摄像头拍的
                        Bitmap bm = BitmapFactory.decodeByteArray(data, 0,
                                data.length);
                        // 如果想把拍好了的照片存储，就可以把data写到内存里面

                        image.setImageBitmap(bm);
                        image.setRotation(90);
                        // 拍完一张如果还想拍的话
                        camera.startPreview();
                    }
                });

            }
        });

    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            // 设置预览
            camera.setPreviewDisplay(surfaceHolder);
            // 设置旋转的角度,只能是90的倍数
            camera.setDisplayOrientation(90);
            // 开始预览
            camera.startPreview();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
