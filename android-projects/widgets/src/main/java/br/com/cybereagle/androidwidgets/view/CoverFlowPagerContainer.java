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
import android.util.AttributeSet;
import android.view.View;
import br.com.cybereagle.androidwidgets.util.ViewUtils;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

public class CoverFlowPagerContainer extends PagerContainer{

    private static final int MAX_ROTATION_ANGLE = 60;

    public CoverFlowPagerContainer(Context context) {
        super(context);
    }

    public CoverFlowPagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoverFlowPagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private List<View> getViewPagerChildren(){
        int childCount = pager.getChildCount();
        List<View> children = new ArrayList<View>(childCount);

        for(int i=0; i<childCount; i++){
            children.add(pager.getChildAt(i));
        }

        return children;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        updateCoverFlow();
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    public void updateCoverFlow() {
        int childCount = pager.getChildCount();
        int[] xy = new int[2];
        for(int i=0; i<childCount; i++){
            View child = pager.getChildAt(i);

            ViewUtils.enableHardwareLayer(child);
            int childMeasuredWidth = child.getMeasuredWidth();
            ViewHelper.setPivotX(child, childMeasuredWidth / 2);
            ViewHelper.setPivotY(child, child.getMeasuredHeight() / 2);


            child.getLocationInWindow(xy);
            float childX = xy[0];
            float childCenterX = childX + childMeasuredWidth / 2;
            int containerHalfWidth = getMeasuredWidth()/2;
            // Bigger the distance from the middle point, bigger the absolute value of the angle.
            float rotationDegrees = ((containerHalfWidth - childCenterX)/containerHalfWidth) * MAX_ROTATION_ANGLE;


            ViewHelper.setRotationY(child, rotationDegrees);
        }
    }

}
