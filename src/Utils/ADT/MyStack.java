package Utils.ADT;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    private Stack<T> stack;
    public MyStack() {
        stack = new Stack<>();
    }
    @Override
    public T pop() throws MyException {
        if(stack.isEmpty()) {
            throw new MyException("Stack is empty");
        }
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    public MyStack<T> deepCopy() {
        MyStack<T> newStack = new MyStack<T>();
        for(T elem : stack) {
            newStack.push(elem);
        }
        return newStack;
    }
    public List<T> toListS() {
        return  (List<T>) Arrays.asList(stack.toArray());
    }
}
