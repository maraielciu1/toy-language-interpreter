package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.ADT.MyIList;
import Utils.State.IMyOut;
import Utils.State.MyOut;

public class PrintStmt implements IStmt{
    private Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value val = exp.eval(state.getSymTable(), state.getHeap());
        IMyOut out = state.getOut();
        out.add(val.toString());
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp);
    }


    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }
}
