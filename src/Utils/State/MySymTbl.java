package Utils.State;

import Model.Value.Value;
import Utils.ADT.MyDictionary;
import Utils.ADT.MyIDictionary;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MySymTbl implements IMySymTbl{
    MyIDictionary<String, Value> symTbl;
    public MySymTbl(){
        this.symTbl=new MyDictionary<>();
    }
    @Override
    public void put(String s, Value v) {
        symTbl.put(s, v);
    }

    @Override
    public Value lookUp(String key) {
        return symTbl.lookUp(key);
    }

    @Override
    public boolean contains(String key) {
        return symTbl.contains(key);
    }

    @Override
    public void update(String key, Value value) {
        symTbl.update(key, value);
    }

    @Override
    public void remove(String key) {
        symTbl.remove(key);
    }

    @Override
    public Iterable<String> getKeys() {
        return symTbl.getKeys();
    }
    @Override
    public String toString() {
        return getSymTbl();
    }
    public String getSymTbl(){
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        for (String key : symTbl.getKeys()){
            sb.append(key);
            sb.append("=");
            sb.append(symTbl.lookUp(key));
            sb.append("\n");
        }
        return sb.toString();
    }
    @Override
    public Map<String, Value> getContent() {
        return symTbl.getMap();
    }

    @Override
    public IMySymTbl deepCopy() {
        MySymTbl newSymTbl = new MySymTbl();
        for (String key : symTbl.getKeys()){
            newSymTbl.put(key, symTbl.lookUp(key));
        }
        return newSymTbl;
    }

    @Override
    public Collection<Value> values(){
        return symTbl.getMap().values();
    }
}
