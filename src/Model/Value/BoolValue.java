package Model.Value;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value{
    boolean val;
    public BoolValue(boolean v) {
        val = v;
    }
    public boolean getVal() {
        return val;
    }
    @Override
    public Type getType() {
        return new BoolType();
    }
    @Override
    public String toString() {
        return Boolean.toString(val);
    }
    @Override
    public boolean equals(Object another) {
        return another instanceof BoolValue;
    }
}
