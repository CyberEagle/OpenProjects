package br.com.cybereagle.commonlibrary.util;

import br.com.cybereagle.commonlibrary.exception.MethodNotFoundException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    public static Method getter(Field field)
            throws NoSuchMethodException {
        Class<?> clazz = field.getDeclaringClass();
        String adjustedName = getAjustedName(field);
        Class<?> fieldType = field.getType();
        if (fieldType.isAssignableFrom(boolean.class)
                || fieldType.isAssignableFrom(Boolean.class)) {
            return clazz.getMethod("is" + adjustedName);
        } else {
            return clazz.getMethod("get" + adjustedName);
        }
    }

    public static Method setter(Field field)
            throws NoSuchMethodException {
        String adjustedName = getAjustedName(field);
        Class<?> fieldType = field.getType();
        return field.getDeclaringClass().getMethod("set" + adjustedName, fieldType);
    }

    private static String getAjustedName(Field field) {
        return Character.toUpperCase(field.getName().charAt(0))
                + field.getName().substring(1);
    }

    public static void set(Field field, Object target,
                           Object value) throws IllegalArgumentException,
            InvocationTargetException {
        try {
            setter(field).invoke(target, value);
        } catch (IllegalAccessException e) {
            // It'll never happen
        } catch (NoSuchMethodException e) {
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
            try {
                field.set(target, value);
            } catch (IllegalAccessException e1) {
                // It'll never happen
            }
        }
    }

    public static void removeFinalModifier(Field field){
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch(NoSuchFieldException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setFinalStatic(Field field, Object newValue) {
        field.setAccessible(true);

        removeFinalModifier(field);

        try {
            field.set(null, newValue);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object get(Field field, Object target)
            throws IllegalArgumentException, InvocationTargetException {
        try {
            return getter(field).invoke(target);
        } catch (IllegalAccessException e) {
            // It'll never happen
            return null;
        } catch (NoSuchMethodException e) {
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
            try {
                return field.get(target);
            } catch (IllegalAccessException e1) {
                // It'll never happen
                return null;
            }
        }
    }

    public static List<Field> getInstanceVariables(Class<?> clazz) {
        List<Field> listFields = new ArrayList<Field>();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields) {
                if(!Modifier.isStatic(field.getModifiers())) {
                    listFields.add(field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return listFields;
    }

    public static Field getInstanceVariable(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            while (clazz != null) {
                Field[] fields = clazz.getDeclaredFields();
                for(Field field : fields) {
                    if(!Modifier.isStatic(field.getModifiers()) &&
                            field.getName().equals(name)) {
                        return field;
                    }
                }
                clazz = clazz.getSuperclass();
            }
            return null;
        }
    }

    public static Method getRestrictedMethod(Class<?> clazz, String name, Class<?>... parameterTypes){
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            while (clazz != null) {
                Method[] methods = clazz.getDeclaredMethods();
                for(Method method : methods) {
                    if(!Modifier.isStatic(method.getModifiers()) &&
                            method.getName().equals(name) && Arrays.equals(method.getParameterTypes(), parameterTypes)) {
                        return method;
                    }
                }
                clazz = clazz.getSuperclass();
            }
            return null;
        }
    }

    public static <T> T invokeRestrictedMethod(Object target, String name, Object[] parameters) throws InvocationTargetException {
        Class<?>[] parameterTypes = new Class[parameters.length];
        for(int i=0; i<parameters.length; i++){
            parameterTypes[i] = parameters[i].getClass();
        }
        return invokeRestrictedMethod(target, name, parameters, parameterTypes);
    }

    public static <T> T invokeRestrictedMethod(Object target, String name, Object[] parameters, Class<?>... parameterTypes) throws InvocationTargetException {
        Method restrictedMethod = getRestrictedMethod(target.getClass(), name, parameterTypes);

        if(restrictedMethod == null){
            throw new MethodNotFoundException(String.format("Method %s not found.", name));
        }

        if(!restrictedMethod.isAccessible()){
            restrictedMethod.setAccessible(true);
        }

        try {
            return (T) restrictedMethod.invoke(target, parameters);
        } catch (IllegalAccessException e) {
            // It'll never happen
            return null;
        }
    }

}
