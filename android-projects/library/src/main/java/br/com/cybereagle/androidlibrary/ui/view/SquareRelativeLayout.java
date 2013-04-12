/*
 * Copyright 2013 Cyber Eagle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.cybereagle.androidlibrary.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SquareRelativeLayout extends RelativeLayout {
    public SquareRelativeLayout(Context context) {
        super(context);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int size;
        if(widthMode == MeasureSpec.EXACTLY && widthSize == 0){
            size = widthSize;
        }
        else if(heightMode == MeasureSpec.EXACTLY && heightSize == 0){
            size = heightSize;
        }
        else{
            size = widthSize < heightSize ? widthSize : heightSize;
        }

        int finalMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(finalMeasureSpec, finalMeasureSpec);
    }
}
