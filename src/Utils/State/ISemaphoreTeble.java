package Utils.State;

import Utils.ADT.MyException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public interface ISemaphoreTeble {
    int put(Pair<Integer, Pair<List<Integer>, Integer>> value) throws MyException;
    int getFirstFree();
    void put(Integer index, Pair<Integer, Pair<List<Integer>, Integer>>  value) throws MyException;

    HashMap<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> getContent();

    Pair<Integer, Pair<List<Integer>, Integer>> lookUp(int semaphoreIndex);
}
