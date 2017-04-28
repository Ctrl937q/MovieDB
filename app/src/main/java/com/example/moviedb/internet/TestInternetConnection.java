package com.example.moviedb.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

public class TestInternetConnection {

    public static boolean checkConnection(@NonNull Context context) {
        return ((ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
