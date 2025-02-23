package Model.Types;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type{
    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }
    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}
