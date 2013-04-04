/*
 * Copyright 2013 Cyber Eagle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.cybereagle.testlibrary;

import java.io.File;
import java.lang.reflect.Method;

import br.com.cybereagle.testlibrary.shadow.*;
import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricContext;
import org.robolectric.RobolectricTestRunner;

import android.os.Build;

public class EagleTestRunner extends RobolectricTestRunner {

	private static final int SDK_INT = Build.VERSION.SDK_INT;

	public EagleTestRunner(Class<?> testClass) throws InitializationError {
		super(RobolectricContext.bootstrap(EagleTestRunner.class, testClass, new RobolectricContext.Factory() {
			@Override
			public RobolectricContext create() {
				return new RobolectricContext() {

					@Override
					protected AndroidManifest createAppManifest() {
						return new AndroidManifest(new File("./application").exists() ? new File("./application")
								: new File("./"));
					}

				};
			}
		}));

	}

    private void clearDatabase() {
        File db = new File(EagleConstants.TEST_DB_FILE_NAME);
        if(db.exists()){
            db.delete();
        }
    }

    @Override
    protected void bindShadowClasses() {
        Robolectric.bindShadowClass(ShadowSQLiteQueryBuilder.class);
        Robolectric.bindShadowClass(ShadowObservable.class);
        Robolectric.bindShadowClass(ShadowContentObservable.class);
        Robolectric.bindShadowClass(ShadowDataSetObservable.class);
        Robolectric.bindShadowClass(ShadowAbstractCursor.class);
        Robolectric.bindShadowClass(ShadowSQLiteCursor.class);

        Robolectric.bindShadowClass(ShadowAsyncTaskLoader.class);
    }

	@Override
	public void beforeTest(final Method method) {
		final int targetSdkVersion = getRobolectricContext().getAppManifest().getSdkVersion();
		Robolectric.Reflection.setFinalStaticField(Build.VERSION.class, "SDK_INT", targetSdkVersion);
        clearDatabase();
	}

	@Override
	public void afterTest(final Method method) {
		resetStaticState();
	}

	@Override
	public void resetStaticState() {
		super.resetStaticState();
		Robolectric.Reflection.setFinalStaticField(Build.VERSION.class, "SDK_INT", SDK_INT);
	}



}