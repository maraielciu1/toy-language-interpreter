package Utils.ADT;

import Model.Value.Value;

import java.util.List;

public interface MyIList<T> {
    public void add(T v);
    public List<T> getList();
    T[] toList();
}
