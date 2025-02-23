package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

public class ReadFile implements IStmt{
    private Exp exp;
    private String varName;
    private IntType i;
    private StringType s;
    public ReadFile(Exp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
        i=new IntType();
        s=new StringType();
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
//        var value = exp.eval(state.getSymTable());
//        if (!state.getSymTable().contains(varName) || !state.getSymTable().lookUp(varName).getType().equals(i)) {
//            throw new MyException("The variable is not defined or the types do not match.");
//        }
//        if(!value.getType().equals(s)) {
//            throw new MyException("The expression is not a string.");
//        }
//        var buffer = state.getFileTable().lookUp((StringValue) value);
//        try {
//            String line = buffer.readLine();
//            if (line == null) {
//                state.getSymTable().update(varName, new IntType().defaultValue());
//            } else {
//                state.getSymTable().update(varName, new IntValue(Integer.parseInt(line)));
//            }
//        } catch (Exception e) {
//            throw new MyException("Error reading from file.");
//        }
        Value value=exp.eval(state.getSymTable(),state.getHeap());
        if(!(value.getType().equals(s))){
            throw new MyException("The expression is not a string.");
        }
        state.getSymTable().update(varName, new IntValue(state.getFileTable().readFile((StringValue)value)));
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
        return new ReadFile(exp, varName);
    }


    @Override
    public String toString() {
        return "ReadFile(" + exp.toString() + ", " + varName + ")";
    }
}
