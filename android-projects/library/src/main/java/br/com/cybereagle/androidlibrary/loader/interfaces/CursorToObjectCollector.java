package br.com.cybereagle.androidlibrary.loader.interfaces;

import android.database.Cursor;

public interface CursorToObjectCollector<T> {
	
	void collect(T object, Cursor cursor);
	
}
