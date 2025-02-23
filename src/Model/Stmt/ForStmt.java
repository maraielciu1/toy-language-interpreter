package Model.Stmt;

import Model.Exp.Exp;
import Model.Exp.RelExp;
import Model.Exp.VarExp;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.ADT.MyIStack;
import Utils.State.IMyExeStack;

public class ForStmt implements IStmt{
    private String varName;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;
    private IStmt stmt;

    public ForStmt(String varName, Exp exp1, Exp exp2, Exp exp3, IStmt stmt){
        this.varName = varName;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMyExeStack stk = state.getExeStack();
        IStmt toWhile = new CompStmt(new AssignStmt("v", exp1), new WhileStmt(new RelExp("<", new VarExp("v"), exp2), new CompStmt(stmt, new AssignStmt("v", exp3))));
        stk.push(toWhile);
        state.setExeStack(stk);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = exp1.typeCheck(typeEnv);
        Type t2 = exp2.typeCheck(typeEnv);
        Type t3 = exp3.typeCheck(typeEnv);
        if (t1.equals(new IntType()) && t2.equals(new IntType()) && t3.equals(new IntType())) {
            return typeEnv;
        }
        else
            throw new MyException("For statement typecheck failed.");
    }

    @Override
    public String toString() {
        return "for(v" + "=" + exp1.toString() + ";" +"v<" +exp2.toString() + ";" +"v="+ exp3.toString() + "){" + stmt.toString() + "}";
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(varName, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), stmt.deepCopy());
    }
}
