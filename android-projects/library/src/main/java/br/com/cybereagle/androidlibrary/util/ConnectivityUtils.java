package br.com.cybereagle.androidlibrary.util;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import roboguice.inject.ContextSingleton;

import javax.inject.Inject;

@ContextSingleton
public class ConnectivityUtils {

    @Inject
    private ConnectivityManager connectivityManager;

    public boolean isOnline() {
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected() && netInfo.isAvailable()) {
            return true;
        }
        return false;
    }

}
