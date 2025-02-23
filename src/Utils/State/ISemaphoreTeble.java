package Utils.State;

import Utils.ADT.MyException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public interface ISemaphoreTeble {
    int put(Pair<Integer, List<Integer>> value) throws MyException;
    int getFirstFree();
    void put(Integer index, Pair<Integer, List<Integer>> value) throws MyException;
    Pair<Integer, List<Integer>> lookUp(int semaphoreIndex);

    HashMap<Integer, Pair<Integer, List<Integer>>> getContent();
}
