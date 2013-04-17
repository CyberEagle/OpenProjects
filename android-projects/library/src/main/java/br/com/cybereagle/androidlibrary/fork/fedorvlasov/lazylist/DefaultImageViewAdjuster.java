package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

public class DefaultImageViewAdjuster<T extends View> implements ImageAdjuster<T> {

    private Resources resources;

    public DefaultImageViewAdjuster(Resources resources) {
        this.resources = resources;
    }

    @Override
    public void setBitmap(T view, Bitmap bitmap) {
        if(view instanceof ImageView){
            ((ImageView)view).setImageBitmap(bitmap);
        }
        else{
            view.setBackground(new BitmapDrawable(resources,bitmap));
        }
    }
}
