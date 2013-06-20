package br.com.cybereagle.androidwidgets.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

public abstract class CircularPagerAdapter extends PagerAdapter {

    /**
     * @see CircularPagerAdapter#instantiateVirtualItem(android.view.ViewGroup, int)
     */
    @Override
    public final Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position%getCount());
    }

    public abstract Object instantiateVirtualItem(ViewGroup container, int position);
}
