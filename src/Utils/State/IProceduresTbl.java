package Utils.State;

import Model.Stmt.IStmt;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public interface IProceduresTbl {
    Pair<List<String>, IStmt> lookUp(String name);

    HashMap<String, Pair<List<String>, IStmt>> getContent();

    void put(String sum, Pair<List<String>, IStmt> listIStmtPair);
}
