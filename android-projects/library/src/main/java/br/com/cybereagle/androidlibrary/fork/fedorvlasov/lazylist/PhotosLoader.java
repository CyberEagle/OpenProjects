package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;

public abstract class PhotosLoader implements Runnable{

    protected ImageLoader imageLoader;
    protected PhotoToLoad photoToLoad;
    protected Class<? extends BitmapDisplayer> bitmapDisplayerImplementation;

    public PhotosLoader() {

    }

    public PhotosLoader(ImageLoader imageLoader, PhotoToLoad photoToLoad, Class<? extends BitmapDisplayer> bitmapDisplayerImplementation) {
        this.imageLoader = imageLoader;
        this.photoToLoad = photoToLoad;
        this.bitmapDisplayerImplementation = bitmapDisplayerImplementation;
    }

    protected BitmapDisplayer getBitmapDisplayer(Bitmap bitmap){
        BitmapDisplayer bitmapDisplayer = null;
        try {
            bitmapDisplayer = bitmapDisplayerImplementation.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        bitmapDisplayer.setImageLoader(imageLoader);
        bitmapDisplayer.setBitmap(bitmap);
        bitmapDisplayer.setPhotoToLoad(photoToLoad);
        return bitmapDisplayer;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void setPhotoToLoad(PhotoToLoad photoToLoad) {
        this.photoToLoad = photoToLoad;
    }

    public void setBitmapDisplayerImplementation(Class<? extends BitmapDisplayer> bitmapDisplayerImplementation) {
        this.bitmapDisplayerImplementation = bitmapDisplayerImplementation;
    }
}
