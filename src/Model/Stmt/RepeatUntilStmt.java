package Model.Stmt;

import Model.Exp.Exp;
import Model.Exp.NotExp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IMyExeStack;

public class RepeatUntilStmt implements IStmt{
    IStmt stmt;
    Exp exp;

    public RepeatUntilStmt(IStmt s, Exp e){
        stmt=s;
        exp=e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMyExeStack exeStack = state.getExeStack();
        IStmt whileS=new CompStmt(stmt,
                new WhileStmt(new NotExp(exp), stmt)
        );
        exeStack.push(whileS);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expType=exp.typeCheck(typeEnv);
        stmt.typeCheck(typeEnv);
        if(expType.equals(new BoolType())){
            stmt.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The exp of repeat until was not boolean");
    }

    @Override
    public IStmt deepCopy() {
        return new RepeatUntilStmt(stmt.deepCopy(),exp.deepCopy());
    }

    @Override
    public String toString(){
        return "repeat("+stmt+") until "+exp;
    }
}
