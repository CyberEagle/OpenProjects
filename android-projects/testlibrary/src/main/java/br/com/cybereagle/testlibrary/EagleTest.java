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
