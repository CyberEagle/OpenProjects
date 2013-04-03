package br.com.cybereagle.androidlibrary.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerIndicatorAdapter extends FragmentPagerAdapter{

	private List<Holder> holders;
	
	public ViewPagerIndicatorAdapter(FragmentManager fragmentManager){
		super(fragmentManager);
		holders = new ArrayList<ViewPagerIndicatorAdapter.Holder>();
	}
	
	public void addPage(Fragment fragment, String pageTitle){
		Holder holder = new Holder();
		holder.fragment = fragment;
		holder.pageTitle = pageTitle;
		holders.add(holder);
		notifyDataSetChanged();
	}
	
	@Override
	public Fragment getItem(int position) {
		return holders.get(position).fragment;
	}

	@Override
	public int getCount() {
		return holders.size();
	}

	
	@Override
	public CharSequence getPageTitle(int position) {
		return holders.get(position).pageTitle;
	}


	private class Holder {
		Fragment fragment;
		String pageTitle;
	}
}
