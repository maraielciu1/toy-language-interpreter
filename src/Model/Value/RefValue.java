package Model.Value;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value{
    private int address;
    private Type locationType;
    public RefValue(int a, Type t) {
        this.address = a;
        this.locationType = t;
    }
    @Override
    public Type getType() {
        return new RefType(this.locationType);
    }
    public int getAddress() {
        return this.address;
    }
    public String toString() {
        return "(" + Integer.toString(this.address) + ", " + this.locationType.toString() + ")";
    }
    public boolean equals(Object another) {
        return another instanceof RefValue;
    }
}
