package com.github.annotation;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
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
 * @author plum-wine
 */
public final class AnnoManageUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static List<Class<?>> getPackageController(String packageName, Class<? extends Annotation> annotation) throws IOException, ClassNotFoundException {
        Preconditions.checkArgument(StringUtils.isNotBlank(packageName), "package不能为空");
        List<Class<?>> classList = new ArrayList<>();
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs = null;
        // 获取当前目录下面的所有的子目录的url
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        } catch (IOException e) {
            LOGGER.error("io error", e);
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
        for (Class<?> classes : classList) {
            // 得到该类下面的所有方法
            Method[] methods = classes.getDeclaredMethods();
            for (Method method : methods) {
                // 得到该类下面的RequestMapping注解
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                if (null != requestMapping) {
                    ExecutorBean executorBean = new ExecutorBean();
                    try {
                        executorBean.setObject(classes.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    executorBean.setMethod(method);
                    mapp.put(requestMapping.value(), executorBean);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Class<?>> classesList;
        classesList = AnnoManageUtil.getPackageController("com.github.annotation", Controller.class);
        Map<String, ExecutorBean> map = new HashMap<>();
        AnnoManageUtil.getRequestMappingMethod(classesList, map);
        map.forEach((k, v) ->
                LOGGER.info(k)
        );
        ExecutorBean bean = map.get("/test1");
        try {
            bean.getMethod().invoke(bean.getObject());
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("error", e);
        }
    }

}
