package Model.Stmt;

import Model.PrgState;
import Model.Types.Type;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

public class FunctionReturnStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getAllSymTables().pop();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new FunctionReturnStmt();
    }

    @Override
    public String toString(){
        return "return";
    }
}
