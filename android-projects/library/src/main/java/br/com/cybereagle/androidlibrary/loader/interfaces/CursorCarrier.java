package br.com.cybereagle.androidlibrary.loader.interfaces;

import android.database.Cursor;

public interface CursorCarrier {

	Cursor getCursor();
	void setCursor(Cursor cursor);
}
