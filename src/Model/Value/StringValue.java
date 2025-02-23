package Model.Value;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value{
    public String getVal() {
        return val;
    }
    public StringValue(String v) {
        val = v;
    }
    private String val;
    @Override
    public Type getType() {
        return new StringType();
    }
    @Override
    public String toString() {
        return val;
    }
    @Override
    public boolean equals(Object another) {
        return another instanceof StringValue;
    }
}
