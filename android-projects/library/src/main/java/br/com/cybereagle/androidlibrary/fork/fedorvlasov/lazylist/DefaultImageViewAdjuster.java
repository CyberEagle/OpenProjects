package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class DefaultImageViewAdjuster implements ImageViewAdjuster {
    @Override
    public void setBitmap(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
