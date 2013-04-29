/*
 * Copyright 2013 Cyber Eagle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package br.com.cybereagle.androidlibrary.loader;

import br.com.cybereagle.androidlibrary.loader.interfaces.CursorCarrier;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Implementação que converte automaticamente Cursor em Objetos através de um conversor.
 */
public abstract class CustomCursorLoader<T extends CursorCarrier> extends AsyncTaskLoader<T> {
    protected final ForceLoadContentObserver observer;

    protected Uri uri;
    protected String[] projection;
    protected String selection;
    protected String[] selectionArgs;
    protected String sortOrder;
    
	protected CustomCursorLoader(Context context, Uri uri) {
	    super(context);
	    this.observer = new ForceLoadContentObserver();
	    this.uri = uri;
	}

	/* Runs on a worker thread */
    @Override
    public T loadInBackground() {
        Cursor cursor = getContext().getContentResolver().query(this.uri, this.projection, this.selection,
        		this.selectionArgs, this.sortOrder);
        if (cursor != null) {
            // Ensure the cursor window is filled
            cursor.getCount();
            registerContentObserver(cursor, this.observer);
        }
        return loadObjectFromCursor(cursor);
    }

    protected abstract T loadObjectFromCursor(Cursor cursor);

	/**
     * Registers an observer to get notifications from the content provider
     * when the cursor needs to be refreshed.
     */
    void registerContentObserver(Cursor cursor, ContentObserver observer) {
        cursor.registerContentObserver(this.observer);
    }

    /* Runs on the UI thread */
    @Override
    public void deliverResult(T cursorCarrier) {
        if (isReset()) {
            // An async query came in while the loader is stopped
            if (cursorCarrier != null) {
                cursorCarrier.getCursor().close();
            }
            return;
        }
        deliverCarrierAndCloseOldCursor(cursorCarrier);

        if (isStarted()) {
            super.deliverResult(cursorCarrier);
        }
    }

    protected abstract void deliverCarrierAndCloseOldCursor(T cursorCarrier);

	/**
     * Starts an asynchronous load of the contacts list data. When the result is ready the callbacks
     * will be called on the UI thread. If a previous load has been completed and is still valid
     * the result may be passed to the callbacks immediately.
     *
     * Must be called from the UI thread
     */
    @Override
    protected void onStartLoading() {
    	T cursorCarrier = getCursorCarrier();
        if (shouldDeliver()) {
            deliverResult(cursorCarrier);
        }
        if (takeContentChanged() || shouldLoad()) {
            forceLoad();
        }
    }

    protected abstract boolean shouldLoad();

	protected abstract boolean shouldDeliver();

	protected abstract T getCursorCarrier();

	/**
     * Must be called from the UI thread
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public void onCanceled(T cursorCarrier) {
        if (cursorCarrier != null && !cursorCarrier.getCursor().isClosed()) {
            cursorCarrier.getCursor().close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        
        // Ensure the loader is stopped
        onStopLoading();

        CursorCarrier cursorCarrier = getCursorCarrier();
        
        if (cursorCarrier != null && !cursorCarrier.getCursor().isClosed()) {
        	cursorCarrier.getCursor().close();
        }
        cleanCursorCarrier();
    }

	protected abstract void cleanCursorCarrier();

}

