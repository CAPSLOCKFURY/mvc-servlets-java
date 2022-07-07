package scanner;

import java.util.Collection;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassSink {

    private final Queue<Set<Class<?>>> classes = new LinkedBlockingQueue<>();

    public Set<Class<?>> getClasses(){
        return classes.stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public Set<Class<?>> getClasses(Predicate<Class<?>> predicate){
        return classes.stream().flatMap(Collection::stream).filter(predicate).collect(Collectors.toSet());
    }

    public void add(Set<Class<?>> classList){
        classes.add(classList);
    }

}
