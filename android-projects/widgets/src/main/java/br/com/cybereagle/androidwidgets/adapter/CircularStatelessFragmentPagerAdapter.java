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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.StatelessFragmentPagerAdapter;
import android.view.ViewGroup;
import br.com.cybereagle.androidwidgets.view.CircularViewPager;

public abstract class CircularStatelessFragmentPagerAdapter extends StatelessFragmentPagerAdapter {

    private CircularViewPager circularViewPager;

    public CircularStatelessFragmentPagerAdapter(FragmentManager fm, CircularViewPager circularViewPager) {
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
