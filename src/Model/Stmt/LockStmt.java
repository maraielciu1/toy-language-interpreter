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

public class LockStmt implements IStmt {
    private String var;
    private static Lock lock = new ReentrantLock();

    public LockStmt(String var) {
        this.var = var;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        IMySymTbl symTable = state.getSymTable();
        ILockTable lockTable = state.getLockTable();
        if(symTable.contains(var)) {
            if(symTable.lookUp(var).getType().equals(new IntType())) {
                IntValue fi = (IntValue) symTable.lookUp(var);
                int foundIndex = fi.getVal();
                if(lockTable.contains(foundIndex)) {
                    if(lockTable.get(foundIndex) == -1) {
                        lockTable.update(foundIndex, state.getId());
                    } else {
                        state.getExeStack().push(this);
                    }
                } else {
                    throw new MyException("The index is not in the lock table.");
                }
            } else {
                throw new MyException("The variable is not an integer.");
            }
        }
        else {
            throw new MyException("The variable is not defined.");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookUp(var).equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException("Variable '" + var + "' is not an integer.");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new LockStmt(var);
    }

    @Override
    public String toString() {
        return "lock(" + var + ")";
    }
}
