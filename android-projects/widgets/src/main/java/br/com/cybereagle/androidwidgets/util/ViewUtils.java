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

package br.com.cybereagle.androidwidgets.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

public class ViewUtils {

    public static final boolean IS_API_11_OR_ABOVE;

    static {
        IS_API_11_OR_ABOVE = Build.VERSION.SDK_INT >= 11;
    }

    public static int makeSquareMeasureSpec(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        int size;
        if(widthMode == View.MeasureSpec.EXACTLY && widthSize == 0){
            size = widthSize;
        }
        else if(heightMode == View.MeasureSpec.EXACTLY && heightSize == 0){
            size = heightSize;
        }
        else{
            size = widthSize < heightSize ? widthSize : heightSize;
        }

        return View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void enableHardwareLayer(View view) {
        if(!ViewUtils.IS_API_11_OR_ABOVE){
            return;
        }

        setLayer(view, View.LAYER_TYPE_HARDWARE);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void enableSoftwareLayer(View view){
        if(!ViewUtils.IS_API_11_OR_ABOVE){
            return;
        }

        setLayer(view, View.LAYER_TYPE_SOFTWARE);
    }

    private static void setLayer(View view, int layer) {
        if(view.getLayerType() != layer){
            view.setLayerType(layer, null);
        }
    }
}
