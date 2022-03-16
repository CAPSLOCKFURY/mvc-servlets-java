package utils;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.WebMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public final class ClassUtils {

    private static final Logger logger = LogManager.getLogger();

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
