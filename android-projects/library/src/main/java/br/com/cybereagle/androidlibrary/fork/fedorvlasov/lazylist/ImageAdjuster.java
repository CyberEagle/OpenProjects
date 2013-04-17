package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Used if you want to apply some effect.
 */
public interface ImageAdjuster<T extends View> {

    void setBitmap(T view, Bitmap bitmap);

}
