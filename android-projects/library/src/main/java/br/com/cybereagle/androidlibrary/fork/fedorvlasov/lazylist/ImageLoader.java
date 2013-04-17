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
    private BitmapDisplayer bitmapDisplayer;
    private PhotosLoader photosLoader;
    private ImageViewAdjuster imageViewAdjuster;

    private int stubId;

    private MemoryCache memoryCache = new MemoryCache();
    private FileCache fileCache;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private ExecutorService executorService;
    private Handler handler = new Handler();//handler to display images in UI thread

    private ImageLoader(Context context, int stubId){
        this.context = context;
        this.stubId = stubId;
        fileCache = new FileCache(context);
    }

    public void displayImage(String url, ImageView imageView) {
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null){
            imageViewAdjuster.setBitmap(imageView, bitmap);
        }
        else {
            queuePhoto(url, imageView);
            imageView.setImageResource(stubId);
        }
    }

    private void queuePhoto(String url, ImageView imageView) {
        final PhotoToLoad photoToLoad = new PhotoToLoad(url, imageView);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                photosLoader.loadPhoto(ImageLoader.this, photoToLoad);
            }
        });
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
    public Bitmap decodeFile(File f) {
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

    public void displayBitmap(final PhotoToLoad photoToLoad, final Bitmap bitmap){
        handler.post(new Runnable() {
            @Override
            public void run() {
                bitmapDisplayer.displayBitmap(ImageLoader.this, photoToLoad, bitmap, imageViewAdjuster);
            }
        });
    }

    public MemoryCache getMemoryCache() {
        return memoryCache;
    }

    public FileCache getFileCache() {
        return fileCache;
    }

    public int getStubId() {
        return stubId;
    }

    public Context getContext() {
        return context;
    }

    public ImageViewAdjuster getImageViewAdjuster() {
        return imageViewAdjuster;
    }

    public static class Builder {

        private Context context;
        private BitmapDisplayer bitmapDisplayer;
        private PhotosLoader photosLoader;
        private ImageViewAdjuster imageViewAdjuster;
        private ExecutorService executorService;
        private int threadPoolSize = 5;
        private int stubId;

        public Builder(Context context, int stubId){
            this.context = context;
            this.stubId = stubId;
        }

        public Builder setBitmapDisplayer(BitmapDisplayer bitmapDisplayer) {
            this.bitmapDisplayer = bitmapDisplayer;
            return this;
        }

        public Builder setPhotosLoader(PhotosLoader photosLoader) {
            this.photosLoader = photosLoader;
            return this;
        }

        public Builder setImageViewAdjuster(ImageViewAdjuster imageViewAdjuster) {
            this.imageViewAdjuster = imageViewAdjuster;
            return this;
        }

        public Builder setExecutorService(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public Builder setThreadPoolSize(int threadPoolSize) {
            this.threadPoolSize = threadPoolSize;
            return this;
        }

        public ImageLoader build(){
            ImageLoader imageLoader = new ImageLoader(context, stubId);
            if(bitmapDisplayer != null){
                imageLoader.bitmapDisplayer = bitmapDisplayer;
            }
            else{
                imageLoader.bitmapDisplayer = new DefaultBitmapDisplayer();
            }
            if(photosLoader != null){
                imageLoader.photosLoader = photosLoader;
            }
            else{
                imageLoader.photosLoader = new DefaultPhotosLoader();
            }
            if(imageViewAdjuster != null){
                imageLoader.imageViewAdjuster = imageViewAdjuster;
            }
            else{
                imageLoader.imageViewAdjuster = new DefaultImageViewAdjuster();
            }
            if(executorService != null){
                imageLoader.executorService = executorService;
            }
            else{
                imageLoader.executorService = Executors.newFixedThreadPool(threadPoolSize);
            }

            return imageLoader;
        }

    }
}
