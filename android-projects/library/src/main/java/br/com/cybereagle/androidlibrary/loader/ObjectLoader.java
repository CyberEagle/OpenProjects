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

import br.com.cybereagle.androidlibrary.loader.interfaces.CursorToObjectConverter;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Implementação que converte automaticamente Cursor em Objetos através de um conversor.
 */
public class ObjectLoader<T> extends CustomCursorLoader<LoadedObject<T>> {

    private LoadedObject<T> loadedObject;
    
    private CursorToObjectConverter<T> cursorToObjectConverter;
    
	private ObjectLoader(Context context, Uri uri) {
	    super(context, uri);
	}
	
	public static class Builder<T> {
		private Context context;
		
		private Uri uri;
	    private String[] projection;
	    private String selection;
	    private String[] selectionArgs;
	    private String sortOrder;
	    private CursorToObjectConverter<T> cursorToObjectConverter;
	    
	    public Builder(Context context, Uri uri, CursorToObjectConverter<T> cursorToObjectConverter){
	    	this.context = context;
	    	this.uri = uri;
	    	this.cursorToObjectConverter = cursorToObjectConverter;
	    }
	    
	    public ObjectLoader<T> build(){
	    	ObjectLoader<T> objectLoader = new ObjectLoader<T>(context, uri);
	    	objectLoader.uri = uri;
	    	objectLoader.projection = projection;
	    	objectLoader.selection = selection;
	    	objectLoader.selectionArgs = selectionArgs;
	    	objectLoader.sortOrder = sortOrder;
	    	objectLoader.cursorToObjectConverter = cursorToObjectConverter;
	    	return objectLoader;
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
	protected LoadedObject<T> loadObjectFromCursor(Cursor cursor) {
		LoadedObject<T> loadedObject = new LoadedObject<T>();
        loadedObject.setObject(this.cursorToObjectConverter.convert(cursor));
        loadedObject.setCursor(cursor);
        return loadedObject;
	}

	@Override
	protected void deliverCarrierAndCloseOldCursor(LoadedObject<T> loadedObject) {
		LoadedObject<T> oldLoadedObject = this.loadedObject;
        this.loadedObject = loadedObject;

        if (oldLoadedObject != null && oldLoadedObject != loadedObject && !oldLoadedObject.getCursor().isClosed()) {
            oldLoadedObject.getCursor().close();
        }
	}

	@Override
	protected boolean shouldLoad() {
		return this.loadedObject == null;
	}

	@Override
	protected boolean shouldDeliver() {
		return this.loadedObject != null;
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

