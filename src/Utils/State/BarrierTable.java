package Utils.State;

import Utils.ADT.MyException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class BarrierTable implements IBarrierTable{
    private HashMap<Integer, Pair<Integer, List<Integer>>> barrierTable;
    private int nextFree;

    public BarrierTable(){
        barrierTable = new HashMap<>();
        nextFree = 1;
    }


    @Override
    public int put(Pair<Integer, List<Integer>> value) throws MyException {
        barrierTable.put(nextFree,value);
        synchronized (this){
            nextFree++;
        }
        return nextFree-1;
    }

    @Override
    public int getFreeLocation() {
        int locAddr=1;
        while(barrierTable.get(locAddr)!=null)
            locAddr++;
        return locAddr;
    }

    @Override
    public void put(Integer key, Pair<Integer, List<Integer>> value) throws MyException {
        if(!key.equals(nextFree))
            throw new MyException("Invalid barrier tbl location");
        synchronized (this){
        barrierTable.put(key, value);

            nextFree++;
        }
    }

    @Override
    public Pair<Integer, List<Integer>> lookUp(int variableInt) {
        return barrierTable.get(variableInt);
    }

    @Override
    public HashMap<Integer, Pair<Integer, List<Integer>>> getContent() {
        return barrierTable;
    }

    @Override
    public Set<Integer> keySet() {
        return barrierTable.keySet();
    }
}
