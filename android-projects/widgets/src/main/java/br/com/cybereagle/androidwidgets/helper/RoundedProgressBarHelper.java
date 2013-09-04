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

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ProgressBar;

/**
 * See http://www.jagsaund.com/blog/2011/11/6/customizing-your-progress-bar-part-one.html
 */
public class RoundedProgressBarHelper {

    private ProgressBar progressBar;

    public RoundedProgressBarHelper(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    /**
     * Instead of using clipping regions to uncover the progress bar as the
     * progress increases we increase the drawable regions for the progress bar.
     * Doing this gives us greater control and allows us to
     * show the rounded cap on the progress bar.
     */
    public void updateProgressBar() {
        Drawable progressDrawable = progressBar.getProgressDrawable();
        int primaryProgress = progressBar.getProgress();
        int secondaryProgress = progressBar.getSecondaryProgress();
        int max = progressBar.getMax();

        if (progressDrawable != null && progressDrawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) progressDrawable;

            final int width = layerDrawable.getBounds().right - layerDrawable.getBounds().left;

            final float primaryProgressScale = getScale(primaryProgress, max);
            final float secondaryProgressScale = getScale(secondaryProgress, max);

            Drawable primaryProgressDrawable = layerDrawable.findDrawableByLayerId(android.R.id.progress);
            Drawable secondaryProgressDrawable = layerDrawable.findDrawableByLayerId(android.R.id.secondaryProgress);

            if (primaryProgressDrawable != null) {
                setProgressBounds(primaryProgressDrawable, primaryProgressScale, width);
            }

            if(secondaryProgressDrawable != null){
                setProgressBounds(secondaryProgressDrawable, secondaryProgressScale, width);
            }
        }
    }

    private void setProgressBounds(Drawable progressDrawable, float scale, int width) {
        Rect progressBarBounds = progressDrawable.getBounds();
        progressBarBounds.right = progressBarBounds.left + (int) (width * scale + 0.5f);
        progressDrawable.setBounds(progressBarBounds);
    }

    private float getScale(int progress, int max) {
        float scale = max > 0 ? (float) progress / (float) max : 0;

        return scale;
    }

}
