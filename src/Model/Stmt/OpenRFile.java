package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Value.StringValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt{
    private Exp exp;
    private StringType s;
    public OpenRFile(Exp exp){
        this.exp = exp;
        s=new StringType();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
//        Value val = exp.eval(state.getSymTable());
//        if(val.getType().equals(s)){
//            StringValue file = (StringValue) val;
//            if(state.getFileTable().contains(file)){
//                throw new MyException("File already opened");
//            }
//            try{
//                BufferedReader reader = new BufferedReader(new FileReader(file.getVal()));
//                state.getFileTable().put(file, reader);
//            } catch (IOException e) {
//                throw new MyException("File not found");
//            }
//        } else {
//            throw new MyException("Expression is not a string");
//        }
        Value val=exp.eval(state.getSymTable(),state.getHeap());
        if(!(val.getType().equals(s))){
            throw new MyException("The expression is not a string.");
        }
        StringValue file=(StringValue)val;
        state.getFileTable().openFile(file);
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
    public String toString()
    {
        return "OpenRFile("+exp.toString()+")";
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }
}

