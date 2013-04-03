package br.com.cybereagle.testlibrary.shadow;

import android.database.Observable;
import org.robolectric.internal.Implementation;
import org.robolectric.internal.Implements;

import java.util.ArrayList;

@Implements(Observable.class)
public class ShadowObservable<T> {

    protected final ArrayList<T> mObservers = new ArrayList<T>();

    @Implementation
    public void registerObserver(T observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized(mObservers) {
            if (mObservers.contains(observer)) {
                throw new IllegalStateException("Observer " + observer + " is already registered.");
            }
            mObservers.add(observer);
        }
    }

    @Implementation
    public void unregisterObserver(T observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized(mObservers) {
            int index = mObservers.indexOf(observer);
            if (index == -1) {
                throw new IllegalStateException("Observer " + observer + " was not registered.");
            }
            mObservers.remove(index);
        }
    }

    @Implementation
    public void unregisterAll() {
        synchronized(mObservers) {
            mObservers.clear();
        }
    }
}
