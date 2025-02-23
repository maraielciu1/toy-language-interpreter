package Model.Stmt;

import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Model.Exp.Exp;
import javafx.util.Pair;

import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphoreStmt implements IStmt {
    private String var;
    private Exp exp;
    private Lock lock = new ReentrantLock();

    public NewSemaphoreStmt(String v, Exp e){
        var = v;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value expVal = exp.eval(state.getSymTable(), state.getHeap());
        if(!expVal.getType().equals(new IntType()))
            throw new MyException("Expression must evaluate to an integer");
        int expInt = ((IntValue)expVal).getVal();
        lock.lock();
        int newLocation = state.getSemTbl().put(new Pair<>(expInt, new Vector<>()));
        Value varVal = state.getSymTable().lookUp(var);
        if(varVal==null)
            throw new MyException("Variable "+var+" not found");
        if(!varVal.getType().equals(new IntType()))
            throw new MyException("Variable "+var+" must be of type int");
        state.getSymTable().put(var, new IntValue(newLocation));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookUp(var);
        if(varType==null)
            throw new MyException("Variable "+var+" not found");
        if(!varType.equals(new IntType()))
            throw new MyException("Variable "+var+" must be of type int");
        if(!exp.typeCheck(typeEnv).equals(new IntType()))
            throw new MyException("Expression must evaluate to an integer");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NewSemaphoreStmt(var, exp.deepCopy());
    }

    @Override
    public String toString(){
        return "newSemaphore("+var+", "+exp.toString()+")";
    }
}
