package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

    private Context context;
    private Class<? extends BitmapDisplayer> bitmapDisplayerImplementation;
    private Class<? extends PhotosLoader> photosLoaderImplementation;

    private int stubId;

    private MemoryCache memoryCache = new MemoryCache();
    private FileCache fileCache;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private ExecutorService executorService;
    private Handler handler = new Handler();//handler to display images in UI thread

    public ImageLoader(Context context, int stubId){
        this(context, stubId, DefaultBitmapDisplayer.class, DefaultPhotoLoader.class);
    }

    public ImageLoader(Context context, int stubId, Class<? extends BitmapDisplayer> bitmapDisplayerImplementation, Class<? extends PhotosLoader> photosLoaderImplementation) {
        this.context = context;
        this.stubId = stubId;
        this.bitmapDisplayerImplementation = bitmapDisplayerImplementation;
        this.photosLoaderImplementation = photosLoaderImplementation;
        this.fileCache = new FileCache(context);
        this.executorService = Executors.newFixedThreadPool(5);
    }

    public void displayImage(String url, ImageView imageView) {
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);
        else {
            queuePhoto(url, imageView);
            imageView.setImageResource(stubId);
        }
    }

    private void queuePhoto(String url, ImageView imageView) {
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        try {
            PhotosLoader photosLoader = photosLoaderImplementation.newInstance();
            photosLoader.setImageLoader(this);
            photosLoader.setPhotoToLoad(p);
            photosLoader.setBitmapDisplayerImplementation(bitmapDisplayerImplementation);
            executorService.submit(photosLoader);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public Bitmap getBitmap(String url) {
        File f = fileCache.getFile(url);

        //from SD cache
        Bitmap b = decodeFile(f);
        if (b != null)
            return b;

        //from web
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            conn.disconnect();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Throwable ex) {
            ex.printStackTrace();
            if (ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private class FakePhotosLoader implements Runnable {
//        private PhotoToLoad photoToLoad;
//
//        FakePhotosLoader(PhotoToLoad photoToLoad) {
//            this.photoToLoad = photoToLoad;
//        }
//
//        @Override
//        public void run() {
//            try {
//                if (imageViewReused(photoToLoad))
//                    return;
//                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.capa1);
//                memoryCache.put(photoToLoad.getUrl(), bmp);
//                if (imageViewReused(photoToLoad))
//                    return;
//                BitmapDisplayer bd = bitmapDisplayerImplementation.newInstance();
//                bd.setImageLoader(ImageLoader.this);
//                bd.setBitmap(bmp);
//                bd.setPhotoToLoad(photoToLoad);
//
//                handler.post(bd);
//            } catch (Throwable th) {
//                th.printStackTrace();
//            }
//        }
//    }

    public boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.getImageView());
        if (tag == null || !tag.equals(photoToLoad.getUrl()))
            return true;
        return false;
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

    public void execute(BitmapDisplayer bitmapDisplayer){
        handler.post(bitmapDisplayer);
    }

    public MemoryCache getMemoryCache() {
        return memoryCache;
    }

    public int getStubId() {
        return stubId;
    }

    public Context getContext() {
        return context;
    }
}
