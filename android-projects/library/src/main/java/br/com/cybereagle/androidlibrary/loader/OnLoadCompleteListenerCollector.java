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

import java.lang.reflect.Field;

import android.support.v4.content.Loader;
import android.support.v4.content.Loader.OnLoadCompleteListener;
import android.util.Log;

/**
 * Classe que usada para hackear o comportamento padrão do Loader para que se possa 
 * transformá-lo em um coletor de dados.
 * @author fernandocamargo
 *
 * @param <T>
 */
public class OnLoadCompleteListenerCollector<T> implements Loader.OnLoadCompleteListener<T>{

	private Loader.OnLoadCompleteListener<T> onLoadCompleteListener;

	private Field haveDataField;
	
	public OnLoadCompleteListenerCollector(OnLoadCompleteListener<T> onLoadCompleteListener) {
		this.onLoadCompleteListener = onLoadCompleteListener;
	}

	@Override
	public void onLoadComplete(Loader<T> loader, T data) {
		onLoadCompleteListener.onLoadComplete(loader, data);
		try {
			if(haveDataField == null){
				Class<?> clazz = onLoadCompleteListener.getClass();
				haveDataField = clazz.getDeclaredField("mHaveData");
			}
			if(!haveDataField.isAccessible()){
				haveDataField.setAccessible(true);
			}
			haveDataField.setBoolean(onLoadCompleteListener, false);
		} catch (NoSuchFieldException e) {
			Log.e(getClass().getSimpleName(), "mHaveData não foi encontrado!");
		} catch (IllegalArgumentException e) {
			Log.e(getClass().getSimpleName(), "mHaveData não aceitou o valor boolean!");
		} catch (IllegalAccessException e) {
			
		}
	}

	public Loader.OnLoadCompleteListener<T> getOriginalOnLoadCompleteListener() {
		return onLoadCompleteListener;
	}

}
