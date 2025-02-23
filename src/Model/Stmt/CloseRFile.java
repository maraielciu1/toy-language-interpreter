package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Value.StringValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

public class CloseRFile implements IStmt{
    private Exp exp;
    private StringType s;
    public CloseRFile(Exp exp) {
        this.exp = exp;
        s=new StringType();
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
//        var value = exp.eval(state.getSymTable());
//        if (!value.getType().equals(s)) {
//            throw new MyException("The expression is not a string.");
//        }
//        var buffer = state.getFileTable().lookUp((StringValue) value);
//        if (buffer == null) {
//            throw new MyException("The file is not opened.");
//        }
//        try {
//            buffer.close();
//            state.getFileTable().remove((StringValue) value);
//        } catch (Exception e) {
//            throw new MyException("Error closing the file.");
//        }
        Value value=exp.eval(state.getSymTable(),state.getHeap());
        if(!(value.getType().equals(s))){
            throw new MyException("The expression is not a string.");
        }
        state.getFileTable().closeFile((StringValue)value);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = exp.typeCheck(typeEnv);
        if (typ.equals(s)) {
            return typeEnv;
        } else {
            throw new MyException("The expression is not a string.");
        }
    }
    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp);
    }


    @Override
    public String toString() {
        return "CloseRFile(" + exp.toString() + ")";
    }
}
