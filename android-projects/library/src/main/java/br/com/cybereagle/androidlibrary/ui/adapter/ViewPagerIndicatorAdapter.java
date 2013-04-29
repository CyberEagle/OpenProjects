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
