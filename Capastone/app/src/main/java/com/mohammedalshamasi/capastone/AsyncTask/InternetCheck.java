package com.mohammedalshamasi.capastone.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

 public  class InternetCheck extends AsyncTask<Activity, Void, Boolean> {


    protected Boolean doInBackground(Activity... activitys) {

        return isNetworkAvailable(activitys[0]);

    }

    public static boolean isNetworkAvailable(Context context){

        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
