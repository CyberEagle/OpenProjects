package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;

public class DefaultBitmapDisplayer extends BitmapDisplayer {


    public DefaultBitmapDisplayer(){

    }

    public DefaultBitmapDisplayer(ImageLoader imageLoader, Bitmap bitmap, PhotoToLoad photoToLoad) {
        super(imageLoader, bitmap, photoToLoad);
    }

    public void run() {
        if (imageLoader.imageViewReused(photoToLoad))
            return;
        if (bitmap != null)
            photoToLoad.getImageView().setImageBitmap(bitmap);
        else
            photoToLoad.getImageView().setImageResource(imageLoader.getStubId());
    }
}