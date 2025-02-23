package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.Type;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

public class ReadHeapStmt implements IStmt {
    private Exp exp;

    public ReadHeapStmt(Exp e) {
        this.exp = e;
    }

    @Override
    public String toString() {
        return "readHeap(" + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        exp.eval(state.getSymTable(), state.getHeap());
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return (MyIDictionary<String, Type>) exp.typeCheck(typeEnv);
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

}
