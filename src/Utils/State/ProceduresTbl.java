package Utils.State;

import Model.Stmt.IStmt;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class ProceduresTbl implements IProceduresTbl{
    private HashMap<String, Pair<List<String>, IStmt>> procTbl;

    public ProceduresTbl(){
        procTbl=new HashMap<>();
    }

    @Override
    public Pair<List<String>, IStmt> lookUp(String name) {
        return procTbl.get(name);
    }

    @Override
    public HashMap<String, Pair<List<String>, IStmt>> getContent() {
        return procTbl;
    }

    @Override
    public void put(String sum, Pair<List<String>, IStmt> listIStmtPair) {
        procTbl.put(sum, listIStmtPair);
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(String key: procTbl.keySet()){
            s.append(key).append("->").append(procTbl.get(key).getKey()).append(" ").append(procTbl.get(key).getValue()).append("\n");
        }
        return s.toString();
    }
}
