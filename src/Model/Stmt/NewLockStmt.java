package Model.Stmt;

import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Value.IntValue;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.ILockTable;
import Utils.State.IMySymTbl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStmt implements IStmt {
    private String var;
    private static Lock lock = new ReentrantLock();

    public NewLockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        ILockTable lockTable = state.getLockTable();
        IMySymTbl symTbl = state.getSymTable();
        int freeAddress = lockTable.getFreeLocation();
        lockTable.put(freeAddress, -1);
        if(symTbl.contains(var) && symTbl.lookUp(var).getType().equals(new IntType())) {
            symTbl.update(var, new IntValue(freeAddress));
        }
        else
            throw new MyException("Variable '" + var + "' does not exist");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookUp(var).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Variable '" + var + "' is nor int");
    }

    @Override
    public IStmt deepCopy() {
        return new NewLockStmt(var);
    }

    @Override
    public String toString() {
        return "newLock(" + var + ")";
    }
}
