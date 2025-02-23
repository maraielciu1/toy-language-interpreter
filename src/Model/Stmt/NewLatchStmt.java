package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt{
    private String var;
    private Exp exp;
    private Lock lock= new ReentrantLock();

    public NewLatchStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        Value expVal = exp.eval(state.getSymTable(), state.getHeap());

        if(!expVal.getType().equals(new IntType())){
            lock.unlock();
            throw new MyException("Expression value is not an integer");
        }
        // LatchTable2 = LatchTable1 synchronizedUnion {newfreelocation ->num1}
        int latch=((IntValue) expVal).getVal();
        int latchAddr=state.getLatchTable().put(latch);
        Value varVal=state.getSymTable().lookUp(var);

        if(varVal==null){
            lock.unlock();
            throw new MyException("Variable not found in SymTable");
        }
        if(!varVal.getType().equals(new IntType())){
            lock.unlock();
            throw new MyException("Variable is not an integer");
        }

        state.getSymTable().update(var, new IntValue(latchAddr));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar;
        typeVar = typeEnv.lookUp(var);
        Type typeExp = exp.typeCheck(typeEnv);
        if(var==null)
            throw new MyException("Variable not found in SymTable");
        if(!typeExp.equals(new IntType()))
            throw new MyException("Expression value is not an integer");
        if(!typeVar.equals(new IntType()))
            throw new MyException("Variable is not an integer");

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLatchStmt(var, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "newLatch(" + var + ", " + exp.toString() + ")";
    }
}
