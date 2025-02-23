package Model.Exp;

import Model.Types.RefType;
import Model.Types.Type;
import Model.Value.RefValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IHeap;
import Utils.State.IMySymTbl;

public class ReadHeapExp implements Exp {
    private Exp exp;

    public ReadHeapExp(Exp exp) {
        this.exp = exp;
    }
    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }

    @Override
    public Value eval(IMySymTbl tbl, IHeap hp) throws MyException {
        Value v = exp.eval(tbl, hp);
        if (v instanceof RefValue) {
            int address = ((RefValue) v).getAddress();
            return hp.read(address);
        }
        throw new MyException("Expression is not a reference");
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = exp.typeCheck(typeEnv); // Get the type of the expression
        if (typ instanceof RefType refType) {
            return refType.getInner(); // Return the type pointed to by the reference
        }
        throw new MyException("ReadHeap: The expression is not of RefType.");
    }
    @Override
    public Exp deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }
}
