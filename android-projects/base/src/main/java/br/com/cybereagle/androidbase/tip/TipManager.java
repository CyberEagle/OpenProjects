package br.com.cybereagle.androidbase.tip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

/**
 * Classe utilizada para controlar uma determinada quantidade de Tips (dicas/tutoriais).
 * @author Fernando Camargo
 *
 */
public class TipManager {

	private final int SWIPE_MIN_DISTANCE;
    
	private final Activity activity;
	private int groupId;
	private List<Integer> viewStubs;
	private List<View> viewPanels;
	private int index;
	private TipTask currentTipTask;
	private long tempoView;
	private boolean keepRunning;
	private boolean isFinishing;
	
	public TipManager(Activity activity, long tempoView, int groupId){
		this(activity, tempoView, getChildrenViewStubs(activity, groupId));
		this.groupId = groupId;
	}
	
	public TipManager(Activity activity, long tempoView, int groupId, List<Integer> exclusoes){
		this(activity, tempoView, getChildrenViewStubs(activity, groupId, exclusoes));
		this.groupId = groupId;
	}
	
	public TipManager(Activity activity, long tempoView, int groupId, Integer... exclusoes){
		this(activity, tempoView, groupId, Arrays.asList(exclusoes));
	}
	
	/**
	 * Construtor do {@link TipManager}.
	 * @param activity {@link Activity} onde aparecerão os Tips
	 * @param tempoView Tempo, em segundos, em que cada Tip permancerá na tela
	 * @param viewStubs Lista de IDs das {@link ViewStub}s dos Tips. 
	 */
	private TipManager(Activity activity, long tempoView, List<Integer> viewStubs) {
		this.activity = activity;
		this.tempoView = tempoView;
		this.viewStubs = viewStubs;
		this.viewPanels = new ArrayList<View>();
		this.keepRunning = true;
		
		DisplayMetrics dm = this.activity.getResources().getDisplayMetrics();
		SWIPE_MIN_DISTANCE = (int)(120 * dm.densityDpi / 160.0f);
	}
	
	private static List<Integer> getChildrenViewStubs(Activity activity, int groupId){
		ViewGroup viewGroup = (ViewGroup) activity.findViewById(groupId);
		List<Integer> idStubs = new ArrayList<Integer>();
		for(int i=0; i<viewGroup.getChildCount(); i++){
			idStubs.add(((ViewStub)viewGroup.getChildAt(i)).getId());
		}
		return idStubs;
	}
	
	private static List<Integer> getChildrenViewStubs(Activity activity, int groupId, List<Integer> exclusoes) {
		ViewGroup viewGroup = (ViewGroup) activity.findViewById(groupId);
		List<Integer> idStubs = new ArrayList<Integer>();
		for(int i=0; i<viewGroup.getChildCount(); i++){
			int id = ((ViewStub)viewGroup.getChildAt(i)).getId();
			if(!exclusoes.contains(id)){
				idStubs.add(id);
			}
		}
		return idStubs;
	}

	public void start(){
		index = -1;
		onStart();
		next();
	}
	
	public void stop(){
		keepRunning = false;
		if(currentTipTask != null){
			stopCurrent();
		}
		onFinish();
	}
	
	public boolean isRunning(){
		return keepRunning && (index+1) < viewStubs.size();
	}
	
	private void onFinish(){
		ViewGroup viewGroup = (ViewGroup) activity.findViewById(groupId);
		if(viewGroup != null && viewGroup.getVisibility() == View.VISIBLE){
			viewGroup.setVisibility(View.GONE);
		}
	}
	
	private void onStart(){
		isFinishing = false;
		keepRunning = true;
		ViewGroup viewGroup = (ViewGroup) activity.findViewById(groupId);
		if(viewGroup != null && viewGroup.getVisibility() == View.GONE){
			viewGroup.setVisibility(View.VISIBLE);
		}
	}
	
	private void stopCurrent(){
		if(currentTipTask.getStatus().equals(AsyncTask.Status.RUNNING)){
			currentTipTask.cancel(true);
		}
	}
	
	private void next(){
		if(currentTipTask != null){
			currentTipTask.setLeftToRight(false);
			stopCurrent();
		}
		if (keepRunning && (index+1) < viewStubs.size()) {
			index++;
			View tipPanel = getTipPanel();
			currentTipTask = new TipTask(tipPanel);
			currentTipTask.execute();
		}
		else{
			isFinishing = true;
		}
	}

