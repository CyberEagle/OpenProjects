package br.com.cybereagle.testlibrary.shadow;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.content.AsyncTaskLoader;
import org.robolectric.internal.Implementation;
import org.robolectric.internal.Implements;
import org.robolectric.internal.RealObject;

import java.lang.reflect.Field;

@Implements(AsyncTaskLoader.class)
public class ShadowAsyncTaskLoader<D> {

    @RealObject
    private AsyncTaskLoader<D> asyncTaskLoader;

    @Implementation
    void executePendingTask() {
        new AsyncTask<Void, Void, D>() {
            @Override
            protected D doInBackground(Void... params) {
                return (D) asyncTaskLoader.loadInBackground();
            }

            @Override
            protected void onPostExecute(D data) {
                updateLastLoadCompleteTimeField();
                asyncTaskLoader.deliverResult(data);
            }

            @Override
            protected void onCancelled(D data) {
                updateLastLoadCompleteTimeField();
                asyncTaskLoader.onCanceled(data);

                executePendingTask();
            }
        };
    }

    private void updateLastLoadCompleteTimeField() {
        try {
            Field mLastLoadCompleteTimeField = AsyncTaskLoader.class.getDeclaredField("mLastLoadCompleteTimeField");
            if(!mLastLoadCompleteTimeField.isAccessible()) {
                mLastLoadCompleteTimeField.setAccessible(true);
            }
            mLastLoadCompleteTimeField.set(asyncTaskLoader, SystemClock.uptimeMillis());

        } catch(NoSuchFieldException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
