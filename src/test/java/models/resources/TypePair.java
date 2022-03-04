package models.resources;

import forms.resourses.Pair;

public class TypePair<K, V, T> extends Pair<K, V> {

    private final T type;

    public TypePair(K k, V v, T t) {
        super(k, v);
        type = t;
    }

    public T getType(){
        return type;
    }
}
