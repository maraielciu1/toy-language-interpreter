package Model.Stmt;

import Model.PrgState;
import Model.Types.Type;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.ADT.MyIStack;
import Utils.State.IMyExeStack;
import Utils.State.MyExeStack;

public class CompStmt implements IStmt{
    public IStmt getFirst() {
        return first;
    }

    private IStmt first;

    public IStmt getSecond() {
        return second;
    }

    private IStmt second;
    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException{
        IMyExeStack stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }
    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(),second.deepCopy());
    }


    @Override
    public String toString() {
        return "(" + first + "; " + second + ")";
    }
}
