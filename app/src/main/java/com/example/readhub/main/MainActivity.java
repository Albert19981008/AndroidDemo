package com.example.readhub.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.readhub.R;
import com.example.readhub.list.NewsListActivity;

import com.example.readhub.service.UpdateService;


/**
 * 登录页，将开启更新新闻的Service
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //加载布局
        setContentView(R.layout.activity_main);


        //更新新闻的服务
        Intent StartServiceIntent = new Intent(this, UpdateService.class);
        startService(StartServiceIntent);

        //登录按钮
        Button buttonLogin = findViewById(R.id.button_login);

        //设置Login监听器
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
                startActivity(intent);
            }
        });
    }
}

