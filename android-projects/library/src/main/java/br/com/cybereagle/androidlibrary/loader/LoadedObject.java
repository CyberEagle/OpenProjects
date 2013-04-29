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
