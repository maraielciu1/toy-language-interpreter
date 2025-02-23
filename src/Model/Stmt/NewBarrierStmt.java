package Model.Stmt;

import Model.Exp.Exp;
import Model.Exp.ValueExp;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import javafx.util.Pair;

import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStmt implements IStmt{
    private String var;
    private Exp exp;
    private Lock lock=new ReentrantLock();
    public NewBarrierStmt(String vname, Exp e){
        var=vname;
        exp=e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value expEval = exp.eval(state.getSymTable(), state.getHeap());
        if(!expEval.getType().equals(new IntType()))
            throw new MyException("Expression should be evaluated to int");

        int expVal = ((IntValue) expEval).getVal();
        lock.lock();
        int newLocation=state.getBarrierTbl().put(new Pair<>(expVal, new Vector<>()));
        state.getSymTable().update(var,new IntValue(newLocation));
        Value varValue = state.getSymTable().lookUp(var);
        if(varValue==null) {
            lock.unlock();
            throw new MyException("Variable not in symtbl");
        }
        if(!varValue.getType().equals(new IntType())){
            lock.unlock();
            throw new MyException("Variable not int");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expType = exp.typeCheck(typeEnv);
        if(!expType.equals(new IntType()))
            throw new MyException("Expression not int");
        Type varType = typeEnv.lookUp(var);
        if(varType==null)
            throw new MyException("Variable was not declared");
        if(!varType.equals(new IntType()))
            throw new MyException("variable not int");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NewBarrierStmt(var,exp.deepCopy());
    }

    @Override
    public String toString(){
        return "newBarrier("+var+","+exp+")";
    }
}
