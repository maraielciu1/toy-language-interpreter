package Model.Types;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type{

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
    @Override
    public Value defaultValue() {
        return new IntValue(0);  // default value for int is 0
    }

}
