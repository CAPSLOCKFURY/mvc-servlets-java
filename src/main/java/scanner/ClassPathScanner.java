package scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ClassPathScanner {

    private static final Logger logger = LogManager.getLogger();

    private final ClassLoader classLoader;

    private URLClassLoader globalClassLoader;

    private URL[] urls;

    private final boolean threadedScan;

    private final int threadCount;

    private final boolean ignoreNoClassDef;

    private final boolean skipJars;

    public ClassPathScanner(){
        this(new Config());
    }

    private ClassPathScanner(Config config){
        classLoader = config.classLoader;
        threadedScan = config.threadedScan;
        threadCount = config.threadCount;
        ignoreNoClassDef = config.ignoreNoClassDef;
        skipJars = config.skipJars;
    }

    private void prepareGlobalClassLoader(){
        try {
            urls = enumerationToArray(classLoader.getResources(""));
            globalClassLoader = new URLClassLoader(urls, classLoader);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private URL[] enumerationToArray(Enumeration<URL> enumeration){
        List<URL> urls = new ArrayList<>();
        while (enumeration.hasMoreElements()){
            urls.add(enumeration.nextElement());
        }
        return urls.toArray(new URL[0]);
    }

    public Set<Class<?>> scan(String packageName){
        return scan(packageName, c -> true);
    }

    public Set<Class<?>> scan(String packageName, Predicate<Class<?>> predicate){
        packageName = packageName.replace('.', '/');
        Set<Class<?>> classes;
        prepareGlobalClassLoader();
        if(packageName.equals("")){
            classes = scanClasspath(predicate);
        } else {
            classes = scanPackage(packageName, predicate);
        }
        try {
            globalClassLoader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    private Set<Class<?>> scanPackage(String packageName, Predicate<Class<?>> predicate){
        URL url = classLoader.getResource(packageName);
        if(url != null) {
            return scanUrl(url, packageName.replace('/', '.')).stream().filter(predicate).collect(Collectors.toSet());
        } else {
            logger.error("Package not found for package name: {}", packageName);
            return Collections.emptySet();
        }
    }

    private Set<Class<?>> scanClasspath(Predicate<Class<?>> predicate) {
        if(!threadedScan) {
            return scanClasspathSync().getClasses(predicate);
        } else {
            return scanClasspathAsync().getClasses(predicate);
        }
    }

    private ClassSink scanClasspathSync(){
        ClassSink classSink = new ClassSink();
        for (URL url : urls) {
            if(skipJars && url.toString().startsWith("jar")){
                continue;
            }
            classSink.add(scanUrl(url, ""));
        }
        return classSink;
    }

    private ClassSink scanClasspathAsync(){
        ClassSink classSink = new ClassSink();
        ExecutorService executors = Executors.newFixedThreadPool(threadCount);
        for(URL url : urls){
            if(skipJars && url.toString().startsWith("jar")){
                continue;
            }
            executors.submit(() -> classSink.add(scanUrl(url, "")));
        }
        try {
            executors.shutdown();
            executors.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return classSink;
    }

    private Set<Class<?>> scanUrl(URL url, String packageName){
        Set<Class<?>> classes;
        if(url.toString().startsWith("jar")){
            classes = scanJar(url, packageName);
        } else {
            classes = scanFile(url, packageName);
        }
        return classes;
    }

    private Set<Class<?>> scanJar(URL url, String packageName){
        Set<Class<?>> classes = new HashSet<>();
        if(!url.toString().startsWith("jar")) {
            throw new IllegalArgumentException("Url is not jar file");
        }
        try(JarFile jarFile = new JarFile(url.getFile().substring(6, url.getFile().indexOf('!')));) {
            jarFile.stream()
                    .filter(e -> !e.toString().startsWith("META-INF"))
                    .filter(e -> e.toString().endsWith(".class"))
                    .map(e -> e.getName().replace('/', '.'))
                    .filter(e -> packageName.equals("") || e.startsWith(packageName))
                    .map(e -> e.replaceAll(".class$", ""))
                    .filter(e -> !e.endsWith("module-info"))
                    .forEach(s ->{
                        try {
                            classes.add(globalClassLoader.loadClass(s));
                        } catch (ClassNotFoundException e) {
                            logger.error("Class not found: {}", s);
                        } catch (NoClassDefFoundError e){
                            if(!ignoreNoClassDef){
                                logger.error("No class def found: {}", s);
                            }
                        }
                    });
            return classes;
        } catch (IOException e) {
            e.printStackTrace();
            return classes;
        }
    }

    private Set<Class<?>> scanFile(URL url, String packageName){
        Set<Class<?>> classes = new HashSet<>();
        try {
            Path start = Paths.get(url.toURI());
            Files.walk(start, Integer.MAX_VALUE)
                    .map(start::relativize)
                    .map(Path::toString)
                    .filter(p -> p.endsWith(".class"))
                    .map(p -> p.replace('/', '.').replace('\\', '.'))
                    .map(p -> p.replaceAll(".class$", ""))
                    .forEach(s -> {
                        try {
                            String className = packageName.equals("") ? s : packageName.concat('.' + s);
                            classes.add(globalClassLoader.loadClass(className));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (NoClassDefFoundError e) {
                            if(!ignoreNoClassDef) {
                                logger.error("No class def for: {}", s);
                            }
                        }
                    });
            return classes;
        } catch (URISyntaxException | IOException e){
            e.printStackTrace();
            return classes;
        }
    }

    public static class Config{

        private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        private boolean threadedScan = true;

        private int threadCount = 10;

        private boolean ignoreNoClassDef = true;

        private boolean skipJars = false;

        public Config classLoader(ClassLoader classLoader){
            this.classLoader = classLoader;
            return this;
        }

        public Config threadedScan(boolean b){
            threadedScan = b;
            return this;
        }

        public Config threadCount(int threadCount){
            this.threadCount = threadCount;
            return this;
        }

        public Config ignoreNoClassDef(boolean b){
            ignoreNoClassDef = b;
            return this;
        }

        public Config skipJars(boolean b){
            skipJars = b;
            return this;
        }

        public ClassPathScanner build(){
            return new ClassPathScanner(this);
        }

    }
}
