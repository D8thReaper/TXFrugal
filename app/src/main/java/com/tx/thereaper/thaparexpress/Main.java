package com.tx.thereaper.thaparexpress;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/*
Consists of a webView which links to the thaparexpress' official site.
 */

public class Main extends Activity {

    WebView webView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        webView = (WebView) findViewById(R.id.webView);  //Linking the webView to the layout.

        webView.setWebViewClient(new HelloWebViewClient());  //Setting HelloWebViewClient as the default browser.

        if (savedInstanceState!=null){                       //Checking if the application is being run for the first time in its lifecycle.
            webView.restoreState(savedInstanceState);       //Restoring the savedState of the application.
        }else {
            webView.loadUrl("http://android.thaparexpress.in");   //Pointing the webView to the given URL
            webView.getSettings().setJavaScriptEnabled(true);  //Allowing navigation for the application.
            webView.canGoBack();                                //Allowing back movement in webView.
            onSaveInstanceState(savedInstanceState);      //Saving the instance of the running activity.
        }
    }

    @Override                                               //Saving instance of the app's webView.
    protected void onSaveInstanceState(Bundle outState) {
        webView.saveState(outState);
    }

    @Override
    public void onBackPressed() {                       //Allowing application to move back.
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
