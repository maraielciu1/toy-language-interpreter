package Model.Stmt;

import Model.Exp.Exp;
import Model.Exp.RelExp;
import Model.PrgState;
import Model.Types.Type;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IMyExeStack;

public class SwitchStmt implements IStmt{
    private Exp exp;
    private Exp exp1;
    private Exp exp2;
    private IStmt stmt;
    private IStmt stmt1;
    private IStmt stmt2;

    public SwitchStmt(Exp e, Exp e1, IStmt s1, Exp e2, IStmt s2, IStmt s){
        exp = e;
        exp1 = e1;
        exp2 = e2;
        stmt = s;
        stmt1 = s1;
        stmt2 = s2;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMyExeStack exeStack = state.getExeStack();
        IStmt newStmt = new IfStmt(
                new RelExp("==", exp, exp1),
                stmt1,
                new IfStmt(
                        new RelExp("==", exp, exp2),
                        stmt2,
                        stmt
                )
        );
        exeStack.push(newStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typExp = exp.typeCheck(typeEnv);
        Type typExp1 = exp1.typeCheck(typeEnv);
        Type typExp2 = exp2.typeCheck(typeEnv);

        if(typExp.equals(typExp1) && typExp2.equals(typExp1)){
            stmt.typeCheck(typeEnv.deepCopy());
            stmt1.typeCheck(typeEnv.deepCopy());
            stmt2.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("Switch statement: different types");
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp.deepCopy(), exp1.deepCopy(), stmt1.deepCopy(), exp2.deepCopy(), stmt2.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString(){
        return "switch("+exp.toString()+") (case "+exp1.toString()+": "+stmt1.toString()+") (case "+exp2.toString()+": "+stmt2.toString()+") default: "+stmt.toString();
    }
}
