package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;

public class DefaultPhotoLoader extends PhotosLoader{
    @Override
    public void run() {
        try {
            if (imageLoader.imageViewReused(photoToLoad)){
                return;
            }

            Bitmap bmp = imageLoader.getBitmap(photoToLoad.getUrl());
            imageLoader.getMemoryCache().put(photoToLoad.getUrl(), bmp);
            if (imageLoader.imageViewReused(photoToLoad)){
                return;
            }

            imageLoader.execute(getBitmapDisplayer(bmp));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
