package com.hang.annotation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


/**
 * @author zhanghang
 */
public final class AnnoManageUtil {

    public static List<Class<?>> getPackageController(String packageName, Class<? extends Annotation> annotation) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs = null;
        //获取当前目录下面的所有的子目录的url
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (dirs != null) {
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = null;
                    try {
                        filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    filePath = filePath.substring(1);
                    getFilePathClasses(packageName, filePath, classList, annotation);
                }
            }
        } else {
            throw new RuntimeException("package not found");
        }
        return classList;
    }

    private static void getFilePathClasses(String packageName, String filePath, List<Class<?>> classList,
                                           Class<? extends Annotation> annotation) throws IOException, ClassNotFoundException {
        Path dir = Paths.get(filePath);
        // 获得当前目录下的文件的stream流
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
        for (Path path : stream) {
            String fileName = String.valueOf(path.getFileName());
            String className = fileName.substring(0, fileName.length() - 6);
            Class<?> classes = Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className);
            // 判断该注解类型是不是所需要的类型
            if (Objects.nonNull(classes) && Objects.nonNull(classes.getAnnotation(annotation))) {
                classList.add(classes);
            }
        }
    }

    public static void getRequestMappingMethod(List<Class<?>> classList, Map<String, ExecutorBean> mapp) {
        for (Class classes : classList) {
            // 得到该类下面的所有方法
            Method[] methods = classes.getDeclaredMethods();
            for (Method method : methods) {
                // 得到该类下面的RequestMapping注解
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                if (null != requestMapping) {
                    ExecutorBean executorBean = new ExecutorBean();
                    try {
                        executorBean.setObject(classes.newInstance());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    executorBean.setMethod(method);
                    mapp.put(requestMapping.value(), executorBean);
                }
            }
        }
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Class<?>> classesList = null;
        classesList = AnnoManageUtil.getPackageController("com.hang.annotation", Controller.class);
        Map<String, ExecutorBean> map = new HashMap<>();
        AnnoManageUtil.getRequestMappingMethod(classesList, map);
        map.forEach((k, v) ->
                System.out.println(k)
        );
        ExecutorBean bean = map.get("/test1");
        try {
            bean.getMethod().invoke(bean.getObject());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
