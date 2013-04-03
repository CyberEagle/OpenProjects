package br.com.cybereagle.testlibrary.shadow;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import org.robolectric.internal.Implementation;
import org.robolectric.internal.Implements;

@Implements(DataSetObservable.class)
public class ShadowDataSetObservable extends ShadowObservable<DataSetObserver> {

    @Implementation
    public void notifyChanged() {
        synchronized(mObservers) {
            for (DataSetObserver observer : mObservers) {
                observer.onChanged();
            }
        }
    }

    @Implementation
    public void notifyInvalidated() {
        synchronized (mObservers) {
            for (DataSetObserver observer : mObservers) {
                observer.onInvalidated();
            }
        }
    }
}
