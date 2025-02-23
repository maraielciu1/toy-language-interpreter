package Model.Exp;

import Model.Types.Type;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IHeap;
import Utils.State.IMySymTbl;

public interface Exp {
    Value eval(IMySymTbl tbl, IHeap hp) throws MyException;
    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;
    Exp deepCopy();
}
