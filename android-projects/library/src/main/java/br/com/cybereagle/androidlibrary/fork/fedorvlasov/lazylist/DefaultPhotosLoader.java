package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;

public class DefaultPhotosLoader implements PhotosLoader{

    @Override
    public void loadPhoto(ImageLoader imageLoader, PhotoToLoad photoToLoad) {
        try {
            if (imageLoader.viewReused(photoToLoad)){
                return;
            }

            Bitmap bitmap = imageLoader.getBitmap(photoToLoad.getUrl());
            imageLoader.getMemoryCache().put(photoToLoad.getUrl(), bitmap);
            if (imageLoader.viewReused(photoToLoad)){
                return;
            }

            imageLoader.displayBitmap(photoToLoad, bitmap);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
