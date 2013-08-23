package br.com.cybereagle.testlibrary;

import com.google.inject.Module;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.Collections;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
public class InjectedTest {

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
