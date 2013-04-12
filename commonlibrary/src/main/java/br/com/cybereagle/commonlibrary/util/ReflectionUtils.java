package br.com.cybereagle.commonlibrary.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

    public static Method getter(Field field)
            throws NoSuchMethodException {
        Class<?> clazz = field.getDeclaringClass();
        String adjustedName = getAjustedName(field);
        Class<?> fieldType = field.getType();
        // Verifica se o campo é boolean
        if (fieldType.isAssignableFrom(boolean.class)
                || fieldType.isAssignableFrom(Boolean.class)) {
            // Retorna o método na forma isNomeCampo
            return clazz.getMethod("is" + adjustedName);
        } else {
            // Retorna o método na forma getNomeCampo
            return clazz.getMethod("get" + adjustedName);
        }
    }

    public static Method setter(Field field)
            throws NoSuchMethodException {
        String adjustedName = getAjustedName(field);
        Class<?> fieldType = field.getType();
        // Retorna o método na forma setNomeCampos que receba como parâmetro
        // um valor com o tipo do campo
        return field.getDeclaringClass().getMethod("set" + adjustedName, fieldType);
    }

    private static String getAjustedName(Field field) {
        // Passa a primeira letra do nome do campo para UpperCase
        return Character.toUpperCase(field.getName().charAt(0))
                + field.getName().substring(1);
    }

    public static void set(Field field, Object target,
                           Object value) throws IllegalArgumentException,
            InvocationTargetException {
        try {
            setter(field).invoke(target, value);
        } catch (IllegalAccessException e) {
            // Nunca acontecerá, pois o método é público
        } catch (NoSuchMethodException e) {
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
            try {
                field.set(target, value);
            } catch (IllegalAccessException e1) {
                // Nunca acontecerá, pois o campo está acessível
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
            // Nunca acontecerá, pois o método é público
            return null;
        } catch (NoSuchMethodException e) {
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
            try {
                return field.get(target);
            } catch (IllegalAccessException e1) {
                // Nunca acontecerá, pois o campo está acessível
                return null;
            }
        }
    }

    /**
     * Retorna um array das variáveis de instância de uma classe específica.<br>
     * Uma variável de instância é definida como um campo não static declarado ou
     * herdado por uma classe.
     *
     * @return java.lang.Field[]
     * @param clazz java.lang.Class
     */
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

    /**
     * Retorna um array das variáveis de instância de uma classe específica.<br>
     * Uma variável de instância é definida como um campo não static declarado ou
     * herdado por uma classe.
     *
     * @return java.lang.Field[]
     * @param clazz java.lang.Class
     */
    public static Field getInstanceVariable(Class<?> clazz, String name) {
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
