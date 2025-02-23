package Utils.State;

import Utils.ADT.MyException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface IBarrierTable {
    int put(Pair<Integer, List<Integer>> value) throws MyException;
    int getFreeLocation();
    void put(Integer key, Pair<Integer, List<Integer>> value) throws MyException;

    Pair<Integer, List<Integer>> lookUp(int variableInt);

    HashMap<Integer, Pair<Integer, List<Integer>>> getContent();

    Set<Integer> keySet();
}
