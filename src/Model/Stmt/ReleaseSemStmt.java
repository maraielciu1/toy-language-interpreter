package Model.Stmt;

import Model.PrgState;
import Model.Types.Type;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseSemStmt implements IStmt{
    private String var;
    private Lock lock = new ReentrantLock();

    public ReleaseSemStmt(String v){
        var = v;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        Value varValue = state.getSymTable().lookUp(var);
        if(varValue==null)
            throw new MyException("Variable "+var+" not found");
        if(!varValue.getType().equals(new Model.Types.IntType()))
            throw new MyException("Variable "+var+" must be of type int");
        int semaphoreIndex = ((Model.Value.IntValue)varValue).getVal();
        Pair<Integer, List<Integer>> semaphoreEmtry = state.getSemTbl().lookUp(semaphoreIndex);
        if(semaphoreEmtry==null){
            lock.unlock();
            throw new MyException("Semaphore "+semaphoreIndex+" not found");
        }
        Integer semaphoreKey = semaphoreEmtry.getKey();
        List<Integer> semaphoreList = semaphoreEmtry.getValue();
        Integer currentThreadId = state.getId();
        if(semaphoreList.contains(currentThreadId))
            semaphoreList.remove(currentThreadId);
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookUp(var);
        if(varType==null)
            throw new MyException("Variable "+var+" not found");
        if(!varType.equals(new Model.Types.IntType()))
            throw new MyException("Variable "+var+" must be of type int");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ReleaseSemStmt(var);
    }

    @Override
    public String toString(){
        return "release("+var+")";
    }
}
