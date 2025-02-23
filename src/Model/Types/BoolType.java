package Model.Types;

import Model.Value.BoolValue;
import Model.Value.Value;

public class BoolType implements Type{
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }
    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
