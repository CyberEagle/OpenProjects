package br.com.cybereagle.androidbase.tip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TipFragment extends Fragment {

	public static final String LAYOUT_ID = "LAYOUT_ID";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		int layoutId = getArguments().getInt(LAYOUT_ID);
		return inflater.inflate(layoutId, container, false);
	}
	
}
