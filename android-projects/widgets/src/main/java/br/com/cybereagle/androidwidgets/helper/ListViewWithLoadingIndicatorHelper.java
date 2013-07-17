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

package br.com.cybereagle.androidwidgets.helper;

import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import br.com.cybereagle.androidwidgets.interfaces.ListViewWithLoadingIndicator;

public class ListViewWithLoadingIndicatorHelper implements ListViewWithLoadingIndicator {

    private ListView listView;

    private View loadingView;
    private boolean loadingViewAttached;
    private View dummyView;

    public ListViewWithLoadingIndicatorHelper(ListView listView){
        this.listView = listView;
    }

    public void beforeSetAdapter(ListAdapter listAdapter){
        dummyView = new View(listView.getContext());
        listView.addFooterView(dummyView, null, false);
    }

    public void afterSetAdapter(ListAdapter listAdapter){
        listView.removeFooterView(dummyView);
        dummyView = null;
    }

    @Override
    public void showLoadingView(){
        if(loadingView != null && !loadingViewAttached){
            listView.addFooterView(loadingView, null, false);
            loadingViewAttached = true;
        }
    }

    @Override
    public void hideLoadingView(){
        if(loadingView != null && loadingViewAttached){
            listView.removeFooterView(loadingView);
            loadingViewAttached = false;
        }

    }

    @Override
    public boolean isLoadingViewVisible(){
        return loadingViewAttached;
    }

    @Override
    public View getLoadingView() {
        return loadingView;
    }

    @Override
    public void setLoadingView(View loadingView) {
        this.loadingView = loadingView;
    }
}
