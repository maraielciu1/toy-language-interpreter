package Utils.State;

import Utils.ADT.MyException;

import java.util.HashMap;

public interface ILatchTable {
    int put(Integer value) throws MyException;
    int get(Integer key) throws MyException;
    int getNextFree();
    void put(Integer key, Integer value) throws MyException;

    void update(Integer latchAddr, int i);

    HashMap<Integer, Integer> getContent();
}
