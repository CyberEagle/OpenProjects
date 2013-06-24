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
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import br.com.cybereagle.androidwidgets.helper.UninterceptableViewPagerHelper;
import br.com.cybereagle.androidwidgets.interfaces.UninterceptableViewPager;

public class SimpleUninterceptableViewPager extends ViewPager implements UninterceptableViewPager{

    private UninterceptableViewPagerHelper uninterceptableViewPagerHelper;

    public SimpleUninterceptableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        uninterceptableViewPagerHelper = new UninterceptableViewPagerHelper(this);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return uninterceptableViewPagerHelper.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean callRealOnInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }
}
