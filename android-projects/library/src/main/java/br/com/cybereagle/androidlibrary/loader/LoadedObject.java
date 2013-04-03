package br.com.cybereagle.androidlibrary.loader;

import br.com.cybereagle.androidlibrary.loader.interfaces.CursorCarrier;
import android.database.Cursor;

public class LoadedObject<T> implements CursorCarrier {

	private T object;
	private Cursor cursor;
	
	public LoadedObject() {
		
	}
	public LoadedObject(T object, Cursor cursor) {
		this.object = object;
		this.cursor = cursor;
	}
	
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	public Cursor getCursor() {
		return cursor;
	}
	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}
}
