package Utils.ADT;

import Model.Value.Value;

import java.util.List;

public class MyList<T> implements MyIList<T> {
    private java.util.List<T> list;

    public MyList() {
        list = new java.util.ArrayList<>();
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public T[] toList() {
        return (T[]) list.toArray();
    }
}
