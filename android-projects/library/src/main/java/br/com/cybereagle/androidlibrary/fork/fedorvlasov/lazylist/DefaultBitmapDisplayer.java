package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;

public class DefaultBitmapDisplayer implements BitmapDisplayer {

    @Override
    public void displayBitmap(ImageLoader imageLoader, PhotoToLoad photoToLoad, Bitmap bitmap, ImageViewAdjuster imageViewAdjuster) {
        if (imageLoader.imageViewReused(photoToLoad))
            return;
        if (bitmap != null)
            imageViewAdjuster.setBitmap(photoToLoad.getImageView(), bitmap);
        else
            photoToLoad.getImageView().setImageResource(imageLoader.getStubId());
    }
}