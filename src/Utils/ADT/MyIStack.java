package Utils.ADT;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws MyException;
    void push(T v);
    boolean isEmpty();
    public MyStack<T> deepCopy();

    public List<T> toListS();
}
