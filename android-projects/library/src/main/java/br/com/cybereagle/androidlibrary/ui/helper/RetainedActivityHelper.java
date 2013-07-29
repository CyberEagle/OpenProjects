package br.com.cybereagle.androidlibrary.ui.helper;

import android.os.Bundle;
import android.util.Log;
import br.com.cybereagle.androidlibrary.annotation.Retained;
import br.com.cybereagle.androidlibrary.ui.interfaces.RetainedActivity;
import br.com.cybereagle.commonlibrary.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RetainedActivityHelper {

    private RetainedActivity retainedActivity;

    private String activityIdentifier;

    public RetainedActivityHelper(RetainedActivity retainedActivity) {
        this.retainedActivity = retainedActivity;
    }

    /**
     * The Activity must call this method in its onCreate(Bundle savedInstanceState)
     * @return
     */
    public void onCreate(Bundle savedInstanceState) {
        retainedActivity.beforeCreate(savedInstanceState);
        retainedActivity.callSuperOnCreate(savedInstanceState);

        retainedActivity.initializeUnretainedInstanceFields(savedInstanceState);
        @SuppressWarnings("unchecked")
        final Map<String, Object> retainedMap = (Map<String, Object>) retainedActivity.getLastCustomNonConfigurationInstance();
        if (retainedMap == null) {
            retainedActivity.initializeRetainedInstanceFields(savedInstanceState);
        } else {
            reinitializeInstanceFields(retainedMap);
        }
        retainedActivity.createView(savedInstanceState);
        retainedActivity.afterCreateView(savedInstanceState);
    }

    /**
     * The Activity must call this method in its onRetainCustomNonConfigurationInstance().
     * @return
     */
    public Object onRetainCustomNonConfigurationInstance() {
        Map<String, Object> retainedMap = new HashMap<String, Object>();
        Class<?> clazz = retainedActivity.getClass();
        List<Field> fields = ReflectionUtils.getInstanceVariables(clazz);
        for (Field field : fields) {
            if (field.getAnnotation(Retained.class) != null) {
                try {
                    retainedMap.put(field.getName(), ReflectionUtils.get(field, retainedActivity));
                } catch (InvocationTargetException e) {
                    logException(e);
                }
            }
        }
        return retainedMap;
    }

    protected void reinitializeInstanceFields(Map<String, Object> retainedMap) {
        Class<?> clazz = retainedActivity.getClass();
        List<Field> fields = ReflectionUtils.getInstanceVariables(clazz);
        for (Field field : fields) {
            if (field.getAnnotation(Retained.class) != null) {
                try {
                    ReflectionUtils.set(field, retainedActivity, retainedMap.get(field.getName()));
                } catch (InvocationTargetException e) {
                    logException(e);
                }
            }
        }
    }

    protected void logException(Exception e) {
        Log.d(getActivityIdentifier(), e.getLocalizedMessage());
    }

    private String getActivityIdentifier() {
        if (this.activityIdentifier != null) {
            return this.activityIdentifier;
        }
        String className = retainedActivity.getClass().getSimpleName();
        StringBuilder activityIdentifier = new StringBuilder();

        String[] subNames = className.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
        for (int i = 0; i < subNames.length; i++) {
            activityIdentifier.append((i == 0 ? "" : "_") + subNames[i]);
        }
        this.activityIdentifier = activityIdentifier.toString().toUpperCase(Locale.US);
        return this.activityIdentifier;
    }
}