	private void back(){
		if(currentTipTask != null && index != 0){
			currentTipTask.setLeftToRight(true);
			stopCurrent();
		}
		if(keepRunning && index != 0 && index >= 0){
			index--;
			View tipPanel = getTipPanel();
			currentTipTask = new TipTask(tipPanel, true);
			currentTipTask.execute();
		}
	}

	private View getTipPanel() {
		View tipPanel;
		if(viewPanels.size() > index){
			tipPanel = viewPanels.get(index);
		}
		else{
			tipPanel = ((ViewStub) activity.findViewById(viewStubs.get(index))).inflate();
			viewPanels.add(tipPanel);
		}
		return tipPanel;
	}

	private class TipTask extends AsyncTask<Void, Void, Void> {

		private View tipPanel;
		private boolean leftToRight;
		
		public TipTask(View tipPanel) {
			this.tipPanel = tipPanel;
			this.leftToRight = false;
		}
		
		public TipTask(View tipPanel, boolean leftToRight){
			this.tipPanel = tipPanel;
			this.leftToRight = leftToRight;
		}

		// Roda na thread normal
		@Override
		protected void onPreExecute() {
			if (tipPanel != null) {
				tipPanel.setVisibility(View.VISIBLE);
				Animation animation = AnimationUtils.makeInAnimation(activity, leftToRight);
				animation.setDuration(500);
				tipPanel.startAnimation(animation);
				tipPanel.setOnTouchListener(new SwipeDetector());
			}
			super.onPreExecute();
		}

		// Roda em outra thread
		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				Thread.sleep(tempoView * 1000);
			} catch (InterruptedException e) {
				
			}
			return null;
		}

		// Roda na thread normal
		@Override
		protected void onPostExecute(Void v) {
			desaparecer();
			next();
			super.onPostExecute(v);
		}

		@Override
		protected void onCancelled(Void result) {
			desaparecer();
			super.onCancelled(result);
		}

		@Override
		protected void onCancelled() {
			desaparecer();
			super.onCancelled();
		}

		private void desaparecer() {
			if(tipPanel != null){
				Animation animation = AnimationUtils.makeOutAnimation(activity, leftToRight);
				animation.setDuration(500);
				animation.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						if (isFinishing) {
							onFinish();
						}
					}
				});
				tipPanel.startAnimation(animation);
				tipPanel.setVisibility(View.GONE);
			}
		}

		public void setLeftToRight(boolean leftToRight) {
			this.leftToRight = leftToRight;
		}
		
	}
	
	private class SwipeDetector implements View.OnTouchListener {

	    static final String logTag = "ActivitySwipeDetector";
	    private float downX, downY, upX, upY;

	    public SwipeDetector(){
	    }

	    public void onRightToLeftSwipe(View v){
	        next();
	    }

	    public void onLeftToRightSwipe(View v){
	        back();
	    }

	    public void onTopToBottomSwipe(View v){
	    	
	    }

	    public void onBottomToTopSwipe(View v){
	    	
	    }

	    public boolean onTouch(View v, MotionEvent event) {
	        switch(event.getAction()){
		        case MotionEvent.ACTION_DOWN: {
		            downX = event.getX();
		            downY = event.getY();
		            return true;
		        }
		        case MotionEvent.ACTION_UP: {
		            upX = event.getX();
		            upY = event.getY();
	
		            float deltaX = downX - upX;
		            float deltaY = downY - upY;
	
		            // swipe horizontal?
		            if(Math.abs(deltaX) > SWIPE_MIN_DISTANCE){
		                // left or right
		                if(deltaX < 0) { this.onLeftToRightSwipe(v); return true; }
		                if(deltaX > 0) { this.onRightToLeftSwipe(v); return true; }
		            }
		            else {
		                Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long, need at least " + SWIPE_MIN_DISTANCE);
		            }
	
		            // swipe vertical?
		            if(Math.abs(deltaY) > SWIPE_MIN_DISTANCE){
		                // top or down
		                if(deltaY < 0) { this.onTopToBottomSwipe(v); return true; }
		                if(deltaY > 0) { this.onBottomToTopSwipe(v); return true; }
		            }
		            else {
		                Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long, need at least " + SWIPE_MIN_DISTANCE);
		                v.performClick();
		            }
		        }
	        }
	        return false;
	    }

	}
}
