package Model.Types;

import Model.Value.RefValue;
import Model.Value.Value;

public class RefType implements Type{
    private Type inner;

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }
    @Override
    public String toString() {
        return "Ref " + this.inner.toString();
    }
    public RefType(Type inner) {
        this.inner = inner;
    }
    public Type getInner() {
        return this.inner;
    }
    public boolean equals(Type obj) {
        if (obj instanceof RefType refType) {
            return this.inner.equals(refType.getInner());
        }
        return false;
    }
}
