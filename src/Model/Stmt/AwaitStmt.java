package Model.Stmt;

import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt{
    private String var;
    private Lock lock=new ReentrantLock();

    public AwaitStmt(String vname){
        var=vname;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value variableValue = state.getSymTable().lookUp(var);
            if (variableValue == null)
                throw new MyException(String.format("Variable %s has not been declared!", var));
            if (!variableValue.getType().equals(new IntType()))
                throw new MyException(String.format("Variable %s should be of integer type!", var));

            int variableInt = ((IntValue) variableValue).getVal();
            lock.lock();
            Pair<Integer, List<Integer>> barrierTableEntry = state.getBarrierTbl().lookUp(variableInt);
            if (barrierTableEntry == null) {
                lock.unlock();
                throw new MyException("Invalid barrier table location!");
            }
            int barrierTableValue = barrierTableEntry.getKey();
            List<Integer> barrierPrograms = barrierTableEntry.getValue();

            if (barrierTableValue > barrierPrograms.size()) {
                if (!barrierPrograms.contains(state.getId()))
                {barrierPrograms.add(state.getId()); System.out.println(state.getId());}
                state.getExeStack().push(this);
            }
            lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookUp(var);
        if(varType==null)
            throw new MyException("Var not declared");
        if(!varType.equals(new IntType()))
            throw new MyException("Var not int");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }

    @Override
    public String toString(){
        return "await("+var+")";
    }
}
