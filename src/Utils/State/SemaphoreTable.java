package Utils.State;

import Utils.ADT.MyException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class SemaphoreTable implements ISemaphoreTeble {
    private int nextFree;
    private HashMap<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> semTable;

    public SemaphoreTable(){
        nextFree = 1;
        semTable = new HashMap<>();
    }
    @Override
    public int put(Pair<Integer, Pair<List<Integer>, Integer>> value) throws MyException {
        synchronized (semTable){
            semTable.put(nextFree, value);
            nextFree++;
        }
        return nextFree-1;
    }


    @Override
    public int getFirstFree() {
        int locationAddr=1;
        while(semTable.containsKey(locationAddr)){
            locationAddr++;
        }
        return locationAddr;
    }

    @Override
    public void put(Integer index, Pair<Integer, Pair<List<Integer>, Integer>>  value) throws MyException {
        synchronized (semTable){
            if(!index.equals(nextFree))
                throw new MyException("Invalid index");
            semTable.put(index, value);
            nextFree++;

        }
    }

    @Override
    public HashMap<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> getContent() {
        return semTable;
    }

    @Override
    public Pair<Integer, Pair<List<Integer>, Integer>> lookUp(int semaphoreIndex) {
        return semTable.get(semaphoreIndex);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Integer key : semTable.keySet()) {
            str.append(key).append(" -> ").append(semTable.get(key)).append("\n");
        }
        return str.toString();
    }


}