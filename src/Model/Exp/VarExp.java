package Model.Exp;

import Model.Types.Type;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IHeap;
import Utils.State.IMySymTbl;

public class VarExp implements Exp{
    private String id;
    public VarExp(String id) {
        this.id = id;
    }
    @Override
    public Value eval(IMySymTbl tbl, IHeap hp) throws MyException {
        return tbl.lookUp(id);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.contains(id)) {
            return typeEnv.lookUp(id); // Return the type of the variable
        } else {
            throw new MyException("Variable " + id + " is not declared.");
        }
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
