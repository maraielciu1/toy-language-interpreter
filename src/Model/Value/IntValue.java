package Model.Value;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value{
    public int getVal() {
        return val;
    }
    private int val;
    public IntValue(int v) {val=v;}
    @Override
    public Type getType() {
        return new IntType();
    }
    public String toString() {
        return Integer.toString(val);
    }
    public boolean equals(Object another) {
        return another instanceof IntValue;
    }
}
