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

    public void setExeStack(IMyExeStack exeStack) { this.exeStack = exeStack; }

    private IMyExeStack exeStack;
    private IMySymTbl symTable;
    private IMyOut out;
    private IStmt originalProgram;
    private IMyFileTbl fileTable;
    private IHeap heap;
    private ILockTable lockTable;

    public int getId() {
        return id;
    }

    static int nextId=0;
    int id;

    public PrgState(IMyExeStack stk, IMySymTbl symtbl,IMyFileTbl filetbl, IHeap hp,IMyOut ot, ILockTable lktbl, IStmt prg) {
        exeStack = stk;
        id=this.nextId();
        symTable = symtbl;
        fileTable = filetbl;
        out = ot;
        heap = hp;
        originalProgram = prg.deepCopy();
        stk.push(prg);
        lockTable = lktbl;
    }

    @Override
    public String toString() {
        return "Program id: "+ id +"\n"+"ExeStack: " + distinctStatamentsString() + "\nSymTable: " + symTable.toString() + "\nOut: " + out.toString() + "\nFileTable: "+fileTable.getFileTableList() + heap.toString() + "\nLockTable: " + lockTable.toString() + "\n";
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

    public ILockTable getLockTable() {
        return lockTable;
    }

    public String lockTableToString(){
        StringBuilder result = new StringBuilder();
        result.append("\nLockTable: ");
        result.append(lockTable.toString());
        return result.toString();
    }
}
