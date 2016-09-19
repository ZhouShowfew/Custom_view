package com.steven.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.timeLine)
    Button mTimeLine;
    @Bind(R.id.wechatChoose_Pic)
    Button mWechatChoosePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.timeLine)
    void setTimeLine(){
        startActivity(new Intent(MainActivity.this,TimeLineActivity.class));
    }

    @OnClick(R.id.wechatChoose_Pic)
    void setWechatChoosePic(){
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }
}
