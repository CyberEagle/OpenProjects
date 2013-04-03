package br.com.cybereagle.androidlibrary.util;

import java.util.List;

import android.database.Cursor;

public class CollectorUtils {

	private static final String REQUIRED_CONSTRUCTOR_MESSAGE = "O objeto deve possuir um construtor público sem argumentos";

	public static interface ObjectFiller<T>{
		void fill(T object, Cursor cursor);
	}
	
	public static <T> void collectFromCursor(List<T> objects, Cursor cursor, ObjectFiller<T> objectFiller, Class<T> clazz){
		int i=0;
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext(), i++) {
			T object = null;
			if(i < objects.size()){
				object = objects.get(i);
			}
			else{
				try {
					object = (T) clazz.newInstance();
					objects.add(object);
				} catch (InstantiationException e) {
					throw new IllegalArgumentException(REQUIRED_CONSTRUCTOR_MESSAGE);
				} catch (IllegalAccessException e) {
					throw new IllegalArgumentException(REQUIRED_CONSTRUCTOR_MESSAGE);
				}
			}
			objectFiller.fill(object, cursor);
		}
	}
	
}
