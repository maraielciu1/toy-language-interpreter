package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

public class WhileStmt implements IStmt {
    private IStmt stmt;
    private Exp condition;

    public WhileStmt(Exp e, IStmt s) {
        this.condition = e;
        this.stmt = s;
    }

    @Override
    public String toString() {
        return "while(" + condition.toString() + ") {" + stmt.toString() + "}";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value val = this.condition.eval(state.getSymTable(), state.getHeap());
        if(val instanceof BoolValue) {
            if(val.toString().equals("true")) {
                state.getExeStack().push(this.deepCopy());
                state.getExeStack().push(this.stmt);
            }
        } else {
            throw new MyException("Condition is not a boolean");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(this.condition.deepCopy(), this.stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = this.condition.typeCheck(typeEnv);
        if(typ.equals(new Model.Types.BoolType())) {
            this.stmt.typeCheck(typeEnv);
            return typeEnv;
        } else {
            throw new MyException("The condition of the while statement is not a boolean.");
        }
    }
}
