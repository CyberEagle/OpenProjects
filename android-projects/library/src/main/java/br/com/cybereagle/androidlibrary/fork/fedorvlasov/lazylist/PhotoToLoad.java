package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.widget.ImageView;

public class PhotoToLoad {

    private String url;
    private ImageView imageView;

    public PhotoToLoad(String u, ImageView i) {
        url = u;
        imageView = i;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
