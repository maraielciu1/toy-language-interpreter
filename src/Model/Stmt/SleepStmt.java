package Model.Stmt;
import Model.PrgState;
import Model.Types.Type;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

public class SleepStmt implements IStmt {
    int number;
    public SleepStmt(int number) {
        this.number = number;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(number>0)
            state.getExeStack().push(new SleepStmt(number-1));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(number);
    }

    @Override
    public String toString() {
        return "sleep(" + number+")";
    }
}
