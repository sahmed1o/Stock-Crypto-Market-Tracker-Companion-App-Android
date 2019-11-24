package com.lans.statscryptomarket;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;




public class MainActivity extends Activity {
    private WebView xWalkWebView;
    private String url_l = "https://statscryptomarket.com/"; //this url is for the crypto market tracker app
    //private String url_l = "https://statsstockmarket.com/"; //this url is for the stock market tracker app

/*
    @Override
    public boolean onKeyDown(int code, KeyEvent e) {
        if(code == KeyEvent.KEYCODE_BACK){

            return true;
        }else{
            return super.onKeyDown(code, e);
        }
    }
*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Context context = this;


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        xWalkWebView=(WebView) findViewById(R.id.xwalkWebView);

        final WebSettings  settings = xWalkWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setAppCacheEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setBuiltInZoomControls(false);
        settings.setDomStorageEnabled( true );
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setSupportMultipleWindows(true);

        //launch prompt to rate app
        AppRater.app_launched(this);

        xWalkWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, android.os.Message resultMsg)
            {
                WebView.HitTestResult result = view.getHitTestResult();
                String data = result.getExtra();
                Context context = view.getContext();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
                context.startActivity(browserIntent);
                return false;
            }
        });

        if (getIntent().getExtras() != null) {
            xWalkWebView.loadUrl(url_l);
        }
        else {
            // Do anything with embedding API
            xWalkWebView.loadUrl(url_l);
        }


        xWalkWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean  shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                //Do Stuff
                Log.d("tag","page changed");
                boolean contains = url.contains("mobile");
                /*
                if a seperate mobile page is used, then a prompt or advert can
                be shown here depending on what device the user is on. Set the URL variable to point to
                the mobile page

                e.g.
                https://www.statsstockmarket.com/mobile

                */
                if(contains){
                    //on the mobile web page
                }else {
                    //on the desktop web page
                }
                return false;
            }
        });


    }



}
