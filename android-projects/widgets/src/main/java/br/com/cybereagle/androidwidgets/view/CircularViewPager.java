package br.com.cybereagle.androidwidgets.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import br.com.cybereagle.androidwidgets.adapter.WrapperCircularPagerAdapter;

public class CircularViewPager extends ViewPager {

    private WrapperCircularPagerAdapter wrapperCircularPagerAdapter;

    public CircularViewPager(Context context) {
        super(context);
    }

    public CircularViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (!(adapter instanceof WrapperCircularPagerAdapter)) {
            throw new IllegalArgumentException("The CircularViewPager accepts only instances of WrapperCircularPagerAdapter. Please, use this class to wrap your Adapter.");
        }
        super.setAdapter(adapter);
        this.wrapperCircularPagerAdapter = (WrapperCircularPagerAdapter) adapter;
        // offset first element so that we can scroll to the left
        setCurrentItem(getOffsetAmount(), false);
    }


    public void setCurrentVirtualItem(int virtualItem) {
        setCurrentVirtualItem(virtualItem, true);
    }

    public void setCurrentVirtualItem(int virtualItem, boolean smoothScroll){
        int currentVirtualItem = getCurrentVirtualItem();

        int leftDistance;
        int rightDistance;
        int size = wrapperCircularPagerAdapter.getRealCount();

        if(virtualItem < currentVirtualItem){
            leftDistance = currentVirtualItem - virtualItem;

            int stepsToArriveToFirstElement = size - currentVirtualItem;
            rightDistance = stepsToArriveToFirstElement + virtualItem;
        }
        else {
            rightDistance = virtualItem - currentVirtualItem;

            int lastIndex = size - 1;
            int stepsToArriveToLastElement = currentVirtualItem + 1;
            leftDistance = stepsToArriveToLastElement + (lastIndex - virtualItem);
        }

        int currentItem = getCurrentItem();

        if(leftDistance < rightDistance){
            setCurrentItem(currentItem - leftDistance, smoothScroll);
        }
        else {
            setCurrentItem(currentItem + rightDistance, smoothScroll);
        }
    }

    public int getCurrentVirtualItem() {
        return getCurrentItem() % wrapperCircularPagerAdapter.getRealCount();
    }

    private int getOffsetAmount() {
        // allow for 100 back cycles from the beginning
        // should be enough to create an illusion of infinity
        // warning: scrolling to very high values (1,000,000+) results in
        // strange drawing behaviour
        return wrapperCircularPagerAdapter.getRealCount() * 100;
    }
}
