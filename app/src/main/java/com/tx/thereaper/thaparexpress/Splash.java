package com.tx.thereaper.thaparexpress;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/*
Displays a splash activity for 3 seconds and then transfers control to Process class.
 */

public class Splash extends Activity {


    private static int splashTime = 3000;    // Duration for the splash screen.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(Splash.this, Process.class);  //Passes control to process class which evaluates the internet connectivity of the application.
                startActivity(i);

                finish();
            }
        }, splashTime);
    }



}