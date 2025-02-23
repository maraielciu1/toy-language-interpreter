package Model.Stmt;

import Model.PrgState;
import Model.Types.Type;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }
}
