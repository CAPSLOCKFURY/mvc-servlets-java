package scanner;

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

    private ClassLoader classLoader;

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
        prepareGlobalClassLoader();
        long start = System.currentTimeMillis();
        if(packageName.equals("")){
            ClassSink classSink = new ClassSink();
            if(!threadedScan) {
                for (URL url : urls) {
                    classSink.add(scanUrl(url, packageName));
                }
            } else {
                ExecutorService executors = Executors.newFixedThreadPool(threadCount);
                for(URL url : urls){
                    String finalPackageName = packageName;
                    executors.submit(() -> classSink.add(scanUrl(url, finalPackageName)));
                }
                try {
                    executors.shutdown();
                    executors.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            return classSink.getClasses(predicate);
        } else {
            URL url = classLoader.getResource(packageName);
            if(url != null) {
                return scanUrl(url, packageName.replace('/', '.')).stream().filter(predicate).collect(Collectors.toList());
            } else {
                System.err.println("Null url for: " + packageName);
            }
        }
        try {
            globalClassLoader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<Class<?>> scanUrl(URL url, String parentPackage){
        List<Class<?>> classes = new LinkedList<>();
        if(url.toString().startsWith("jar")){
            try {
                JarFile jarFile = new JarFile(url.getFile().substring(6, url.getFile().indexOf('!')));
                jarFile.stream()
                        .filter(e -> !e.toString().startsWith("META-INF"))
                        .filter(e -> e.toString().endsWith(".class"))
                        .map(e -> e.getName().replace('/', '.'))
                        .filter(e -> parentPackage.equals("") || e.startsWith(parentPackage))
                        .map(e -> e.replaceAll(".class$", ""))
                        .filter(e -> !e.endsWith("module-info"))
                        .forEach(s ->{
                            try {
                                classes.add(globalClassLoader.loadClass(s));
                            } catch (ClassNotFoundException e) {
                                System.err.println("Class not found: " + e.getMessage());
                            } catch (NoClassDefFoundError ignored){

                            }
                        });
                jarFile.close();
            } catch (IOException e) {
                e.printStackTrace();
                return classes;
            }
        } else {
            try {
                Path start = Paths.get(url.toURI());
                Files.walk(start, Integer.MAX_VALUE)
                        .map(start::relativize)
                        .map(Path::toString)
                        .filter(p -> p.endsWith(".class"))
                        .map(p -> p.replace('/', '.').replace('\\', '.'))
                        .map(p -> p.replaceAll(".class$", ""))
                        .forEach(s ->{
                            try {
                                String className = parentPackage.equals("") ? s : parentPackage.concat('.' + s);
                                classes.add(globalClassLoader.loadClass(className));
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (NoClassDefFoundError e){
                                System.err.println("No class def for: " + s);
                            }
                        });
            } catch (IOException | URISyntaxException e){
                e.printStackTrace();
                return classes;
            }
        }
        return classes;
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
