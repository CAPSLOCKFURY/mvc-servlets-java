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

    public static List<Class<?>> getClassesInPackage(String packageName){
        //TODO add recursive directories support
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL packageRoot = classLoader.getResource(packageName.replace('.', '/'));
        if(packageRoot == null){
            logger.debug("Package root is null");
            return Collections.emptyList();
        }
        File[] files = new File(packageRoot.getFile()).listFiles(
                (dir, name) -> name.endsWith(".class")
        );
        return Arrays.stream(files)
                .map(f -> f.getName().replaceAll(".class", ""))
                .map(f -> {
                    try {
                        return Class.forName(packageName.concat('.' + f));
                    } catch (ClassNotFoundException e) {
                        logger.error(e.getStackTrace());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static List<Class<? extends Command>> getAnnotatedCommandClassesInPackage(String packageName){
        List<Class<?>> classes = getClassesInPackage(packageName);
        List<Class<?>> filteredClasses = classes.stream()
                .filter(c -> Command.class.isAssignableFrom(c) && c.isAnnotationPresent(WebMapping.class))
                .collect(Collectors.toList());
        return (List<Class<? extends Command>>)(List<?>) filteredClasses;
    }

    private ClassUtils(){
        throw new UnsupportedOperationException();
    }
}
