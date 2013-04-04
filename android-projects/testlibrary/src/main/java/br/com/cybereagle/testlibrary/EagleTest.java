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

import com.google.inject.Module;
import org.mockito.MockitoAnnotations;
import org.robolectric.util.DatabaseConfig.UsingDatabaseMap;
import org.robolectric.util.SQLiteMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@UsingDatabaseMap(PersistentSQLiteMap.class)
public abstract class EagleTest {

    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        TestGuiceModule module = new TestGuiceModule();
        module.addAllBindings(getCustomBindings());
        TestGuiceModule.setUp(this, module, getProductionModule());
    }

    public void tearDown() throws Exception{
        TestGuiceModule.tearDown();
    }

    protected Map<Class<?>, Object> getCustomBindings(){
        return Collections.emptyMap();
    }

    protected Module getProductionModule(){
        return null;
    }
}
