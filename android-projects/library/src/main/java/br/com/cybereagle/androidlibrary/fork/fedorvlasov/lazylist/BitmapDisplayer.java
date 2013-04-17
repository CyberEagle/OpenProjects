package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;

public interface BitmapDisplayer {

    void displayBitmap(ImageLoader imageLoader, PhotoToLoad photoToLoad, Bitmap bitmap, ImageViewAdjuster imageViewAdjuster);

}
