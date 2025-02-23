package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Value.RefValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

import java.sql.Ref;

public class NewStmt implements IStmt {
    private String name;
    private Exp exp;
    public NewStmt(String n, Exp e) {
        this.name = n;
        this.exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value val = this.exp.eval(state.getSymTable(), state.getHeap());
        state.getSymTable().put(this.name, new RefValue(state.getHeap().allocate(val), val.getType()));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookUp(this.name); // Variable type
        Type exprType = this.exp.typeCheck(typeEnv); // Expression type

        if (varType instanceof RefType refVarType && exprType.equals(refVarType.getInner())) {
            // Direct match: variable is RefType, and inner type matches expression
            return typeEnv;
        } else if (varType instanceof RefType refVarType && exprType instanceof RefType) {
            // Nested match: variable is Ref Ref int, and expression is Ref int
            return typeEnv;
        }

        throw new MyException("NewStmt typeCheck Error: Variable " + this.name + " has type " + varType +
                ", but expression evaluates to " + exprType);
    }
    public IStmt deepCopy() {
        return new NewStmt(name, exp);
    }


    @Override
    public String toString() {
        return "new(" + this.name + ", " + this.exp.toString() + ")";
    }
}
