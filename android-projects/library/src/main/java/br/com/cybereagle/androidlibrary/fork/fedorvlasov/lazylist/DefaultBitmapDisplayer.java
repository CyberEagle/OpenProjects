package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

public class DefaultBitmapDisplayer implements BitmapDisplayer {

    @Override
    public <T extends View> void displayBitmap(ImageLoader<T> imageLoader, PhotoToLoad<T> photoToLoad, Bitmap bitmap, ImageAdjuster<T> imageViewAdjuster) {
        if (imageLoader.viewReused(photoToLoad))
            return;
        if (bitmap != null){
            imageViewAdjuster.setBitmap(photoToLoad.getView(), bitmap);
        }
        else{
            View view = photoToLoad.getView();
            if(view instanceof ImageView){
                ((ImageView)view).setImageResource(imageLoader.getStubId());
            }
            else{
                view.setBackgroundResource(imageLoader.getStubId());
            }
        }
    }
}