package Utils.State;

import Model.Value.Value;
import Utils.ADT.MyDictionary;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

import java.util.Map;

public class Heap implements IHeap{
    private MyIDictionary<Integer, Value> heap;
    private int firstFreeAddress;
    public Heap() {
        this.heap = new MyDictionary<>();
        this.firstFreeAddress = 1;
    }

    @Override
    public int allocate(Value value) {
        this.heap.put(this.firstFreeAddress, value);
        return this.firstFreeAddress++;
    }

    @Override
    public void deallocate(int address) throws MyException {
        try{
            this.heap.remove(address);
        }
        catch (Exception e) {
            throw new MyException("Address not found in heap");
        }

    }

    @Override
    public Value read(int address) throws MyException {
        try{
            return this.heap.lookUp(address);
        }
        catch (Exception e) {
            throw new MyException("Address not found in heap");
        }
    }

    @Override
    public void write(int address, Value value) throws MyException{
        if(this.heap.lookUp(address) == null)
            throw new MyException("Address not found in heap");
        this.heap.put(address, value);
    }

    @Override
    public Map<Integer, Value> getContent() {
        return this.heap.getMap();
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder("Heap:\n");
        for (int key : heap.getKeys()) {
            answer.append(key).append("(").append(heap.lookUp(key).getType().toString()).append(")").append(":-> ").append(heap.lookUp(key).toString()).append("\n");
        }
        return answer.toString();
    }
    @Override
    public void setContent(Map<Integer, Value> newContent) {
        this.heap.setContent(newContent);
    }
}
