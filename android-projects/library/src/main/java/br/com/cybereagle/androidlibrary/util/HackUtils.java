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

import android.widget.ListView;
import br.com.cybereagle.commonlibrary.util.ReflectionUtils;

import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;

@Singleton
public class HackUtils {


    public int measureHeightOfChildren(ListView listView, int widthMeasureSpec, int startPosition, int endPosition,
                                       final int maxHeight, int disallowPartialChildPosition) {
        try {
            return ReflectionUtils.invokeRestrictedMethod(listView, "measureHeightOfChildren",
                    new Integer[]{widthMeasureSpec, startPosition, endPosition, maxHeight, disallowPartialChildPosition},
                    int.class, int.class, int.class, int.class, int.class);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
