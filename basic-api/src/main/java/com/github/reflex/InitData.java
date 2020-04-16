package com.github.reflex;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author hangs.zhang
 * 初始化文件数据的工具
 */
public class InitData {

    private static final ConcurrentMap<Class, Object> map = new ConcurrentHashMap<Class, Object>();

    /**
     * 构造方法传入packageName
     * 加载指定包下的所有数据对象
     * 将dir传入数据对象，创建数据对象
     * 将数据对象放入CurrentHashMap中
     *
     * @param packageName
     */
    public InitData(String packageName) {
        try {
            String dir = "dir";
            List<String> clsNames = getClassName(packageName, false);
            for (String className : clsNames) {
                System.out.println(className);
                Class cls = Class.forName(className);
                newInstance(cls, dir);
            }
        } catch (Exception e) {
            System.out.println("InitData all error!");
        }
    }

    /**
     * 追加指定的class
     *
     * @param path
     * @param classes
     */
    public static void bachNewInstance(String path, Class... classes) {
        if (StringUtils.isBlank(path) || classes.length <= 0) {
            return;
        }
        for (Class cls : classes) {
            newInstance(cls, path);
        }
    }

    public static <T> void newInstance(Class<T> type, String param) {
        Object ob = map.get(type);
        try {
            if (ob == null) {
                // 获取非public构造器
                Constructor constructor = type.getDeclaredConstructor(String.class);
                constructor.setAccessible(true);
                ob = constructor.newInstance(param);
                map.put(type, ob);
            }
        } catch (Exception e) {
            System.out.println("InitData newInstance error");
        }
    }

    public static <T> T getInstance(Class<T> type) {
        return (T) map.get(type);
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName  包名
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    public static List<String> getClassName(String packageName, boolean childPackage) {
        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            // 返回协议中的字符串对象
            String type = url.getProtocol();
            if (type.equals("file")) {
                fileNames = getClassNameByFile(url.getPath(), null, childPackage);
            } else if (type.equals("jar")) {
                fileNames = getClassNameByJar(url.getPath(), childPackage);
            }
        } else {
            fileNames = getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, childPackage);
        }
        List<String> result = Lists.newArrayList();
        for (String name : fileNames) {
            if (!name.contains("$")) {
                result.add(name);
            }
        }
        Collections.sort(result, Comparator.naturalOrder());
        return result;
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath     文件路径
     * @param className    类名集合
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String filePath, List<String> className, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                if (childPackage) {
                    myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage));
                }
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    // indexOf("//classes")定位到c
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                }
            }
        }
        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     *
     * @param jarPath      jar文件路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJar(String jarPath, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        String[] jarInfo = jarPath.split("!");
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        String packagePath = jarInfo[1].substring(1);
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    if (childPackage) {
                        if (entryName.startsWith(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    } else {
                        int index = entryName.lastIndexOf("/");
                        String myPackagePath;
                        if (index != -1) {
                            myPackagePath = entryName.substring(0, index);
                        } else {
                            myPackagePath = entryName;
                        }
                        if (myPackagePath.equals(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getClassNameByJar error");
        }
        return myClassName;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     *
     * @param urls         URL集合
     * @param packagePath  包路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        if (urls != null) {
            for (int i = 0; i < urls.length; i++) {
                URL url = urls[i];
                String urlPath = url.getPath();
                // 不必搜索classes文件夹
                if (urlPath.endsWith("classes/")) {
                    continue;
                }
                String jarPath = urlPath + "!/" + packagePath;
                myClassName.addAll(getClassNameByJar(jarPath, childPackage));
            }
        }
        return myClassName;
    }
}
