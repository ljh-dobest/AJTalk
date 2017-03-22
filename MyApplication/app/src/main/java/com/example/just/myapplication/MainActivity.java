package com.example.just.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.RxGalleryListener;
import cn.finalteam.rxgalleryfinal.ui.base.IMultiImageCheckedListener;
import cn.finalteam.rxgalleryfinal.utils.ModelUtils;

public class MainActivity extends AppCompatActivity {
    private Button mBtnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //手动打开日志。
        ModelUtils.setDebugModel(true);
        mBtnOpen = (Button) findViewById(R.id.btn_open);
        mBtnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxGalleryFinal
                        .with(MainActivity.this)
                        .image()
                        .multiple()
                        .maxSize(8)
                        .imageLoader(ImageLoaderType.PICASSO)
                        .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {

                            @Override
                            protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                                Toast.makeText(getBaseContext(), "已选择" + imageMultipleResultEvent.getResult().size()+ "张图片", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                Toast.makeText(getBaseContext(), "OVER", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .openGallery();
            }
        });
        RxGalleryListener.getInstance().setMultiImageCheckedListener(new IMultiImageCheckedListener() {
            @Override
            public void selectedImg(Object t, boolean isChecked) {
                //这个主要点击或者按到就会触发，所以不建议在这里进行Toast
                      /*  Toast.makeText(getBaseContext(),"->"+isChecked,Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void selectedImgMax(Object t, boolean isChecked, int maxSize) {
                Toast.makeText(getBaseContext(), "你最多只能选择" + maxSize + "张图片", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
