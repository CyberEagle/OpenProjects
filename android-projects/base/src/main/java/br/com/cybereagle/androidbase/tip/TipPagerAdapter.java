/*
 * Copyright 2013 Cyber Eagle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.cybereagle.androidbase.tip;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

public class TipPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> tipFragments;
		
	public TipPagerAdapter(FragmentManager fragmentManager, int... tipLayoutIds) {
		super(fragmentManager);
		this.tipFragments = createTipFragments(tipLayoutIds);
	}
	
	public static List<Fragment> createTipFragments(int... tipLayoutIds){
		List<Fragment> tipFragments = new ArrayList<Fragment>();
		for(int tipLayoutId : tipLayoutIds){
			Bundle bundle = new Bundle();
			bundle.putInt(TipFragment.LAYOUT_ID, tipLayoutId);
			Fragment tipFragment = new TipFragment();
			tipFragment.setArguments(bundle);
			tipFragments.add(tipFragment);
		}
		// Fragment invisível
		tipFragments.add(new Fragment());
		return tipFragments;
	}

	@Override
	public Fragment getItem(int position) {
		return tipFragments.get(position);
	}

	@Override
	public int getCount() {
		return tipFragments.size();
	}
	
	public void destroy(FragmentTransaction fragmentTransaction){
		List<Fragment> tipFragments = new ArrayList<Fragment>(this.tipFragments);
		this.tipFragments.clear();
		notifyDataSetChanged();
		for(Fragment tipFragment : tipFragments){
			fragmentTransaction.remove(tipFragment);
		}
	}

}
