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
