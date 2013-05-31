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

package br.com.cybereagle.androidlibrary.util;

import android.content.Context;
import android.content.res.TypedArray;

import javax.inject.Singleton;

@Singleton
public class StyleUtils {

    /**
     * It's useful when you have a style attribute (style="?layout_example") in a
     * View and it's bound to a style in an Theme. So, you want to get some styled
     * attributes programatically based on an attribute and a theme.
     * @param context
     * @param theme Something like R.style.Theme_Example
     * @param styleAttr Something like R.attr.layout_example
     * @param wantedAttrs Something like: [android.R.attr.layout_width, android.R.attr.layout_height]
     * @return TypedArray with the attributes. Remember to recycle it!
     */
    public TypedArray getAttrsByThemeAndAttr(Context context, int theme, int styleAttr, int... wantedAttrs){
        TypedArray a = context.getTheme().obtainStyledAttributes(theme, new int[]{styleAttr});
        int styleId = a.getResourceId(0, 0);
        a.recycle();

        return context.getTheme().obtainStyledAttributes(styleId, wantedAttrs);
    }

}
