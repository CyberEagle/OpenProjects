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

import br.com.cybereagle.testlibrary.shadow.*;
import com.google.inject.Module;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;
import org.robolectric.util.DatabaseConfig.UsingDatabaseMap;
import org.robolectric.util.SQLiteMap;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@UsingDatabaseMap(PersistentSQLiteMap.class)
@Config(shadows = {ShadowSQLiteQueryBuilder.class, ShadowObservable.class, ShadowContentObservable.class, ShadowDataSetObservable.class, ShadowAbstractCursor.class, ShadowSQLiteCursor.class, ShadowAsyncTaskLoader.class})
public abstract class EagleTest {

    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    public void tearDown() throws Exception {
        TestGuiceModule.tearDown();
    }

    protected Map<Class<?>, Object> getCustomBindings() {
        return Collections.emptyMap();
    }

    protected Module getProductionModule() {
        return null;
    }

    protected void clearDatabase() {
        File db = new File(EagleConstants.TEST_DB_FILE_NAME);
        if (db.exists()) {
            db.delete();
        }
    }
}
