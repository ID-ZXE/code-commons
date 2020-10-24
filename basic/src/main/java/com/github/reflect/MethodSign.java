package com.github.reflect;

import java.lang.reflect.Method;

/**
 * @author hangs.zhang
 * @date 2020/05/04 21:13
 * *****************
 * function:
 */
public class MethodSign {

    public static void main(String[] args) throws Exception {
        System.out.println(MethodSign.getDesc(System.class));
        System.out.println(MethodSign.getDesc(String.class));
        System.out.println(MethodSign.getDesc(Integer.class));

        Method method = MethodSign.class.getDeclaredMethod("main", String[].class);
        Method method2 = MethodSign.class.getDeclaredMethod("testMethod", Integer.class, String.class);

        System.out.println(getDesc(method));
        System.out.println(getDesc(method2));
    }

    public String testMethod(Integer value, String key) {
        return "test";
    }

    public static String getDesc(final Method method) {
        final StringBuilder buf = new StringBuilder();
        buf.append("(");
        final Class<?>[] types = method.getParameterTypes();
        for (Class<?> type : types) {
            buf.append(getDesc(type));
        }
        buf.append(")");
        buf.append(getDesc(method.getReturnType()));
        return buf.toString();
    }

    public static String getDesc(final Class<?> returnType) {
        if (returnType.isPrimitive()) {
            return getPrimitiveLetter(returnType);
        }
        if (returnType.isArray()) {
            return "[" + getDesc(returnType.getComponentType());
        }
        return "L" + getType(returnType) + ";";
    }

    public static String getType(final Class<?> parameterType) {
        if (parameterType.isArray()) {
            return "[" + getDesc(parameterType.getComponentType());
        }
        if (!parameterType.isPrimitive()) {
            final String clsName = parameterType.getName();
            return clsName.replaceAll("\\.", "/");
        }
        return getPrimitiveLetter(parameterType);
    }

    public static String getPrimitiveLetter(final Class<?> type) {
        if (Integer.TYPE.equals(type)) {
            return "I";
        }
        if (Void.TYPE.equals(type)) {
            return "V";
        }
        if (Boolean.TYPE.equals(type)) {
            return "Z";
        }
        if (Character.TYPE.equals(type)) {
            return "C";
        }
        if (Byte.TYPE.equals(type)) {
            return "B";
        }
        if (Short.TYPE.equals(type)) {
            return "S";
        }
        if (Float.TYPE.equals(type)) {
            return "F";
        }
        if (Long.TYPE.equals(type)) {
            return "J";
        }
        if (Double.TYPE.equals(type)) {
            return "D";
        }
        throw new IllegalStateException("Type: " + type.getCanonicalName() + " is not a primitive type");
    }
}