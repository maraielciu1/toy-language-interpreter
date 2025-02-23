package Model;

import Model.Stmt.CompStmt;
import Model.Stmt.IStmt;
import Model.Stmt.NopStmt;
import Utils.ADT.*;
import Utils.State.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class PrgState {
    public IMyExeStack getExeStack() {
        return exeStack;
    }

    public IMySymTbl getSymTable() {
        return symTables.peek();
    }

    public Stack<IMySymTbl> getAllSymTables(){ return symTables;}

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
    //private IMySymTbl symTable;
    private Stack<IMySymTbl> symTables;
    private IMyOut out;
    private IStmt originalProgram;
    private IMyFileTbl fileTable;
    private IHeap heap;
    private IProceduresTbl procTbl;

    public int getId() {
        return id;
    }

    static int nextId=0;
    int id;

    public PrgState(IMyExeStack stk, IMySymTbl symtbl,IMyFileTbl filetbl, IHeap hp,IMyOut ot, IProceduresTbl ptbl, IStmt prg) {
        exeStack = stk;
        id=this.nextId();
        symTables = new Stack<>();
        symTables.push(symtbl);
        fileTable = filetbl;
        out = ot;
        heap = hp;
        originalProgram = prg;
        procTbl=ptbl;
        stk.push(prg);
    }

    @Override
    public String toString() {
        return "Program id: "+ id +"\n"+"ExeStack: " + distinctStatamentsString() + "\nSymTable: " + symTablesToString() + "\nOut: " + out.toString() + "\nFileTable: "+fileTable.getFileTableList() + heap.toString() + procTblToString();
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

    public String symTablesToString(){
        StringBuilder returnString = new StringBuilder();
        if(symTables.empty())
            return returnString.toString()+'\n';
        Stack<IMySymTbl> stack = new Stack<>();
        while(!symTables.empty()){
            if(symTables.peek() instanceof IStmt)
                returnString.append((symTables.peek()).toString()).append('\n');
            else
                returnString.append(symTables.peek().toString()).append('\n');
            stack.push(symTables.pop());
        }
        while(!stack.empty()){
            symTables.push(stack.pop());
        }
        return returnString.toString();
    }

    public IProceduresTbl getProcTbl() {
        return procTbl;
    }

    public String procTblToString(){
        return "\nProceduresTable: "+procTbl.toString();
    }
}
