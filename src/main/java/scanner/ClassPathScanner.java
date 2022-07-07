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

    private boolean threadedScan = true;

    private int threadCount = 10;

    public ClassPathScanner(){
        this(Thread.currentThread().getContextClassLoader());
    }

    public ClassPathScanner(ClassLoader classLoader) {
        this.classLoader = classLoader;
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

    public List<Class<?>> scan(String packageName){
        return scan(packageName, c -> true);
    }

    public List<Class<?>> scan(String packageName, Predicate<Class<?>> predicate){
        packageName = packageName.replace('.', '/');
        List<Class<?>> classes;
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

    private List<Class<?>> scanPackage(String packageName, Predicate<Class<?>> predicate){
        URL url = classLoader.getResource(packageName);
        if(url != null) {
            return scanUrl(url, packageName.replace('/', '.')).stream().filter(predicate).collect(Collectors.toList());
        } else {
            logger.error("Package not found for package name: {}", packageName);
            return Collections.emptyList();
        }
    }

    private List<Class<?>> scanClasspath(Predicate<Class<?>> predicate) {
        if(!threadedScan) {
            return scanClasspathSync().getClasses(predicate);
        } else {
            return scanClasspathAsync().getClasses(predicate);
        }
    }

    private ClassSink scanClasspathSync(){
        ClassSink classSink = new ClassSink();
        for (URL url : urls) {
            classSink.add(scanUrl(url, ""));
        }
        return classSink;
    }

    private ClassSink scanClasspathAsync(){
        ClassSink classSink = new ClassSink();
        ExecutorService executors = Executors.newFixedThreadPool(threadCount);
        for(URL url : urls){
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

    private List<Class<?>> scanUrl(URL url, String packageName){
        List<Class<?>> classes;
        if(url.toString().startsWith("jar")){
            classes = scanJar(url, packageName);
        } else {
            classes = scanFile(url, packageName);
        }
        return classes;
    }

    private List<Class<?>> scanJar(URL url, String packageName){
        List<Class<?>> classes = new LinkedList<>();
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
                            logger.error("Class not found: {}", e.getMessage());
                        } catch (NoClassDefFoundError ignored){

                        }
                    });
            return classes;
        } catch (IOException e) {
            e.printStackTrace();
            return classes;
        }
    }

    private List<Class<?>> scanFile(URL url, String packageName){
        List<Class<?>> classes = new LinkedList<>();
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
                            logger.error("No class def for: {}", s);
                        }
                    });
            return classes;
        } catch (URISyntaxException | IOException e){
            e.printStackTrace();
            return classes;
        }
    }

    public ClassPathScanner setThreadedScan(boolean threadedScan) {
        this.threadedScan = threadedScan;
        return this;
    }

    public ClassPathScanner setThreadCount(int threadCount) {
        this.threadCount = threadCount;
        return this;
    }
}
