package com.tx.thereaper.thaparexpress;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/*
Checks the mobile's connectivity to internet by sending a ping to google.com. Application works only when connected to internet.
 */

public class Process extends Activity {

    ProgressDialog pDialog;  //Progress dialog to be shown while the activity checks for internet connectivity.
    Intent i;
    String LOG_TAG = "LOGCAT";
    boolean iState = true;  //Boolean for Internet connectivity state. True means connected and false means disconnected.
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        pDialog = new ProgressDialog(this);                     //Setting up the progress dialog.
        pDialog.setMessage("Checking internet connection");
        pDialog.setCancelable(false);

        showpDialog();

        new InternetCheck().execute();          // Async task to check the network connectivity.
    }

    private void showpDialog() {                // Makes the progress dialog visible.
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {                // Makes the progress dialog invisible.
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public boolean hasActiveInternetConnection() {          //Method for checking actual internet connectivity and confirms data transfer.
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.github.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(500);               //connection timeout till which the app waits for a response.
                urlc.connect();
                setiState(urlc.getResponseCode() == 200);  // Sets the value of iState variable according to response.
                Log.i(LOG_TAG, iState + " dega");
                return iState;
            } catch (IOException e) {
                Log.i(LOG_TAG, "Error checking internet connection");
            }
        } else {
            Log.i(LOG_TAG, "No network available!");
        }
        Log.i(LOG_TAG, "False returned!");
        setiState(false);
        return false;
    }

    public void setiState(boolean b){       // Sets the iState variable to either TRUE or FALSE.
        iState = b;
    }

    private boolean isNetworkAvailable() {      // Checks the phone's physical state of being connected to internet.
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private class InternetCheck extends AsyncTask<Void, Integer, Void> {        //Async task's definition.

        @Override
        protected void onPreExecute() {
            Log.i(LOG_TAG, "Showing dialog");
            showpDialog();
        }

        @Override
        protected Void  doInBackground(Void... params) {
            Log.i(LOG_TAG, "Background process started");
            hasActiveInternetConnection();      //Uses method hasActiveInternetConnection to change the variable iState.
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(LOG_TAG, "Hiding dialog");
            hidepDialog();
            setView();              //Decides the further movement of control in application.
        }
    }

    public void setView(){
        if(iState) {            //If the test returns positive then the control is passed on to the Main class.
            i = new Intent(Process.this,Main.class);
            startActivity(i);
            finish();
        }

        else {                  //If the test returns negative then an alertDialog pops up which makes the application quit.
            alert = new AlertDialog.Builder(Process.this).create();
            alert.setTitle("No connection!");
            alert.setCancelable(false);         //Makes the dialog invulnerable to cancelation attacks.
            alert.setMessage("Application is not able to receive any data. ");
            alert.setButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {        //Setting up the onclickListner of the button.
                    hidepDialog();
                    finish();
                }
            });
            alert.show();
        }
    }
}
