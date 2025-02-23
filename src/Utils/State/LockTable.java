package Utils.State;

import Utils.ADT.MyException;

import java.util.HashMap;
import java.util.Set;

public class LockTable implements ILockTable {
    private HashMap<Integer,Integer> lockTable;
    private int freeLocation=0;
    public LockTable(){
        this.lockTable=new HashMap<>();
    }
    @Override
    public int getFreeLocation() {
        synchronized (lockTable){
            freeLocation++;
            return freeLocation;
        }
    }

    @Override
    public void put(int key, int value) throws MyException {
        synchronized (lockTable){
            if(lockTable.containsKey(key))
                throw new MyException("Key already exists in lock table");
            lockTable.put(key,value);
        }
    }

    @Override
    public HashMap<Integer, Integer> getContent() {
        synchronized (lockTable){
            return lockTable;
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (lockTable){
            return lockTable.isEmpty();
        }
    }

    @Override
    public int get(int key) throws MyException {
        synchronized (lockTable){
            if(!lockTable.containsKey(key))
                throw new MyException("Key does not exist in lock table");
            return lockTable.get(key);
        }
    }

    @Override
    public boolean contains(int key) {
        synchronized (lockTable){
            return lockTable.containsKey(key);
        }
    }

    @Override
    public void update(int key, int value) throws MyException {
        synchronized (lockTable){
            if(!lockTable.containsKey(key))
                throw new MyException("Key does not exist in lock table");
            lockTable.replace(key,value);
        }
    }

    @Override
    public void setContent(HashMap<Integer, Integer> newContent) {
        synchronized (lockTable){
            lockTable=newContent;
        }
    }

    @Override
    public Set<Integer> getKeySet() {
        synchronized (lockTable){
            return lockTable.keySet();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        synchronized (lockTable){
            for (var key : lockTable.keySet()) {
                sb.append(key).append(" -> ").append(lockTable.get(key)).append("\n");
            }
        }
        return sb.toString();
    }
}
