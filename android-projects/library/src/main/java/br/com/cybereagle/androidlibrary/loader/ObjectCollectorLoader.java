package br.com.cybereagle.androidlibrary.loader;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.Loader;
import br.com.cybereagle.androidlibrary.loader.interfaces.CursorToObjectCollector;

public class ObjectCollectorLoader<T> extends CustomCursorLoader<LoadedObject<T>> {
	
	private OnLoadCompleteListenerCollector<LoadedObject<T>> onLoadCompleteListenerCollector;

	private LoadedObject<T> loadedObject;

	private CursorToObjectCollector<T> cursorToObjectCollector;
	
	private boolean loadedFirstTime = false;
	private boolean collectOnUIThread;

	private ObjectCollectorLoader(T object, Context context, Uri uri) {
		super(context, uri);
		this.collectOnUIThread = context instanceof Activity;
		this.loadedObject = new LoadedObject<T>();
		this.loadedObject.setObject(object);
	}

	public static class Builder<T> {
		
		private T object;
		private Context context;
		
		private Uri uri;
		private String[] projection;
		private String selection;
		private String[] selectionArgs;
		private String sortOrder;
		private CursorToObjectCollector<T> cursorToObjectCollector;

		public Builder(T object, Context context, Uri uri, CursorToObjectCollector<T> cursorToObjectCollector) {
			this.object = object;
			this.context = context;
			this.uri = uri;
			this.cursorToObjectCollector = cursorToObjectCollector;
		}

		public ObjectCollectorLoader<T> build() {
			ObjectCollectorLoader<T> objectCollectorLoader = new ObjectCollectorLoader<T>(object, context, uri);
			objectCollectorLoader.uri = uri;
			objectCollectorLoader.projection = projection;
			objectCollectorLoader.selection = selection;
			objectCollectorLoader.selectionArgs = selectionArgs;
			objectCollectorLoader.sortOrder = sortOrder;
			objectCollectorLoader.cursorToObjectCollector = cursorToObjectCollector;
			return objectCollectorLoader;
		}

		public Builder<T> setProjection(String[] projection) {
			this.projection = projection;
			return this;
		}

		public Builder<T> setSelection(String selection) {
			this.selection = selection;
			return this;
		}

		public Builder<T> setSelectionArgs(String[] selectionArgs) {
			this.selectionArgs = selectionArgs;
			return this;
		}

		public Builder<T> setSortOrder(String sortOrder) {
			this.sortOrder = sortOrder;
			return this;
		}

	}

	@Override
	public void registerListener(int id, Loader.OnLoadCompleteListener<LoadedObject<T>> listener) {
		this.onLoadCompleteListenerCollector = new OnLoadCompleteListenerCollector<LoadedObject<T>>(listener);
		super.registerListener(id, onLoadCompleteListenerCollector);
	}

	@Override
	public void unregisterListener(Loader.OnLoadCompleteListener<LoadedObject<T>> listener) {
		if(listener == onLoadCompleteListenerCollector.getOriginalOnLoadCompleteListener()){
			super.unregisterListener(onLoadCompleteListenerCollector);
		}
		else{
			super.unregisterListener(listener);
		}
	}

	@Override
	protected LoadedObject<T> loadObjectFromCursor(final Cursor cursor) {
		LoadedObject<T> loadedObject = new LoadedObject<T>(this.loadedObject.getObject(), cursor);
		if(!collectOnUIThread){
			cursorToObjectCollector.collect(loadedObject.getObject(), cursor);
		}
		
        return loadedObject;
	}

	@Override
	protected void deliverCarrierAndCloseOldCursor(LoadedObject<T> loadedObject) {
		if(collectOnUIThread){
			cursorToObjectCollector.collect(loadedObject.getObject(), loadedObject.getCursor());
		}
		
        Cursor oldCursor = this.loadedObject.getCursor();
        this.loadedObject.setCursor(loadedObject.getCursor());
        
        if(!loadedFirstTime){
        	loadedFirstTime = true;
        }

        if (oldCursor != null && oldCursor != this.loadedObject.getCursor() && !oldCursor.isClosed()) {
            oldCursor.close();
        }
	}

	@Override
	protected boolean shouldLoad() {
		return true;
	}

	@Override
	protected boolean shouldDeliver() {
		return this.loadedFirstTime;
	}

	@Override
	protected LoadedObject<T> getCursorCarrier() {
		return this.loadedObject;
	}

	@Override
	protected void cleanCursorCarrier() {
		this.loadedObject = null;
	}
}
