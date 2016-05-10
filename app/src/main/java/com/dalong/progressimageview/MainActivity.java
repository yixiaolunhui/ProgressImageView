package com.dalong.progressimageview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dalong.imageview.ProgressImageView;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private ProgressImageView processImageView ;
    private final int SUCCESS=0;
    private int progress=0;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        processImageView=(ProgressImageView) findViewById(R.id.image);
        mRadioGroup=(RadioGroup) findViewById(R.id.id_group);
        mRadioGroup.setOnCheckedChangeListener(this);
    }


    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    Toast.makeText(MainActivity.this, "图片上传完成", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.id_left:
                processImageView.setType(0);
                break;
            case R.id.id_top:
                processImageView.setType(1);
                break;
            case R.id.id_right:
                processImageView.setType(2);
                break;
            case R.id.id_bottom:
                processImageView.setType(3);
                break;
        }
        progress=0;
        initData();
    }



    //模拟图片上传进度
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(progress==100){//图片上传完成
                        mHandler.sendEmptyMessage(SUCCESS);
                        return;
                    }
                    progress++;
                    processImageView.setProgress(progress);
                    try{
                        Thread.sleep(100);  //暂停0.1秒
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
