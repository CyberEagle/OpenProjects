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
