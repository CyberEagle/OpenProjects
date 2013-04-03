package br.com.cybereagle.androidlibrary.loader.interfaces;

import android.database.Cursor;

public interface CursorToObjectConverter<T> {

	T convert(Cursor cursor);
}
