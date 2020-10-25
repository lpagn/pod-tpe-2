package models;

import java.io.Serializable;

public class Pair<K,V> implements Serializable {
    K key;
    V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)){
            return false;
        }

        Pair<K,V> p = (Pair<K,V>) obj;

        return p.key == this.key;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s;%s",key.toString(),value.toString());
    }

    @Override
    public int hashCode() {
        return key.hashCode()+value.hashCode();
    }
}
