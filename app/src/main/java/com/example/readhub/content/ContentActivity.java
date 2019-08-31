package com.example.readhub.content;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.readhub.R;


/**
 * 内容展示页，主要作用是打开链接的网页
 */
public class ContentActivity extends AppCompatActivity {

    private TextView mHeadLine;      //标题
    private TextView mSiteSource;    //作者栏
    private WebView mWebView;        //webView开网页


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        mHeadLine = this.findViewById(R.id.item_headline);
        mSiteSource = this.findViewById(R.id.item_siteSource);

        //得到文章详细信息
        Intent intent = getIntent();
        String siteSource = intent.getStringExtra("siteSource");
        String headline = intent.getStringExtra("headline");
        String url = intent.getStringExtra("url");

        //显示标题和来源
        mHeadLine.setText(headline);
        mSiteSource.setText(siteSource);

        //WebView用于打开网页
        mWebView = this.findViewById(R.id.item_web_view);

        //加载网络请求权限
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setAllowFileAccess(true);

        mWebView.setWebViewClient(new WebViewClient());

        mWebView.loadUrl(url);
    }

}
