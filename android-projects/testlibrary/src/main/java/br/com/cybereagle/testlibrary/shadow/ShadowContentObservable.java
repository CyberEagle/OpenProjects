/*
 * Copyright 2013 Cyber Eagle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
