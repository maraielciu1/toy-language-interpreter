package Utils.State;

import Model.Value.Value;
import Utils.ADT.MyIDictionary;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IMySymTbl {
    void put(String s, Value v);
    Value lookUp(String key);
    boolean contains(String key);
    void update(String key, Value value);
    void remove(String key);
    Iterable<String> getKeys();
    String toString();
    Map<String, Value> getContent();
    IMySymTbl deepCopy();
    Collection<Value> values();
}
