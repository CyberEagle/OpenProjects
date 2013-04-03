package br.com.cybereagle.androidlibrary.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import br.com.cybereagle.androidlibrary.ui.view.EditableListView;

import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;

public abstract class EditableListFragment extends RoboSherlockFragment{

	private EditableListView editableListView;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.editableListView = new EditableListView(getActivity());
		return this.editableListView;
	}

	public EditableListView getEditableListView() {
		return editableListView;
	}
	
	public EditableListView getListView(){
		return getEditableListView();
	}
	
	public void setListAdapter(ListAdapter listAdapter){
		this.editableListView.setAdapter(listAdapter);
	}
	
}
