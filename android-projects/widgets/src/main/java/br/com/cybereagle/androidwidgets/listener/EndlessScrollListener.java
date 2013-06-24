/*
 * Copyright 2013 Cyber Eagle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package br.com.cybereagle.androidwidgets.listener;

import android.widget.AbsListView;
import android.widget.ListView;

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;

    public EndlessScrollListener() {
    }
    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        ListView listView = (ListView) view;
        int headerViewsCount = listView.getHeaderViewsCount();
        int footerViewsCount = listView.getFooterViewsCount();
        int liquidTotalItemCount = totalItemCount - headerViewsCount - footerViewsCount;
        if (loading) {
            if (liquidTotalItemCount > previousTotal) {
                loading = false;
                previousTotal = liquidTotalItemCount;
                currentPage++;
            }
        }
        if (hasMoreDataToLoad() && !loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= (totalItemCount - footerViewsCount)) {
            loading = true;
            loadMoreData(currentPage);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    protected abstract boolean hasMoreDataToLoad();
    protected abstract void loadMoreData(int page);

}
