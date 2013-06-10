package br.com.cybereagle.androidlibrary.util;

import android.view.View;

import javax.inject.Singleton;

@Singleton
public class ViewUtils {

    public void setVisibility(int visibility, View... views){
        for(View view : views){
            view.setVisibility(visibility);
        }
    }
}
