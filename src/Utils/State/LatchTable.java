package Utils.State;

import Utils.ADT.MyException;

import java.util.HashMap;

public class LatchTable implements ILatchTable {
    private HashMap<Integer, Integer> latchTable;
    private int nextFree;

    public LatchTable() {
        this.latchTable = new HashMap<>();
        this.nextFree = 0;
    }
    @Override
    public int put(Integer value) throws MyException {
        synchronized (latchTable) {
            latchTable.put(nextFree, value);
            nextFree++;
        }
        return nextFree - 1;
    }

    @Override
    public int get(Integer key) throws MyException {
        synchronized (latchTable) {
            if (latchTable.containsKey(key)) {
                return latchTable.get(key);
            }
            throw new MyException("Key not found in LatchTable");
        }
    }

    @Override
    public int getNextFree() {
        int addr=1;
        synchronized (latchTable) {
            while (latchTable.containsKey(addr)) {
                addr++;
            }
        }
        return addr;
    }

    @Override
    public void put(Integer key, Integer value) throws MyException {
        synchronized (latchTable) {
            if(!key.equals(nextFree)) {
                throw new MyException("Key not found in LatchTable");
            }
            latchTable.put(key, value);
            nextFree++;
        }
    }

    @Override
    public void update(Integer latchAddr, int i) {
        synchronized (latchTable) {
            if (latchTable.containsKey(latchAddr)) {
                latchTable.replace(latchAddr, i);
            }
        }
    }

    @Override
    public HashMap<Integer, Integer> getContent() {
        return latchTable;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Integer key : latchTable.keySet()) {
            str.append(key);
            str.append(" -> ");
            str.append(latchTable.get(key));
            str.append("\n");
        }
        return str.toString();
    }
}
