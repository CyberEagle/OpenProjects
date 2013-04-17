package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.view.View;
import android.widget.ImageView;

public class PhotoToLoad<T extends View> {

    private String url;
    private T view;

    public PhotoToLoad(String u, T i) {
        url = u;
        view = i;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }
}
