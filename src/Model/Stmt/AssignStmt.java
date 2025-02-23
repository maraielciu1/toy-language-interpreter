package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.ADT.MyIStack;
import Utils.State.IMyExeStack;
import Utils.State.IMySymTbl;
import Utils.State.MyExeStack;

public class AssignStmt implements IStmt{
    private String id;
    private Exp exp;
    public AssignStmt(String i, Exp e){
        id=i;
        exp=e;
    }

    public String toString(){
        return id+"="+ exp.toString();
    }
    public PrgState execute(PrgState state) throws MyException{
        IMyExeStack stk=state.getExeStack();
        IMySymTbl symTbl= state.getSymTable();
        if (symTbl.contains(id)){
            Value val = exp.eval(symTbl,state.getHeap());
            Type typId=(symTbl.lookUp(id)).getType();
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else
                throw new MyException("declared type of variable"+id+" and type of the assigned expression do not match");
        }
        else
            throw new MyException("the used variable" +id + " was not declared before");
        return null;

    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typId = typeEnv.lookUp(id);
        Type typExp = exp.typeCheck(typeEnv);
        if (typId.equals(typExp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id,exp.deepCopy());
    }
}
