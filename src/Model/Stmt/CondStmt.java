package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IMyExeStack;
import Utils.State.IMySymTbl;

public class CondStmt implements IStmt{
    private String var;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;

    public CondStmt(String v, Exp e1, Exp e2, Exp e3){
        this.var = v;
        this.exp1 = e1;
        this.exp2 = e2;
        this.exp3 = e3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMySymTbl mySymTable = state.getSymTable();
        if(mySymTable.contains(var)){
            Value v1=exp1.eval(mySymTable,state.getHeap());
            Value v2=exp2.eval(mySymTable,state.getHeap());
            Value v3=exp3.eval(mySymTable,state.getHeap());
            Type typeID = (mySymTable.lookUp(var)).getType();
            if(v2.getType().equals(typeID) && v3.getType().equals(typeID)){
                if(v1.getType().equals(new BoolType())) {
                    boolean cond=((BoolValue)v1).getVal();
                    if(cond)
                        mySymTable.update(var,v2);
                    else
                        mySymTable.update(var,v3);
                }
                else
                    throw new MyException("Conditional assignment: right hand side and left hand side have different types ");
            }
            else
                throw new MyException("Conditional assignment: right hand side and left hand side have different types ");

        }
        else
            throw new MyException("Conditional assignment: the used variable" +var + " was not declared before");
        state.setSymTable(mySymTable);
        return null;

    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookUp(var);
        Type typeExp2 = exp2.typeCheck(typeEnv);
        Type typeExp3 = exp3.typeCheck(typeEnv);
        if(typeVar.equals(typeExp2) && typeExp2.equals(typeExp3) && typeExp3.equals(typeExp2)){
            Type typeExp1 = exp1.typeCheck(typeEnv);
            if(typeExp1.equals(new BoolType()))
                return typeEnv;
            else
                throw new MyException("Conditional assignment: the condition is not a boolean");
        }
        else
            throw new MyException("Conditional assignment: right hand side and left hand side have different types ");

    }

    @Override
    public IStmt deepCopy() {
        return new CondStmt(var, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy());
    }

    @Override
    public String toString(){
        return var+"="+exp1.toString()+"?"+exp2.toString()+":"+exp3.toString();
    }
}
