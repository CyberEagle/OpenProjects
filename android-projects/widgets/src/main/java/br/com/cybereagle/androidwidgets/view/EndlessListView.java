package br.com.cybereagle.androidwidgets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import br.com.cybereagle.androidwidgets.helper.EndlessListViewHelper;
import br.com.cybereagle.androidwidgets.listener.EndlessScrollListener;

public class EndlessListView extends ListView {

    private EndlessListViewHelper endlessListViewHelper;

    public EndlessListView(Context context) {
        this(context, null, 0);
    }

    public EndlessListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public EndlessListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.endlessListViewHelper = new EndlessListViewHelper(this);
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        if(onScrollListener instanceof EndlessScrollListener){
            endlessListViewHelper.setEndlessScrollListener((EndlessScrollListener) onScrollListener);
        }
        super.setOnScrollListener(onScrollListener);
    }

    public View getLoadingView() {
        return endlessListViewHelper.getLoadingView();
    }

    public void setLoadingView(View loadingView) {
        endlessListViewHelper.setLoadingView(loadingView);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        endlessListViewHelper.beforeSetAdapter(adapter);
        super.setAdapter(adapter);
        endlessListViewHelper.afterSetAdapter(adapter);
    }

    public void showLoadingView(){
        endlessListViewHelper.showLoadingView();
    }

    public void hideLoadingView(){
        endlessListViewHelper.hideLoadingView();
    }

    public boolean isLoadingViewVisible(){
        return endlessListViewHelper.isLoadingViewVisible();
    }
}
