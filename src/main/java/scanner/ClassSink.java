package scanner;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassSink {

    private final Queue<List<Class<?>>> classes = new LinkedBlockingQueue<>();

    public List<Class<?>> getClasses(){
        return classes.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public List<Class<?>> getClasses(Predicate<Class<?>> predicate){
        return classes.stream().flatMap(Collection::stream).filter(predicate).collect(Collectors.toList());
    }

    public void add(List<Class<?>> classList){
        classes.add(classList);
    }

}
