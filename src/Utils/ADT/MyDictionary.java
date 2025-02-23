package Utils.ADT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K,V> implements MyIDictionary<K,V>{
    private Map<K,V> table;
    public MyDictionary(){
        table = new HashMap<>();
    }
    @Override
    public void put(K key, V value) {
        table.put(key, value);
    }

    @Override
    public V lookUp(K key) {
        return table.get(key);
    }

    @Override
    public boolean contains(K key) {
        return table.containsKey(key);
    }

    @Override
    public String toString(){
        return table.toString();
    }

    @Override
    public void update(K key, V value) {
        table.put(key, value);
    }

    @Override
    public void remove(K key) {
        table.remove(key);
    }

    @Override
    public Iterable<K> getKeys() {
        return table.keySet();
    }
    @Override
    public Map<K,V> getMap(){
        return table;
    }
    @Override
    public void setContent(Map<K, V> newContent) {
        table = newContent;
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        MyIDictionary<K, V> newDict = new MyDictionary<>();
        for (K key : table.keySet()){
            newDict.put(key, table.get(key));
        }
        return newDict;
    }

    @Override
    public Set<String> keySet() {
        return (Set<String>) table.keySet();
    }

}
