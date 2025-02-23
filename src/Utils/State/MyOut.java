package Utils.State;

import Utils.ADT.MyIList;
import Utils.ADT.MyList;

import java.util.List;

public class MyOut implements IMyOut {
    private MyIList<String> list;

    public MyOut() {
        list = new MyList<>();
    }

    @Override
    public void add(String s) {
        list.add(s);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public String[] toList() {
        return list.toList();
    }

    @Override
    public String getOutList(){
        String[] out = list.toList();
        StringBuilder sb = new StringBuilder();
        for (String s : out){
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<String> getList() {
        return list.getList();
    }

}
