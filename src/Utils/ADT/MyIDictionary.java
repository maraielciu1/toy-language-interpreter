package Utils.ADT;

import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K,V> {
    void put(K key, V value);
    V lookUp(K key);
    boolean contains(K key);
    void update(K key, V value);
    void remove(K key);
    Iterable<K> getKeys();
    Map<K,V> getMap();

    void setContent(Map<K, V> newContent);

    MyIDictionary<K,V> deepCopy();

    Set<String> keySet();

}
