package Model.Exp;

import Model.Types.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IHeap;
import Utils.State.IMySymTbl;

public class ValueExp implements Exp {
    private Value e;
    public ValueExp(Value value) {
        this.e = value;
    }
    @Override
    public Value eval(IMySymTbl tbl,  IHeap hp) throws MyException {
        return e;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e);
    }
}
