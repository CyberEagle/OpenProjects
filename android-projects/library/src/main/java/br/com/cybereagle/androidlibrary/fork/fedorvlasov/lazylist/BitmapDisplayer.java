package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;
import android.view.View;

public interface BitmapDisplayer {

    <T extends View> void displayBitmap(ImageLoader<T> imageLoader, PhotoToLoad<T> photoToLoad, Bitmap bitmap, ImageAdjuster<T> imageViewAdjuster);

}
