import Model.Exp.*;
import Model.PrgState;
import Model.Stmt.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.RefValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Utils.ADT.MyDictionary;
import Utils.ADT.MyList;
import Utils.ADT.MyStack;
import Repository.Repository;
import Controller.Controller;
import Utils.State.*;
import View.Command.ExitCommand;
import View.Command.RunExample;
import View.TextMenu;

import static java.lang.Math.random;


public class Interpreter {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
//        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
//                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
//                        new PrintStmt(new VarExp("v"))));
//        try{
//            ex1.typeCheck(new MyDictionary<String, Model.Types.Type>());
//            PrgState prg1 = new PrgState(new MyExeStack(), new MySymTbl(), new MyFileTbl(), new Heap(),new MyOut(), ex1);
//            IRepository repo1 = new Repository(prg1, "log1.txt");
//            Controller controller1 = new Controller(repo1);
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
//                new CompStmt(new VarDeclStmt("b",new IntType()),
//                        new CompStmt(new AssignStmt("a",
//                                new ArithExp('+',new ValueExp(new IntValue(2)),
//                                        new ArithExp('*',new ValueExp(new IntValue(3)),
//                                                new ValueExp(new IntValue(5))))),
//                                new CompStmt(new AssignStmt("b",
//                                        new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
//                                        new PrintStmt(new VarExp("b"))))));
//        try{
//            ex2.typeCheck(new MyDictionary<String, Model.Types.Type>());
//            PrgState prg2 = new PrgState(new MyExeStack(), new MySymTbl(), new MyFileTbl(),new Heap(), new MyOut(), ex2);
//            IRepository repo2 = new Repository(prg2, "log2.txt");
//            Controller controller2 = new Controller(repo2);
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
//                new CompStmt(new VarDeclStmt("v", new IntType()),
//                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
//                                new CompStmt(new IfStmt(new VarExp("a"),
//                                        new AssignStmt("v",new ValueExp(new IntValue(2))),
//                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
//                                        new PrintStmt(new VarExp("v"))))));
//        try{
//            ex3.typeCheck(new MyDictionary<String, Model.Types.Type>());
//            PrgState prg3 = new PrgState(new MyExeStack(), new MySymTbl(), new MyFileTbl(),new Heap(), new MyOut(), ex3);
//            IRepository repo3 = new Repository(prg3, "log3.txt");
//            Controller controller3 = new Controller(repo3);
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",
//                new ValueExp(new StringValue("/Users/maraielciu/Desktop/map_projects/toy_language_interpreter/src/View/test.in"))), new CompStmt(new OpenRFile(new VarExp("varf")),
//                new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"),
//                        "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(
//                        new ReadFile(new VarExp("varf"), "varc"), new CompStmt(
//                        new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
//        try{
//            ex4.typeCheck(new MyDictionary<String, Model.Types.Type>());
//            PrgState prg4 = new PrgState(new MyExeStack(), new MySymTbl(), new MyFileTbl(),new Heap(), new MyOut(), ex4);
//            IRepository repo4 = new Repository(prg4, "log4.txt");
//            Controller controller4 = new Controller(repo4);
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)
//        IStmt ex5 = new CompStmt(
//                new VarDeclStmt("v", new RefType(new StringType())), // v: Ref int
//                new CompStmt(
//                        new NewStmt("v", new ValueExp(new IntValue(20))), // Initialize v with Ref int (20)
//                        new CompStmt(
//                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), // a: Ref Ref int
//                                new CompStmt(
//                                        new NewStmt("a", new VarExp("v")), // Assign a = Ref of v (Ref Ref int)
//                                        new CompStmt(
//                                                new PrintStmt(new VarExp("v")), // Print v
//                                                new PrintStmt(new VarExp("a"))  // Print a
//                                        )
//                                )
//                        )
//                )
//        );
//
//        try{
//            ex5.typeCheck(new MyDictionary<String, Model.Types.Type>());
//            PrgState prg5 = new PrgState(new MyExeStack(), new MySymTbl(), new MyFileTbl(),new Heap(), new MyOut(), ex5);
//            IRepository repo5 = new Repository(prg5, "log5.txt");
//            Controller controller5 = new Controller(repo5);
//            menu.addCommand(new RunExample("1", ex5.toString(), controller5));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)
//        IStmt ex6 = new CompStmt(
//                new VarDeclStmt("v", new RefType(new IntType())), // v is Ref Int
//                new CompStmt(
//                        new NewStmt("v", new ValueExp(new IntValue(20))), // v = new(20), Ref Int
//                        new CompStmt(
//                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), // a is Ref Ref Int
//                                new CompStmt(
//                                        new NewStmt("a", new VarExp("v")), // a = new(v), Ref Ref Int
//                                        new CompStmt(
//                                                new PrintStmt(new ReadHeapExp(new VarExp("v"))), // Print heap value of v
//                                                new PrintStmt(new ArithExp(
//                                                        '+',
//                                                        new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), // Double dereference
//                                                        new ValueExp(new IntValue(5)) // Add 5
//                                                ))
//                                        )
//                                )
//                        )
//                )
//        );
//
//        try{
//        ex6.typeCheck(new MyDictionary<String, Model.Types.Type>());
//            PrgState prg6 = new PrgState(new MyExeStack(), new MySymTbl(), new MyFileTbl(),new Heap(), new MyOut(), ex6);
//            IRepository repo6 = new Repository(prg6, "log6.txt");
//            Controller controller6 = new Controller(repo6);
//            menu.addCommand(new RunExample("2", ex6.toString(), controller6));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        // Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
//        IStmt ex7 = new CompStmt(
//                new VarDeclStmt("v", new RefType(new IntType())),  // v is Ref Int
//                new CompStmt(
//                        new NewStmt("v", new ValueExp(new IntValue(20))),  // new(v, 20)
//                        new CompStmt(
//                                new PrintStmt(new ReadHeapExp(new VarExp("v"))),  // print rH(v)
//                                new CompStmt(
//                                        new WriteHeapStmt(new VarExp("v"), new ValueExp(new IntValue(30))),  // wH(v, 30)
//                                        new PrintStmt(
//                                                new ArithExp(
//                                                        '+',
//                                                        new ReadHeapExp(new VarExp("v")),  // rH(v)
//                                                        new ValueExp(new IntValue(5))  // + 5
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//
//        try{
//            ex7.typeCheck(new MyDictionary<String, Model.Types.Type>());
//            PrgState prg7 = new PrgState(new MyExeStack(), new MySymTbl(), new MyFileTbl(),new Heap(), new MyOut(), ex7);
//            IRepository repo7 = new Repository(prg7, "log7.txt");
//            Controller controller7 = new Controller(repo7);
//            menu.addCommand(new RunExample("3", ex7.toString(), controller7));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
//        IStmt ex8= new CompStmt(
//                new VarDeclStmt("v", new IntType()), // int v;
//                new CompStmt(
//                        new AssignStmt("v", new ValueExp(new IntValue(4))), // v = 4;
//                        new CompStmt(
//                                new WhileStmt(
//                                        new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))), // while (v > 0)
//                                        new CompStmt(
//                                                new PrintStmt(new VarExp("v")), // print(v);
//                                                new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))) // v = v - 1;
//                                        )
//                                ),
//                                new PrintStmt(new VarExp("v")) // print(v);
//                        )
//                )
//        );
//        try{
//            ex8.typeCheck(new MyDictionary<String, Model.Types.Type>());
//            PrgState prg8 = new PrgState(new MyExeStack(), new MySymTbl(), new MyFileTbl(),new Heap(), new MyOut(), ex8);
//            IRepository repo8 = new Repository(prg8, "log8.txt");
//            Controller controller8 = new Controller(repo8);
//            menu.addCommand(new RunExample("4", ex8.toString(), controller8));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        // int v; Ref int a; v=10; new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))
//        IStmt ex9 = new CompStmt(
//                new VarDeclStmt("v", new IntType()),  // int v
//                new CompStmt(
//                        new VarDeclStmt("a", new RefType(new IntType())),  // Ref int a
//                        new CompStmt(
//                                new AssignStmt("v", new ValueExp(new IntValue(10))),  // v = 10
//                                new CompStmt(
//                                        new NewStmt("a", new ValueExp(new IntValue(22))),  // new(a, 22)
//                                        new CompStmt(
//                                                new ForkStmt(
//                                                        new CompStmt(
//                                                                new WriteHeapStmt(new VarExp("a"), new ValueExp(new IntValue(30))),  // wH(a, 30)
//                                                                new CompStmt(
//                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),  // v = 32
//                                                                        new CompStmt(
//                                                                                new PrintStmt(new VarExp("v")),  // print v
//                                                                                new PrintStmt(new ReadHeapExp(new VarExp("a")))  // print rH(a)
//                                                                        )
//                                                                )
//                                                        )
//                                                ),
//                                                new CompStmt(
//                                                        new PrintStmt(new VarExp("v")),  // print v
//                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))  // print rH(a)
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//
//        try{
//            ex9.typeCheck(new MyDictionary<String, Model.Types.Type>());
//            PrgState prg9 = new PrgState(new MyExeStack(), new MySymTbl(), new MyFileTbl(),new Heap(), new MyOut(), ex9);
//            IRepository repo9 = new Repository(prg9, "log9.txt");
//            Controller controller9 = new Controller(repo9);
//            menu.addCommand(new RunExample("5", ex9.toString(), controller9));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
        //Ref int v1; Ref int v2; int x; int q; new(v1,20);new(v2,30);newLock(x);
        // fork( fork( lock(x);wh(v1,rh(v1)-1);unlock(x) ); lock(x);wh(v1,rh(v1)*10);unlock(x) );newLock(q);
        // fork( fork(lock(q);wh(v2,rh(v2)+5);unlock(q)); lock(q);wh(v2,rh(v2)*10);unlock(q) );
        // nop;nop;nop;nop; lock(x); print(rh(v1)); unlock(x); lock(q); print(rh(v2)); unlock(q);
        IStmt ex12 = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("x", new IntType()),
                                new CompStmt(
                                        new VarDeclStmt("q", new IntType()),
                                        new CompStmt(
                                                new NewStmt("v1", new ValueExp(new IntValue(20))),
                                                new CompStmt(
                                                        new NewStmt("v2", new ValueExp(new IntValue(30))),
                                                        new CompStmt(
                                                                new NewLockStmt("x"),
                                                                new CompStmt(
                                                                        new ForkStmt(
                                                                                new CompStmt(
                                                                                        new ForkStmt(
                                                                                                new CompStmt(
                                                                                                        new LockStmt("x"),
                                                                                                        new CompStmt(
                                                                                                                new WriteHeapStmt(new VarExp("v1"),
                                                                                                                        new ArithExp('-',
                                                                                                                                new ReadHeapExp(new VarExp("v1")),
                                                                                                                                new ValueExp(new IntValue(1)))
                                                                                                                ),
                                                                                                                new UnlockStmt("x")
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStmt(
                                                                                                new LockStmt("x"),
                                                                                                new CompStmt(
                                                                                                        new WriteHeapStmt(new VarExp("v1"),
                                                                                                                new ArithExp('*',
                                                                                                                        new ReadHeapExp(new VarExp("v1")),
                                                                                                                        new ValueExp(new IntValue(10))
                                                                                                                )
                                                                                                        ),
                                                                                                        new UnlockStmt("x")
                                                                                                )
                                                                                        )
                                                                                )
                                                                        ),
                                                                        new CompStmt(
                                                                                new NewLockStmt("q"),
                                                                                new CompStmt(
                                                                                        new ForkStmt(
                                                                                                new CompStmt(
                                                                                                        new ForkStmt(
                                                                                                                new CompStmt(
                                                                                                                        new LockStmt("q"),
                                                                                                                        new CompStmt(
                                                                                                                                new WriteHeapStmt(new VarExp("v2"),
                                                                                                                                        new ArithExp('+',
                                                                                                                                                new ReadHeapExp(new VarExp("v2")),
                                                                                                                                                new ValueExp(new IntValue(5))
                                                                                                                                        )
                                                                                                                                ),
                                                                                                                                new UnlockStmt("q")
                                                                                                                        )
                                                                                                                )
                                                                                                        ),
                                                                                                        new CompStmt(
                                                                                                                new LockStmt("q"),
                                                                                                                new CompStmt(
                                                                                                                        new WriteHeapStmt(new VarExp("v2"),
                                                                                                                                new ArithExp('*',
                                                                                                                                        new ReadHeapExp(new VarExp("v2")),
                                                                                                                                        new ValueExp(new IntValue(10))
                                                                                                                                )
                                                                                                                        ),
                                                                                                                        new UnlockStmt("q")
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStmt(
                                                                                                new NopStmt(),
                                                                                                new CompStmt(
                                                                                                        new NopStmt(),
                                                                                                        new CompStmt(
                                                                                                                new NopStmt(),
                                                                                                                new CompStmt(
                                                                                                                        new NopStmt(),
                                                                                                                        new CompStmt(
                                                                                                                                new LockStmt("x"),
                                                                                                                                new CompStmt(
                                                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                                                        new CompStmt(
                                                                                                                                                new UnlockStmt("x"),
                                                                                                                                                new CompStmt(
                                                                                                                                                        new LockStmt("q"),
                                                                                                                                                        new CompStmt(
                                                                                                                                                                new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                                                                                                                                new UnlockStmt("q")
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        try{
            ex12.typeCheck(new MyDictionary<String, Model.Types.Type>());
            PrgState prg9 = new PrgState(new MyExeStack(), new MySymTbl(), new MyFileTbl(),new Heap(), new MyOut(), new LockTable(), ex12);
            IRepository repo9 = new Repository(prg9, "log12.txt");
            Controller controller9 = new Controller(repo9);
            menu.addCommand(new RunExample("5", ex12.toString(), controller9));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        menu.show();

    }
}
