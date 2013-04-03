package br.com.cybereagle.androidbase.tip;

import roboguice.fragment.RoboFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.cybereagle.androidbase.R;

public class TipManagerFragment extends RoboFragment {

	public static final String TAG = "TIP_MANAGER_FRAGMENT";
	
	public static final String TIP_LAYOUT_IDS_KEY = "TIP_LAYOUT_IDS";
	public static final String TIMEOUT_KEY = "TIMEOUT_IDS";

	private TipPagerAdapter tipPagerAdapter;
	private ViewPager viewPager;

	private long timeout;
//	private TipChangeTask tipChangeTask;
	
	private boolean hidden = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.timeout = getArguments().getLong(TIMEOUT_KEY, 10);
//		this.tipChangeTask = new TipChangeTask();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View tipsView = inflater.inflate(R.layout.tip_manager_layout, container, false);
		this.tipPagerAdapter = new TipPagerAdapter(getFragmentManager(), getArguments().getIntArray(TIP_LAYOUT_IDS_KEY));
		this.viewPager = (ViewPager) tipsView.findViewById(R.id.tip_pager);
		this.viewPager.setSaveEnabled(false);
		this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
//				if (tipChangeTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
//					tipChangeTask.cancel(true);
//				}
				if(position == (tipPagerAdapter.getCount()-1)){
					stop();
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		new SetViewPagerAdapterTask().execute();
		return tipsView;
	}

	public void start(FragmentActivity fragmentActivity) {
		FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
		fragmentTransaction.add(android.R.id.content, this, TAG);
		fragmentTransaction.commit();
		hidden = false;
	}

	public boolean isShowing(){
		return getFragmentManager() == null ? false : !hidden;  
	}
	
	private void nextItem(){
		viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
	}

	public void stop() {
//		if ( tipChangeTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
//			tipChangeTask.cancel(true);
//		}
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
		fragmentTransaction.hide(this);
		fragmentTransaction.commit();
		hidden = true;
		
		new DestroyTask(getFragmentManager()).execute();
	}

	private class SetViewPagerAdapterTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			viewPager.setAdapter(tipPagerAdapter);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}
		
	}
	
	private class TipChangeTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(timeout * 1000);
			} catch (InterruptedException e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			nextItem();
		}
	}
	
	private class DestroyTask extends AsyncTask<Void, Void, Void> {

		private FragmentManager fragmentManager;
		
		public DestroyTask(FragmentManager fragmentManager) {
			this.fragmentManager = fragmentManager;
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
		
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			try{
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				tipPagerAdapter.destroy(fragmentTransaction);
				fragmentTransaction.remove(TipManagerFragment.this);
				fragmentTransaction.commit();
			}
			catch(Exception e){
				
			}
		}
		
	}
}
