package br.com.cybereagle.testlibrary.shadow;

import android.database.ContentObservable;
import android.database.ContentObserver;
import org.robolectric.internal.Implementation;
import org.robolectric.internal.Implements;

@Implements(ContentObservable.class)
public class ShadowContentObservable extends ShadowObservable<ContentObserver> {

    @Override
    public void registerObserver(ContentObserver observer) {
        super.registerObserver(observer);
    }

    @Implementation
    public void dispatchChange(boolean selfChange) {
        synchronized(mObservers) {
            for (ContentObserver observer : mObservers) {
                if (!selfChange || observer.deliverSelfNotifications()) {
                    observer.dispatchChange(selfChange);
                }
            }
        }
    }

    @Implementation
    public void notifyChange(boolean selfChange) {
        synchronized(mObservers) {
            for (ContentObserver observer : mObservers) {
                observer.onChange(selfChange);
            }
        }
    }
}
