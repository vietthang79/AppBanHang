package com.example.appbanhang.Activity.TienIch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.appbanhang.R;

public class WedView_Activity extends AppCompatActivity {
    private WebView webView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wed_view);

//        ActionToolbar();
//        toolbar = findViewById(R.id.toobar_Web);
        webView = findViewById(R.id.webView);

        Intent intent = getIntent();
        if (intent != null){
            String link = intent.getStringExtra("link");
            webView.loadUrl(link);
            webView.setWebViewClient(new WebViewClient());// bấm vào nó chạy trong chính app mình chứ k chạy ra app mặc định
        }
    }

//    private void ActionToolbar() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//    }
}