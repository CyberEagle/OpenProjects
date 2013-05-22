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
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;

public class EditableListView extends ScrollView {

	private LinearLayout linearLayout;
	
	private ListAdapter listAdapter;
	private AdapterDataSetObserver dataSetObserver;
	
	private Drawable divider;
    private int dividerHeight;

    public EditableListView(Context context) {
        this( context, null, 0);
    }

    public EditableListView(Context context, AttributeSet attrs) {
        this( context, attrs, 0);
    }
    
	public EditableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.linearLayout = new LinearLayout(context);
		this.linearLayout.setOrientation(LinearLayout.VERTICAL);
		@SuppressWarnings("deprecation")
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
		this.linearLayout.setLayoutParams(layoutParams);
		this.linearLayout.setDividerDrawable(context.getResources().getDrawable(android.R.drawable.divider_horizontal_bright));
		this.setLayoutParams(layoutParams);
		this.addView(linearLayout);
	}

	private void onChange(){
		int i=0;
		for(;i<listAdapter.getCount(); i++){
			View convertView = linearLayout.getChildAt(i);
			View view = listAdapter.getView(i, convertView, linearLayout);
			if(convertView != view){
				linearLayout.addView(view, i);
			}
		}
		
		for(;i<linearLayout.getChildCount(); i++){
			linearLayout.removeViewAt(i);
		}
		
		requestLayout();
	}
	
	public Drawable getDivider() {
		return divider;
	}

	public void setDivider(Drawable divider) {
		this.divider = divider;
	}

	public int getDividerHeight() {
		return dividerHeight;
	}

	public void setDividerHeight(int dividerHeight) {
		this.dividerHeight = dividerHeight;
	}

	public ListAdapter getAdapter() {
		return listAdapter;
	}

	public void setAdapter(ListAdapter adapter) {
		if(this.listAdapter != null && this.dataSetObserver != null){
			this.listAdapter.unregisterDataSetObserver(this.dataSetObserver);
		}
		this.listAdapter = adapter;
		linearLayout.removeAllViews();
		if(this.listAdapter != null){
			this.dataSetObserver = new AdapterDataSetObserver();
			this.listAdapter.registerDataSetObserver(dataSetObserver);
		}
	}
	
	private class AdapterDataSetObserver extends DataSetObserver {

		@Override
		public void onChanged() {
			super.onChanged();
			onChange();
		}

		@Override
		public void onInvalidated() {
			super.onInvalidated();
			linearLayout.removeAllViews();
		}
		
	}
}
