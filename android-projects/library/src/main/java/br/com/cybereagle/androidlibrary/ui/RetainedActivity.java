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

package br.com.cybereagle.androidlibrary.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import br.com.cybereagle.androidlibrary.annotation.Retained;
import br.com.cybereagle.commonlibrary.util.ReflectionUtils;
import roboguice.inject.InjectView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class RetainedActivity extends RoboActionBarActivity {

	protected String activityIdentifier;

	@InjectView(android.R.id.content)
	protected View contentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        beforeCreate(savedInstanceState);
		super.onCreate(savedInstanceState);

		initializeUnretainedInstanceFields(savedInstanceState);
		@SuppressWarnings("unchecked")
		final Map<String, Object> retainedMap = (Map<String, Object>) getLastCustomNonConfigurationInstance();
		if (retainedMap == null) {
			initializeRetainedInstanceFields(savedInstanceState);
		} else {
			reinitializeInstanceFields(retainedMap);
		}
		createView(savedInstanceState);
        afterCreateView(savedInstanceState);
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		Map<String, Object> retainedMap = new HashMap<String, Object>();
		Class<? extends RetainedActivity> clazz = this.getClass();
		List<Field> fields = ReflectionUtils.getInstanceVariables(clazz);
		for (Field field : fields) {
			if (field.getAnnotation(Retained.class) != null) {
				try {
					retainedMap.put(field.getName(), ReflectionUtils.get(field, this));
				} catch (InvocationTargetException e) {
					logException(e);
				}
			}
		}
		return retainedMap;
	}

	protected void reinitializeInstanceFields(Map<String, Object> retainedMap) {
		Class<? extends RetainedActivity> clazz = this.getClass();
		List<Field> fields = ReflectionUtils.getInstanceVariables(clazz);
		for (Field field : fields) {
			if (field.getAnnotation(Retained.class) != null) {
				try {
					ReflectionUtils.set(field, this, retainedMap.get(field.getName()));
				} catch (InvocationTargetException e) {
					logException(e);
				}
			}
		}
	}

	protected void logException(Exception e) {
		Log.d(getActivityIdentifier(), e.getLocalizedMessage());
	}

    protected void beforeCreate(Bundle savedInstanceState){

    }

    protected void initializeUnretainedInstanceFields(Bundle savedInstanceState) {

    }

	protected void initializeRetainedInstanceFields(Bundle savedInstanceState){

    }

    protected void afterCreateView(Bundle savedInstanceState){

    }

    protected abstract void createView(Bundle savedInstanceState);

	protected String getActivityIdentifier() {
		if (this.activityIdentifier != null) {
			return this.activityIdentifier;
		}
		String className = this.getClass().getSimpleName();
		StringBuilder activityIdentifier = new StringBuilder();

		String[] subNames = className.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
		for (int i = 0; i < subNames.length; i++) {
			activityIdentifier.append((i == 0 ? "" : "_") + subNames[i]);
		}
		this.activityIdentifier = activityIdentifier.toString().toUpperCase(Locale.US);
		return this.activityIdentifier;
	}
}
