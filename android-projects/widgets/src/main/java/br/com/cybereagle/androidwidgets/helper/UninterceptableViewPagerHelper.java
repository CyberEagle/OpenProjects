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

package br.com.cybereagle.androidwidgets.helper;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import br.com.cybereagle.androidwidgets.interfaces.UninterceptableViewPager;

public class UninterceptableViewPagerHelper {

    private ViewPager viewPager;

    public UninterceptableViewPagerHelper(ViewPager viewPager) {
        this.viewPager = viewPager;
        if(!(viewPager instanceof UninterceptableViewPager)){
            throw new IllegalArgumentException("The ViewPager must implement " + UninterceptableViewPager.class.getName());
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = ((UninterceptableViewPager)viewPager).callRealOnInterceptTouchEvent(ev);
        if (ret) {
            viewPager.getParent().requestDisallowInterceptTouchEvent(true);
        }
        return ret;
    }
}
