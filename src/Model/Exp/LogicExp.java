package Model.Exp;

import Model.Types.BoolType;
import Model.Types.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IHeap;
import Utils.State.IMySymTbl;

public class LogicExp implements Exp{
    public Exp getE1() {
        return e1;
    }

    public Exp getE2() {
        return e2;
    }

    public int getOp() {
        return op;
    }

    private Exp e1;
    private Exp e2;
    int op; //1-and, 2-or, 3-not
    public LogicExp(char c, Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        if (c == '&')
            op = 1;
        if (c == '|')
            op = 2;
        if (c == '!')
            op = 3;
    }
    @Override
    public Value eval(IMySymTbl tbl,  IHeap hp) throws MyException {
        Value v1,v2;
        v1 = e1.eval(tbl,hp);
        if (op == 3){
            if (v1.getType().equals(new BoolType())){
                BoolValue b = (BoolValue)v1;
                return new BoolValue(!b.getVal());
            }
            else
                throw new MyException("Operand is not a boolean");
        }
        v2 = e2.eval(tbl,hp);
        if (v1.getType().equals(new BoolType()) && v2.getType().equals(new BoolType())){
            BoolValue b1 = (BoolValue)v1;
            BoolValue b2 = (BoolValue)v2;
            if (op == 1)
                return new BoolValue(b1.getVal() && b2.getVal());
            if (op == 2)
                return new BoolValue(b1.getVal() || b2.getVal());
        }
        throw new MyException("Operands are not boolean");
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1, t2;
        t1 = e1.typeCheck(typeEnv);
        t2 = e2.typeCheck(typeEnv);
        if (t1.equals(new BoolType())){
            if (t2.equals(new BoolType())){
                if (op == 3)
                    return new BoolType();
                else
                    return new BoolType();
            }
            else
                throw new MyException("Second operand is not a boolean");
        }
        else
            throw new MyException("First operand is not a boolean");
    }
    @Override
    public Exp deepCopy() {
        char c='&';
        if (op == 1)
            c = '&';
        if (op == 1)
            c = '|';
        if (op == 1)
            c = '!';
        return new LogicExp(c, e1.deepCopy(), e2.deepCopy());
    }
}
