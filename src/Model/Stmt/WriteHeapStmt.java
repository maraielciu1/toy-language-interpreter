package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Value.RefValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

public class WriteHeapStmt implements IStmt{
    private Exp address;
    private Exp value;
    public WriteHeapStmt(Exp a, Exp v) {
        this.address = a;
        this.value = v;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value addr = this.address.eval(state.getSymTable(), state.getHeap());
        Value val = this.value.eval(state.getSymTable(), state.getHeap());
        if(!(addr instanceof RefValue)) {
            throw new MyException("Type mismatch");
        }
        state.getHeap().write(((RefValue)addr).getAddress(), val);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type addrType = this.address.typeCheck(typeEnv); // Type of the address
        Type valType = this.value.typeCheck(typeEnv); // Type of the value
        if (addrType instanceof RefType refType && refType.getInner().equals(valType)) {
            return typeEnv; // Both types match
        }
        throw new MyException("WriteHeap: Address is not a RefType or types do not match.");
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(this.address.deepCopy(), this.value.deepCopy());
    }

    @Override
    public String toString() {
        return "writeHeap(" + this.address.toString() + ", " + this.value.toString() + ")";
    }
}
