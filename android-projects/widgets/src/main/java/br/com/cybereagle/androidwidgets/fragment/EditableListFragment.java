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

package br.com.cybereagle.androidwidgets.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import br.com.cybereagle.androidwidgets.view.EditableListView;
import roboguice.fragment.RoboFragment;

public abstract class EditableListFragment extends RoboFragment {

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
