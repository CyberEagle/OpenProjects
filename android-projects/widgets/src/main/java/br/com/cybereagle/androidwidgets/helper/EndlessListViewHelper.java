package br.com.cybereagle.androidwidgets.helper;

import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import br.com.cybereagle.androidwidgets.listener.EndlessScrollListener;

public class EndlessListViewHelper {

    private ListView listView;

    private View loadingView;
    private boolean loadingViewAttached;
    private View dummyView;

    public EndlessListViewHelper(ListView listView){
        this.listView = listView;
    }

    public void beforeSetAdapter(ListAdapter listAdapter){
        dummyView = new View(listView.getContext());
        listView.addFooterView(dummyView);
    }

    public void afterSetAdapter(ListAdapter listAdapter){
        listView.removeFooterView(dummyView);
        dummyView = null;
    }

    public void showLoadingView(){
        if(loadingView != null && !loadingViewAttached){
            listView.addFooterView(loadingView, null, false);
            loadingViewAttached = true;
        }
    }

    public void hideLoadingView(){
        if(loadingView != null && loadingViewAttached){
            listView.removeFooterView(loadingView);
            loadingViewAttached = false;
        }

    }

    public boolean isLoadingViewVisible(){
        return loadingViewAttached;
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public View getLoadingView() {
        return loadingView;
    }

    public void setLoadingView(View loadingView) {
        this.loadingView = loadingView;
    }
}
