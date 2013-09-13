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

package br.com.cybereagle.androidwidgets.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import br.com.cybereagle.androidwidgets.view.CircularViewPager;

public class WrapperCircularPagerAdapter extends PagerAdapter {

    private static final int DEFAULT_QUANTITY_OF_CYCLES = 100;

    private PagerAdapter adapter;
    private int quantityOfCycles;

    private CircularViewPager circularViewPager;

    public WrapperCircularPagerAdapter(PagerAdapter adapter) {
        this(adapter, DEFAULT_QUANTITY_OF_CYCLES);
    }

    /**
     *
     * @param adapter
     * @param quantityOfCycles The quantity of times the user can se all the elements going to the same side.
     *                         WARNING: Bigger the quantityOfCycles, worse the performance.
     */
    public WrapperCircularPagerAdapter(PagerAdapter adapter, int quantityOfCycles){
        this.adapter = adapter;
        this.quantityOfCycles = quantityOfCycles;
    }

    @Override
    public int getCount() {
        return 2*getOffsetAmount();
    }

    /**
     * @return the {@link #getCount()} result of the wrapped adapter
     */
    public int getVirtualCount() {
        return adapter.getCount();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return adapter.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        adapter.destroyItem(container, position, object);
    }

    /*
     * Delegate rest of methods directly to the inner adapter.
     */

    @Override
    public void finishUpdate(ViewGroup container) {
        adapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        adapter.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        return adapter.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        adapter.startUpdate(container);
    }

    @Override
    public int getItemPosition(Object object) {
        return adapter.getItemPosition(object);
    }

    /**
     * Offset for each side, if the current item is in the middle.
     * @return
     */
    public int getOffsetAmount() {
        return getVirtualCount() * quantityOfCycles;
    }

    /**
     * It tries to keep at the same virtual item, but near the middle of the pages to avoid the user reaching one of the ends.
     */
    @Override
    public void notifyDataSetChanged() {
        if(circularViewPager == null){
            throw new IllegalStateException("The WrapperCircularPagerAdapter isn't attached to a CircularViewPager");
        }
        if(getVirtualCount() > 0){
            int currentVirtualItem = circularViewPager.getCurrentVirtualItem();
            super.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
            circularViewPager.setCurrentVirtualItem(currentVirtualItem, getOffsetAmount()+currentVirtualItem, false);
        }
        else {
            super.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
        }
    }

    public PagerAdapter getAdapter() {
        return adapter;
    }

    public void setCircularViewPager(CircularViewPager circularViewPager) {
        this.circularViewPager = circularViewPager;
    }
}
