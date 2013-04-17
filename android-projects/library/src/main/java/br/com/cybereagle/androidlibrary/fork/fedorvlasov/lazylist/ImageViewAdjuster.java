package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Used if you want to apply some effect.
 */
public interface ImageViewAdjuster {

    void setBitmap(ImageView imageView, Bitmap bitmap);

}
