package br.com.cybereagle.androidwidgets.listener;

import android.widget.AbsListView;

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;
    private boolean loadingViewAttached;

    public EndlessScrollListener() {
    }
    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (loading) {
            if (totalItemCount - (loadingViewAttached ? 1 : 0) > previousTotal) {
                loading = false;
                previousTotal = totalItemCount - (loadingViewAttached ? 1 : 0);
                currentPage++;
            }
        }
        if (hasMoreDataToLoad() && !loading && (totalItemCount - visibleItemCount - (loadingViewAttached ? 1 : 0)) <= (firstVisibleItem + visibleThreshold)) {
            loading = true;
            loadMoreData(currentPage);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    protected abstract boolean hasMoreDataToLoad();
    protected abstract void loadMoreData(int page);

    public void setLoadingViewAttached(boolean loadingViewAttached) {
        this.loadingViewAttached = loadingViewAttached;
    }

    public boolean isLoadingViewAttached() {
        return loadingViewAttached;
    }

}
