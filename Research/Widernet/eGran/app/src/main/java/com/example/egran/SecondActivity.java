package com.example.egran;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    public String fileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        WebView webView = (WebView) findViewById(R.id.webview);

        Intent intent = getIntent();
        String queryString = intent.getStringExtra("query");
        fileName = queryString;

        webView.loadUrl("file:///android_asset/" + fileName);
    }
}