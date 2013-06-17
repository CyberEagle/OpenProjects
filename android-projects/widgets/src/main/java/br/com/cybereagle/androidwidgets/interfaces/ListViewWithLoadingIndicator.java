package br.com.cybereagle.androidwidgets.interfaces;

import android.view.View;

public interface ListViewWithLoadingIndicator {

    void showLoadingView();
    void hideLoadingView();
    boolean isLoadingViewVisible();
    View getLoadingView();
    void setLoadingView(View loadingView);

}
