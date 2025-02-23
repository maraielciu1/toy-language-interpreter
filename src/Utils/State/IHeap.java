package Utils.State;

import Model.Value.Value;
import Utils.ADT.MyException;

import java.util.Map;

public interface IHeap {
    int allocate(Value value);
    void deallocate(int address) throws MyException;
    Value read(int address) throws MyException;
    void write(int address, Value value) throws MyException;
    Map<Integer, Value> getContent();
    void setContent(Map<Integer, Value> newContent);
}
