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
        this.wrapperCircularPagerAdapter.setCircularViewPager(this);
        // offset first element so that we can scroll to the left
        setCurrentItem(wrapperCircularPagerAdapter.getOffsetAmount(), false);
    }


    public void setCurrentVirtualItem(int virtualItem) {
        setCurrentVirtualItem(virtualItem, true);
    }

    public void setCurrentVirtualItem(int virtualItem, boolean smoothScroll){
        setCurrentVirtualItem(virtualItem, getCurrentItem(), smoothScroll);
    }

    public void setCurrentVirtualItem(int virtualItem, int currentItem, boolean smoothScroll){
        int currentVirtualItem = getCurrentVirtualItem();

        int leftDistance;
        int rightDistance;
        int size = wrapperCircularPagerAdapter.getVirtualCount();

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

        if(leftDistance < rightDistance){
            setCurrentItem(currentItem - leftDistance, smoothScroll);
        }
        else {
            setCurrentItem(currentItem + rightDistance, smoothScroll);
        }
    }

    /**
     *
     * @return Current item or -1 if the ViewPager is empty.
     */
    public int getCurrentVirtualItem() {
        final int virtualCount = wrapperCircularPagerAdapter.getVirtualCount();
        if(virtualCount > 0) {
            return getCurrentItem() % virtualCount;
        }
        return -1;
    }

    public PagerAdapter getVirtualAdapter() {
        return wrapperCircularPagerAdapter != null ? wrapperCircularPagerAdapter.getAdapter() : null;
    }
}
