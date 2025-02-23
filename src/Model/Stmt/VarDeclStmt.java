package Model.Stmt;

import Model.PrgState;
import Model.Types.Type;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IMySymTbl;

public class VarDeclStmt implements IStmt{
    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    private String name;
    private Type type;
    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMySymTbl symTable = state.getSymTable();
        if (symTable.contains(name)) {
            throw new MyException("Variable " + name + " is already declared.");
        }
        symTable.put(name, type.defaultValue());
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name, type); // Add the declared variable type to the symbol table
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type);
    }


    @Override
    public String toString() {
        return type + " " + name;
    }
}
