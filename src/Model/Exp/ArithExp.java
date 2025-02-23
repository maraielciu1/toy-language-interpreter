package Model.Exp;

import Model.Types.IntType;
import Model.Types.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IHeap;
import Utils.State.IMySymTbl;

public class ArithExp implements Exp{
    Exp e1;

    public Exp getE1() {
        return e1;
    }

    public Exp getE2() {
        return e2;
    }

    public int getOp() {
        return op;
    }

    Exp e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide
    public ArithExp(char c, Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        if (c == '+')
            op = 1;
        if (c == '-')
            op = 2;
        if (c == '*')
            op = 3;
        if (c == '/')
            op = 4;
    }
    public Value eval(IMySymTbl tbl, IHeap hp) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl,hp);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == 1)
                    return new IntValue(n1 + n2);
                if (op == 2)
                    return new IntValue(n1 - n2);
                if (op == 3)
                    return new IntValue(n1 * n2);
                if (op == 4) if (n2 == 0)
                    throw new MyException("division by zero");
                else
                    return new IntValue(n1 / n2);
            }
            else
                throw new MyException("second operand is not an integer");

        }
        else
            throw new MyException("first operand is not an integer");

        return null;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1,t2;
        t1 = e1.typeCheck(typeEnv);
        t2 = e2.typeCheck(typeEnv);
        if (t1.equals(new IntType())) {
            if (t2.equals(new IntType())) {
                return new IntType();
            }
            else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString() {
        if (op == 1)
            return e1 + "+" + e2;
        if (op == 2)
            return e1 + "-" + e2;
        if (op == 3)
            return e1 + "*" + e2;
        if (op == 4)
            return e1 + "/" + e2;
        return null;
    }

    @Override
    public Exp deepCopy() {
        char c='+';
        if (op==1)
            c='+';
        if (op==2)
            c='-';
        if (op==3)
            c='*';
        if (op==4)
            c='/';
        return new ArithExp(c, e1.deepCopy(), e2.deepCopy());
    }
}
