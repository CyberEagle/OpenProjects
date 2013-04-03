package br.com.cybereagle.androidlibrary.ui.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
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
		super(context);
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
