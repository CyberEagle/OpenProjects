package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;

/**
 * Created with IntelliJ IDEA.
 * User: fibo
 * Date: 16/04/13
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public abstract class BitmapDisplayer implements Runnable {
    protected ImageLoader imageLoader;
    protected Bitmap bitmap;
    protected PhotoToLoad photoToLoad;

    public BitmapDisplayer(){

    }

    public BitmapDisplayer(ImageLoader imageLoader, Bitmap bitmap, PhotoToLoad photoToLoad) {
        this.imageLoader = imageLoader;
        this.bitmap = bitmap;
        this.photoToLoad = photoToLoad;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public PhotoToLoad getPhotoToLoad() {
        return photoToLoad;
    }

    public void setPhotoToLoad(PhotoToLoad photoToLoad) {
        this.photoToLoad = photoToLoad;
    }
}
