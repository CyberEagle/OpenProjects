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

import android.content.ContentResolver;
import android.database.*;
import android.database.sqlite.SQLiteCursor;
import android.net.Uri;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.shadows.*;

import java.lang.ref.WeakReference;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Implements(SQLiteCursor.class)
public class ShadowSQLiteCursor extends org.robolectric.shadows.ShadowSQLiteCursor {

    @RealObject
    private AbstractCursor realCursor;

    protected ContentResolver mContentResolver;
    protected Uri mNotifyUri;
    protected ContentObserver mSelfObserver;
    final private Object mSelfObserverLock = new Object();
    protected boolean mSelfObserverRegistered;

    DataSetObservable mDataSetObservable = new DataSetObservable();
    ContentObservable mContentObservable = new ContentObservable();


    @Implementation
    public boolean requery() {
        if (mSelfObserver != null && !mSelfObserverRegistered) {
            mContentResolver.registerContentObserver(mNotifyUri, true, mSelfObserver);
            mSelfObserverRegistered = true;
        }
        mDataSetObservable.notifyChanged();
        return true;
    }

    @Implementation
    public void close() {
        mClosed = true;
        mContentObservable.unregisterAll();
        deactivateInternal();
    }

    @Implementation
    public void deactivate() {
        deactivateInternal();
    }

    @Implementation
    public void deactivateInternal() {
        if (mSelfObserver != null) {
            mContentResolver.unregisterContentObserver(mSelfObserver);
            mSelfObserverRegistered = false;
        }
        mDataSetObservable.notifyInvalidated();
    }

    @Implementation
    public void registerContentObserver(ContentObserver observer) {
        mContentObservable.registerObserver(observer);
    }

    @Implementation
    public void unregisterContentObserver(ContentObserver observer) {
        if (!mClosed) {
            mContentObservable.unregisterObserver(observer);
        }
    }

    @Implementation
    protected void notifyDataSetChange() {
        mDataSetObservable.notifyChanged();
    }

    @Implementation
    protected DataSetObservable getDataSetObservable() {
        return mDataSetObservable;
    }

    @Implementation
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    @Implementation
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    @Implementation
    public void setNotificationUri(ContentResolver cr, Uri notifyUri) {
        synchronized (mSelfObserverLock) {
            mNotifyUri = notifyUri;
            mContentResolver = cr;
            if (mSelfObserver != null) {
                mContentResolver.unregisterContentObserver(mSelfObserver);
            }
            mSelfObserver = new SelfContentObserver(this);
            mContentResolver.registerContentObserver(mNotifyUri, true, mSelfObserver);
            mSelfObserverRegistered = true;
        }
    }

    @Override
    @Implementation
    protected void finalize() {
        if (mSelfObserver != null && mSelfObserverRegistered == true) {
            mContentResolver.unregisterContentObserver(mSelfObserver);
        }
    }

    @Implementation
    protected void onChange(boolean selfChange) {
        synchronized (mSelfObserverLock) {
            mContentObservable.dispatchChange(selfChange);
            if (mNotifyUri != null && selfChange) {
                mContentResolver.notifyChange(mNotifyUri, mSelfObserver);
            }
        }
    }

    /**
     * Cursors use this class to track changes others make to their URI.
     */
    protected static class SelfContentObserver extends ContentObserver {
        WeakReference<ShadowSQLiteCursor> mCursor;

        public SelfContentObserver(ShadowSQLiteCursor cursor) {
            super(null);
            mCursor = new WeakReference<ShadowSQLiteCursor>(cursor);
        }

        @Override
        public boolean deliverSelfNotifications() {
            return false;
        }

        @Override
        public void onChange(boolean selfChange) {
            ShadowSQLiteCursor cursor = mCursor.get();
            if (cursor != null) {
                cursor.onChange(false);
            }
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            onChange(selfChange);
        }
    }
}
