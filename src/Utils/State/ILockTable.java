package Utils.State;

import Utils.ADT.MyException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public interface ILockTable {
    int getFreeLocation();
    void put(int key, int value) throws MyException;
    HashMap<Integer,Integer> getContent();
    boolean isEmpty();
    int get(int key) throws MyException;
    boolean contains(int key);
    void update(int key, int value) throws MyException;
    void setContent(HashMap<Integer,Integer> newContent);
    Set<Integer> getKeySet();
}
