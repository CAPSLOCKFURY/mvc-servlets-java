package models.resources;

import forms.resourses.Pair;

public class TypePair<K, V, T> extends Pair<K, V> {

    private final Class<T> type;

    public TypePair(K k, V v, Class<T> t) {
        super(k, v);
        type = t;
    }

    public Class<T> getType(){
        return type;
    }
}
