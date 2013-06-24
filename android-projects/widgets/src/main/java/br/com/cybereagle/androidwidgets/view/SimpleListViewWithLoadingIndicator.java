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

package br.com.cybereagle.androidwidgets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import br.com.cybereagle.androidwidgets.helper.ListViewWithLoadingIndicatorHelper;
import br.com.cybereagle.androidwidgets.interfaces.ListViewWithLoadingIndicator;

public class SimpleListViewWithLoadingIndicator extends ListView implements ListViewWithLoadingIndicator {

    private ListViewWithLoadingIndicatorHelper listViewWithLoadingIndicatorHelper;

    public SimpleListViewWithLoadingIndicator(Context context) {
        this(context, null, 0);
    }

    public SimpleListViewWithLoadingIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public SimpleListViewWithLoadingIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.listViewWithLoadingIndicatorHelper = new ListViewWithLoadingIndicatorHelper(this);
    }

    @Override
    public View getLoadingView() {
        return listViewWithLoadingIndicatorHelper.getLoadingView();
    }

    @Override
    public void setLoadingView(View loadingView) {
        listViewWithLoadingIndicatorHelper.setLoadingView(loadingView);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        listViewWithLoadingIndicatorHelper.beforeSetAdapter(adapter);
        super.setAdapter(adapter);
        listViewWithLoadingIndicatorHelper.afterSetAdapter(adapter);
    }

    @Override
    public void showLoadingView(){
        listViewWithLoadingIndicatorHelper.showLoadingView();
    }

    @Override
    public void hideLoadingView(){
        listViewWithLoadingIndicatorHelper.hideLoadingView();
    }

    @Override
    public boolean isLoadingViewVisible(){
        return listViewWithLoadingIndicatorHelper.isLoadingViewVisible();
    }
}
