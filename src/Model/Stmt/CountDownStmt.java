package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStmt implements IStmt {
    private String var;
    private Lock lock= new ReentrantLock();

    public CountDownStmt(String var) {
        this.var = var;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value varVal=state.getSymTable().lookUp(var);
        if(varVal==null){
            throw new MyException("Variable not found in SymTable");
        }
        if(!varVal.getType().equals(new Model.Types.IntType())){
            throw new MyException("Variable is not an integer");
        }
        lock.lock();
        Integer latchAddr=((IntValue) varVal).getVal();
        Integer latchValue=state.getLatchTable().get(latchAddr);
        if(latchValue==null){
            lock.unlock();
            throw new MyException("Latch address not found in LatchTable");
        }
        if(latchValue>0){
            state.getLatchTable().update(latchAddr, latchValue-1);

        }
        state.getOut().add(String.valueOf(new IntValue(state.getId())));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookUp(var);
        if(var==null)
            throw new MyException("Variable not found in SymTable");
        if(!typeVar.equals(new Model.Types.IntType()))
            throw new MyException("Variable is not an integer");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new CountDownStmt(var);
    }

    @Override
    public String toString() {
        return "countDown("+var+")";
    }
}
