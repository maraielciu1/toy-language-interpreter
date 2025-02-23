package Utils.State;

import Model.Stmt.IStmt;
import Utils.ADT.MyException;
import Utils.ADT.MyIStack;
import Utils.ADT.MyStack;

import java.util.ArrayList;
import java.util.List;

public class MyExeStack implements IMyExeStack{
    MyIStack<IStmt> stack;
    public MyExeStack(){
        this.stack=new MyStack<>();
    }
    @Override
    public IStmt pop() throws MyException {
        return stack.pop();
    }

    @Override
    public void push(IStmt i) {
        stack.push(i);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<IStmt> toList() {
        return stack.toListS();
    }

    @Override
    public IStmt[] getReversed() {
        List<IStmt> reversed = new ArrayList<>();
        for (IStmt stmt : stack.toListS()) {
            reversed.add(0, stmt);
        }
        return reversed.toArray(new IStmt[0]);
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
