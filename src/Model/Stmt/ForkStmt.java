package Model.Stmt;

import Model.PrgState;
import Model.Types.Type;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.MyExeStack;
import Utils.State.MySymTbl;

public class ForkStmt implements IStmt{
    private IStmt innerStmt;
    public ForkStmt(IStmt i){
        this.innerStmt=i;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyExeStack exeStack = new MyExeStack();
        return new PrgState(exeStack, state.getSymTable().deepCopy(), state.getFileTable(), state.getHeap(), state.getOut(),state.getBarrierTbl(), innerStmt);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return innerStmt.typeCheck(typeEnv);
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(innerStmt.deepCopy());
    }

    @Override
    public String toString(){
        return "fork("+innerStmt.toString()+")";
    }
}
