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
