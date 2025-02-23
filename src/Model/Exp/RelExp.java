package Model.Exp;

import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IHeap;
import Utils.State.IMySymTbl;

import java.util.Objects;

public class RelExp implements Exp{
    private Exp e1;
    private Exp e2;
    private int op; //1-<, 2-<=, 3-==, 4-!=, 5->, 6->=
    public RelExp(String c, Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        if (Objects.equals(c, "<"))
            op = 1;
        if (Objects.equals(c, "<="))
            op = 2;
        if (Objects.equals(c, "=="))
            op = 3;
        if (Objects.equals(c, "!="))
            op = 4;
        if (Objects.equals(c, ">"))
            op = 5;
        if (Objects.equals(c, ">="))
            op = 6;
    }
    @Override
    public Value eval(IMySymTbl tbl, IHeap hp) throws MyException {
        Value v1 = e1.eval(tbl,hp);
        Value v2 = e2.eval(tbl,hp);

        if (v1 instanceof IntValue && v2 instanceof IntValue) {
            IntValue int1 = (IntValue) v1;
            IntValue int2 = (IntValue) v2;
            int n1 = int1.getVal();
            int n2 = int2.getVal();

            switch (op) {
                case 1:
                    return new BoolValue(n1 - n2 < 0);
                case 2:
                    return new BoolValue(n1 - n2 <= 0);
                case 3:
                    return new BoolValue(n1 - n2 == 0);
                case 4:
                    return new BoolValue(n1 - n2 != 0);
                case 5:
                    return new BoolValue(n1 - n2 > 0);
                case 6:
                    return new BoolValue(n1 - n2 >= 0);
                default:
                    throw new MyException("Invalid operation code");
            }
        }
        throw new MyException("Operands are not of type IntValue");
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = e1.typeCheck(typeEnv);
        Type t2 = e2.typeCheck(typeEnv);
        if (t1.equals(new IntType()) && t2.equals(new IntType())) {
            return new BoolType();
        }
        throw new MyException("Operands are not of type IntValue");
    }

    @Override
    public String toString() {
        return e1.toString() + " " + switch (op) {
            case 1 -> "<";
            case 2 -> "<=";
            case 3 -> "==";
            case 4 -> "!=";
            case 5 -> ">";
            case 6 -> ">=";
            default -> throw new IllegalStateException("Unexpected value: " + op);
        } + " " + e2.toString();
    }
    @Override
    public Exp deepCopy() {
        return new RelExp(switch (op) {
            case 1 -> "<";
            case 2 -> "<=";
            case 3 -> "==";
            case 4 -> "!=";
            case 5 -> ">";
            case 6 -> ">=";
            default -> throw new IllegalStateException("Unexpected value: " + op);
        }, e1.deepCopy(), e2.deepCopy());
    }
}
