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

public class AquireSemStmt implements IStmt {
    private String var;
    private Lock lock = new ReentrantLock();

    public AquireSemStmt(String v){
        var = v;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value varValue = state.getSymTable().lookUp(var);
        if(varValue==null)
            throw new MyException("Variable "+var+" not found");
        if(!varValue.getType().equals(new Model.Types.IntType()))
            throw new MyException("Variable "+var+" must be of type int");
        int semaphoreIndex = ((Model.Value.IntValue)varValue).getVal();
        lock.lock();
        Pair<Integer, Pair<List<Integer>, Integer>> semaphoreEmtry = state.getSemTbl().lookUp(semaphoreIndex);
        if(semaphoreEmtry==null){
            lock.unlock();
            throw new MyException("Semaphore "+semaphoreIndex+" not found");
        }
        Integer semaphoreKey = semaphoreEmtry.getKey();
        Integer semaphoreKey2=semaphoreEmtry.getValue().getValue();
        List<Integer> semaphoreList = semaphoreEmtry.getValue().getKey();
        if(semaphoreKey-semaphoreKey2>semaphoreList.size()){
            if(!semaphoreList.contains(state.getId()))
                semaphoreList.add(state.getId());
        }
        else
            state.getExeStack().push(new AquireSemStmt(var));
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
        return new AquireSemStmt(var);
    }

    @Override
    public String toString(){
        return "aquireSemaphore("+var+")";
    }
}
