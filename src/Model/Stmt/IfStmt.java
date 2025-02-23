package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Value.BoolValue;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.ADT.MyIStack;
import Utils.State.IMyExeStack;
import Utils.State.MyExeStack;

public class IfStmt implements IStmt{
    public Exp getExp() {
        return exp;
    }

    public IStmt getThenS() {
        return thenS;
    }

    public IStmt getElseS() {
        return elseS;
    }

    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;
    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMyExeStack stk = state.getExeStack();
        var symTable = state.getSymTable();
        var condition = exp.eval(symTable, state.getHeap());
        if (!condition.getType().equals(new BoolType())) {
            throw new MyException("The condition of IF is not a boolean.");
        }
        BoolValue boolCondition = (BoolValue) condition;
        if (boolCondition.getVal()) {
            stk.push(thenS);
        } else {
            stk.push(elseS);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typExp = exp.typeCheck(typeEnv);
        if (typExp instanceof BoolType) {
            thenS.typeCheck(typeEnv.deepCopy());
            elseS.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MyException("The condition of IF is not a boolean.");
        }
    }
    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp, thenS, elseS);
    }


    public String toString(){
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString() +")ELSE("+elseS.toString()+"))";
    }
}
