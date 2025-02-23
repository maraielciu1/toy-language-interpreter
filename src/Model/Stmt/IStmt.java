package Model.Stmt;

import Model.PrgState;
import Model.Types.Type;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;
    IStmt deepCopy();
}
