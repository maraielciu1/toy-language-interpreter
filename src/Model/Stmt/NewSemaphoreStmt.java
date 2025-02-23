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
    private Exp exp1;
    private Exp exp2;
    private Lock lock = new ReentrantLock();

    public NewSemaphoreStmt(String v, Exp e1, Exp e2){
        var = v;
        exp1 = e1;
        exp2 = e2;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value expVal1 = exp1.eval(state.getSymTable(), state.getHeap());
        Value expVal2 = exp2.eval(state.getSymTable(), state.getHeap());
        if(!expVal1.getType().equals(new IntType()))
            throw new MyException("Expression must evaluate to an integer");
        if(!expVal2.getType().equals(new IntType()))
            throw new MyException("Expression must evaluate to an integer");

        lock.lock();
        int v1 = ((IntValue)expVal1).getVal();
        int v2 = ((IntValue)expVal2).getVal();
        int newLocation = state.getSemTbl().getFirstFree();
        state.getSemTbl().put(newLocation, new Pair<>(v1, new Pair<>(new Vector<>(), v2)));
        Value varVal = state.getSymTable().lookUp(var);
        if(varVal==null){
            lock.unlock();
            throw new MyException("Variable "+var+" not found");}
        if(!varVal.getType().equals(new IntType())){
            lock.unlock();
            throw new MyException("Variable "+var+" must be of type int");}
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
        if(!exp1.typeCheck(typeEnv).equals(new IntType()))
            throw new MyException("Expression must evaluate to an integer");
        if(!exp2.typeCheck(typeEnv).equals(new IntType()))
            throw new MyException("Expression must evaluate to an integer");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NewSemaphoreStmt(var, exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public String toString(){
        return "newSemaphore("+var+", "+exp1.toString()+","+exp2.toString()+")";
    }
}