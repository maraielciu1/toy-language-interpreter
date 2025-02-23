package Model.Stmt;

import Model.Exp.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IMySymTbl;
import Utils.State.MySymTbl;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class FunctionCallStmt implements IStmt{
    private String name;
    private List<Exp> parameters;

    public FunctionCallStmt(String fname, List<Exp> params){
        name=fname;
        parameters = new ArrayList<>();
        for(int i=0;i<params.size();++i){
            this.parameters.add(params.get(i));
        }
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        try{Pair<List<String>, IStmt> functionEntry = state.getProcTbl().lookUp(name);
        if(functionEntry==null)
            throw new MyException("Function "+name+" not found");
        List<String> functionParams = functionEntry.getKey();
        IStmt functionBody = functionEntry.getValue();
        List<Value> paramValues = new Vector<Value>();
        for(int i=0;i<parameters.size();++i){
            paramValues.add(parameters.get(i).eval(state.getSymTable(), state.getHeap()));
        }
        IMySymTbl newSymTbl = new MySymTbl();
        for(int i=0;i<functionParams.size();++i){
            newSymTbl.put(functionParams.get(i), paramValues.get(i));
        }
        state.getAllSymTables().push(newSymTbl);
        state.getExeStack().push(new FunctionReturnStmt());
        state.getExeStack().push(functionBody);}
        catch (MyException e){
            throw new MyException(e.getMessage());
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new FunctionCallStmt(name, parameters);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("call ").append(name).append("(");
        for(int i=0;i<parameters.size();++i){
            str.append(parameters.get(i).toString());
            if(i<parameters.size()-1)
                str.append(", ");
        }
        str.append(")");
        return str.toString();
    }
}
