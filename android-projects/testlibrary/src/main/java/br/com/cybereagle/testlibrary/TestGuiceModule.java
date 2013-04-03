package br.com.cybereagle.testlibrary;

import android.app.Application;
import android.util.Log;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.robolectric.Robolectric;
import roboguice.RoboGuice;
import roboguice.config.DefaultRoboModule;
import roboguice.inject.RoboInjector;
import roboguice.util.Ln;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestGuiceModule extends AbstractModule {

    private Map<Class<?>, Object> bindings;

    public TestGuiceModule() {
        bindings = new HashMap<Class<?>, Object>();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void configure() {
        Set<Map.Entry<Class<?>, Object>> entries = bindings.entrySet();
        for (Map.Entry<Class<?>, Object> entry : entries) {
            bind((Class<Object>) entry.getKey()).toInstance(entry.getValue());
        }
    }

    public void addBinding(Class<?> type, Object object) {
        bindings.put(type, object);
    }

    public void addAllBindings(Map<Class<?>, Object> bindings){
        this.bindings.putAll(bindings);
    }

    public static void setUp(Object testObject, TestGuiceModule module, Module productionModule) {
        Module roboGuiceModule = RoboGuice.newDefaultRoboModule(Robolectric.application);
        Module testModule = productionModule != null ? Modules.override(roboGuiceModule, productionModule, new RobolectricModule()).with(module) : Modules.override(roboGuiceModule, new RobolectricModule()).with(module);
        RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, testModule);
        RoboInjector injector = RoboGuice.getInjector(Robolectric.application);
        injector.injectMembers(testObject);
    }

    public static void tearDown() {
        RoboGuice.util.reset();
        Application app = Robolectric.application;
        DefaultRoboModule defaultModule = RoboGuice.newDefaultRoboModule(app);
        RoboGuice.setBaseApplicationInjector(app, RoboGuice.DEFAULT_STAGE, defaultModule);
    }

    static class RobolectricModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Ln.BaseConfig.class).toInstance(new RobolectricLoggerConfig());
        }
    }

    static class RobolectricLoggerConfig extends Ln.BaseConfig {
        public RobolectricLoggerConfig() {
            super();
            this.packageName = "robo";
            this.minimumLogLevel = Log.VERBOSE;
            this.scope = "ROBO";
        }
    }

}
