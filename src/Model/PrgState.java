package Model;

import Model.Stmt.CompStmt;
import Model.Stmt.IStmt;
import Model.Stmt.NopStmt;
import Utils.ADT.*;
import Utils.State.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PrgState {
    public IMyExeStack getExeStack() {
        return exeStack;
    }

    public IMySymTbl getSymTable() {
        return symTable;
    }

    public IMyOut getOut() {
        return out;
    }

    public IHeap getHeap() {
        return heap;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public IBarrierTable getBarrierTbl(){return barrierTable;}

    public void setExeStack(IMyExeStack exeStack) { this.exeStack = exeStack; }

    private IMyExeStack exeStack;
    private IMySymTbl symTable;
    private IMyOut out;
    private IStmt originalProgram;
    private IMyFileTbl fileTable;
    private IHeap heap;
    private IBarrierTable barrierTable;

    public int getId() {
        return id;
    }

    static int nextId=0;
    int id;

    public PrgState(IMyExeStack stk, IMySymTbl symtbl,IMyFileTbl filetbl, IHeap hp,IMyOut ot,IBarrierTable btbl, IStmt prg) {
        exeStack = stk;
        id=this.nextId();
        symTable = symtbl;
        fileTable = filetbl;
        out = ot;
        heap = hp;
        originalProgram = prg;
        stk.push(prg);
        barrierTable=btbl;
    }

    @Override
    public String toString() {
        try {
            return "Program id: "+ id +"\n"+"ExeStack: " + distinctStatamentsString() + "\nSymTable: " + symTable.toString() + "\nOut: " + out.toString() + "\nFileTable: "+fileTable.getFileTableList() + heap.toString()+barrierTableToString();
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }

    public IMyFileTbl getFileTable() {
        return fileTable;
    }

    private Node<IStmt> toTree(IStmt stmt) {
        Node node;
        if (stmt instanceof CompStmt){
            CompStmt comptStmt = (CompStmt) stmt;
            node = new Node<>(new NopStmt());
            node.setLeft(new Node<>(comptStmt.getFirst()));
            node.setRight(toTree( comptStmt.getSecond()));
        }
        else {
            node = new Node<>(stmt);
        }
        return node;

    }
    public List<IStmt> distinctStataments() {
        MyTree<IStmt> tree =  new MyTree<IStmt>();
        List<IStmt> inOrderList=new LinkedList<IStmt>();
        if(getExeStack().toList().size()>0) {
            tree.setRoot(toTree( (IStmt)getExeStack().toList().get(0)));
            tree.inorderTraversal(inOrderList, tree.getRoot());
        }
        return inOrderList;
    }

    public String distinctStatamentsString() {
        List<IStmt> inOrderList = distinctStataments();
        StringBuilder str = new StringBuilder();
        for (IStmt stmt : inOrderList) {

            if(!Objects.equals(stmt.toString(),";")) {
                if(stmt.toString().equals(""))
                    continue;
                str.append(stmt.toString());
                str.append("\n");
            }
        }
        return str.toString();
    }

    public boolean isNotCompleted(){
        return exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException{
        if(exeStack.isEmpty())
            throw new MyException("prgstate stack is empty");
        IStmt crtStmt= exeStack.pop();
        return crtStmt.execute(this);
    }


    static synchronized int nextId(){
        nextId++;
        return nextId;
    }

    public String barrierTableToString() throws MyException {
        StringBuilder barrierTableStringBuilder = new StringBuilder();
        barrierTableStringBuilder.append("\nBarrierTable: ");
        for (int key: barrierTable.keySet()) {
            barrierTableStringBuilder.append(String.format("%d -> %d: ", key, barrierTable.lookUp(key).getKey()));
            for (int value: barrierTable.lookUp(key).getValue())
                barrierTableStringBuilder.append(String.format("%d ", value));
        }
        barrierTableStringBuilder.append("\n");
        return barrierTableStringBuilder.toString();
    }
}
