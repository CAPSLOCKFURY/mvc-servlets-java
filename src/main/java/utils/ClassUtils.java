package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ClassUtils {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Gets all classes in package and its subpackages recursively
     * @param packageName name of the package
     * @return List of found classes in package and subpackages
     */
    public static List<Class<?>> getClassesInPackage(String packageName){
        List<File> files = getFilesInPackage(packageName);
        List<Class<?>> classes = files.stream()
                .filter(File::isFile)
                .filter(f -> f.getName().endsWith(".class"))
                .map(f -> f.getName().replaceAll(".class", ""))
                .map(f -> {
                    try {
                        String className;
                        if(packageName.equals("")) {
                            className = packageName.concat(f);
                        } else {
                            className = packageName.concat('.' + f);
                        }
                        return Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        files.stream().filter(File::isDirectory)
                .forEach(f -> {
                    String dirName;
                    if(packageName.equals("")){
                        dirName = f.getName();
                    } else {
                        dirName = packageName.concat('.' + f.getName());
                    }
                    classes.addAll(getClassesInPackage(dirName));
                });
        return classes;
    }

    public static List<Class<?>> getClassesInPackage(String packageName, Predicate<Class<?>> predicate){
        List<Class<?>> classes = getClassesInPackage(packageName);
        return classes.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Finds all files in given package
     * @param packageName name of package in which files will be searched
     * @return List of files found in package
     */
    private static List<File> getFilesInPackage(String packageName){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL packageRoot = classLoader.getResource(packageName.replace('.', '/'));
        if(packageRoot == null){
            logger.debug("Package root is null");
            return Collections.emptyList();
        }
        File[] files = new File(packageRoot.getFile()).listFiles();
        if(files == null){
            throw new IllegalArgumentException("There are no files in given package");
        }
        return new ArrayList<>(Arrays.asList(files));
    }

    private ClassUtils(){
        throw new UnsupportedOperationException();
    }
}
