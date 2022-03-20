package utils;

import commands.base.Command;
import commands.base.WebMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ClassUtils {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Finds all classes in given package and it subpackages
     * @param packageName name of package in which classes will be searched
     * @return List of classes found in package and it subpackages
     */
    public static List<Class<?>> getClassesInPackage(String packageName){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL packageRoot = classLoader.getResource(packageName.replace('.', '/'));
        if(packageRoot == null){
            logger.debug("Package root is null");
            return Collections.emptyList();
        }
        File[] files = new File(packageRoot.getFile()).listFiles();
        return getClassFiles(files, packageName);
    }

    /**
     * @param packageName name of package in which classes will be searched
     * @return all commands that implements {@link Command} and hava {@link WebMapping} annotation
     */
    public static List<Class<Command>> getAnnotatedCommandClassesInPackage(String packageName){
        List<Class<?>> classes = getClassesInPackage(packageName);
        List<Class<?>> filteredClasses = classes.stream()
                .filter(c -> Command.class.isAssignableFrom(c) && c.isAnnotationPresent(WebMapping.class))
                .collect(Collectors.toList());
        return (List<Class<Command>>)(List<?>) filteredClasses;
    }

    private static List<Class<?>> getClassFiles(File[] files, String packageName){
        List<Class<?>> classes = Arrays.stream(files)
                .filter(File::isFile)
                .map(f -> f.getName().replaceAll(".class", ""))
                .map(f -> {
                    try {
                        return Class.forName(packageName.concat('.' + f));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Arrays.stream(files).filter(File::isDirectory).forEach(f -> {
            classes.addAll(getClassFiles(f.listFiles(), packageName.concat("." + f.getName())));
        });
        return classes;
    }

    private ClassUtils(){
        throw new UnsupportedOperationException();
    }
}
