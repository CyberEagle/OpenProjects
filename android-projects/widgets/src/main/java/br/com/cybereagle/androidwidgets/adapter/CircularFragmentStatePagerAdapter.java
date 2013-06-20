package br.com.cybereagle.androidwidgets.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import br.com.cybereagle.androidwidgets.view.CircularViewPager;

public abstract class CircularFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private CircularViewPager circularViewPager;

    public CircularFragmentStatePagerAdapter(FragmentManager fm, CircularViewPager circularViewPager) {
        super(fm);
        this.circularViewPager = circularViewPager;
    }

    /**]
     * @see CircularFragmentPagerAdapter#getVirtualItem(int)
     */
    @Override
    public final Fragment getItem(int position) {
        return getVirtualItem(position % getCount());
    }

    public abstract Fragment getVirtualItem(int position);

}
