package Utils.Programs;

import Model.Exp.*;
import Model.Stmt.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

public class All {
    IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
            new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                    new PrintStmt(new VarExp("v"))));
    IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
            new CompStmt(new VarDeclStmt("b",new IntType()),
                    new CompStmt(new AssignStmt("a",
                            new ArithExp('+',new ValueExp(new IntValue(2)),
                                    new ArithExp('*',new ValueExp(new IntValue(3)),
                                            new ValueExp(new IntValue(5))))),
                            new CompStmt(new AssignStmt("b",
                                    new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
                                    new PrintStmt(new VarExp("b"))))));
    IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
            new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                            new CompStmt(new IfStmt(new VarExp("a"),
                                    new AssignStmt("v",new ValueExp(new IntValue(2))),
                                    new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                    new PrintStmt(new VarExp("v"))))));
    IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",
            new ValueExp(new StringValue("/Users/maraielciu/Desktop/map_projects/toy_language_interpreter/src/View/test.in"))), new CompStmt(new OpenRFile(new VarExp("varf")),
            new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"),
                    "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(
                    new ReadFile(new VarExp("varf"), "varc"), new CompStmt(
                    new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
    // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)
    IStmt ex5 = new CompStmt(
            new VarDeclStmt("v", new RefType(new IntType())), // v: Ref int
            new CompStmt(
                    new NewStmt("v", new ValueExp(new IntValue(20))), // Initialize v with Ref int (20)
                    new CompStmt(
                            new VarDeclStmt("a", new RefType(new RefType(new IntType()))), // a: Ref Ref int
                            new CompStmt(
                                    new NewStmt("a", new VarExp("v")), // Assign a = Ref of v (Ref Ref int)
                                    new CompStmt(
                                            new PrintStmt(new VarExp("v")), // Print v
                                            new PrintStmt(new VarExp("a"))  // Print a
                                    )
                            )
                    )
            )
    );
    // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)
    IStmt ex6 = new CompStmt(
            new VarDeclStmt("v", new RefType(new IntType())), // v is Ref Int
            new CompStmt(
                    new NewStmt("v", new ValueExp(new IntValue(20))), // v = new(20), Ref Int
                    new CompStmt(
                            new VarDeclStmt("a", new RefType(new RefType(new IntType()))), // a is Ref Ref Int
                            new CompStmt(
                                    new NewStmt("a", new VarExp("v")), // a = new(v), Ref Ref Int
                                    new CompStmt(
                                            new PrintStmt(new ReadHeapExp(new VarExp("v"))), // Print heap value of v
                                            new PrintStmt(new ArithExp(
                                                    '+',
                                                    new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), // Double dereference
                                                    new ValueExp(new IntValue(5)) // Add 5
                                            ))
                                    )
                            )
                    )
            )
    );
    // Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
    IStmt ex7 = new CompStmt(
            new VarDeclStmt("v", new RefType(new IntType())),  // v is Ref Int
            new CompStmt(
                    new NewStmt("v", new ValueExp(new IntValue(20))),  // new(v, 20)
                    new CompStmt(
                            new PrintStmt(new ReadHeapExp(new VarExp("v"))),  // print rH(v)
                            new CompStmt(
                                    new WriteHeapStmt(new VarExp("v"), new ValueExp(new IntValue(30))),  // wH(v, 30)
                                    new PrintStmt(
                                            new ArithExp(
                                                    '+',
                                                    new ReadHeapExp(new VarExp("v")),  // rH(v)
                                                    new ValueExp(new IntValue(5))  // + 5
                                            )
                                    )
                            )
                    )
            )
    );

    // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
    IStmt ex8=  new CompStmt(
            new VarDeclStmt("v", new IntType()), // int v;
            new CompStmt(
                    new AssignStmt("v", new ValueExp(new IntValue(4))), // v = 4;
                    new CompStmt(
                            new WhileStmt(
                                    new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))), // while (v > 0)
                                    new CompStmt(
                                            new PrintStmt(new VarExp("v")), // print(v);
                                            new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))) // v = v - 1;
                                    )
                            ),
                            new PrintStmt(new VarExp("v")) // print(v);
                    )
            )
    );
    // int v; Ref int a; v=10; new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))
    IStmt ex9 = new CompStmt(
            new VarDeclStmt("v", new IntType()), // int v;
            new CompStmt(
                    new VarDeclStmt("a", new RefType(new IntType())), // Ref int a;
                    new CompStmt(
                            new AssignStmt("v", new ValueExp(new IntValue(10))), // v = 10;
                            new CompStmt(
                                    new NewStmt("a", new ValueExp(new IntValue(22))), // new(a,22);
                                    new CompStmt(
                                            new ForkStmt(
                                                    new CompStmt(
                                                            new WriteHeapStmt(new VarExp("a"), new ValueExp(new IntValue(30))), // wH(a,30);
                                                            new CompStmt(
                                                                    new AssignStmt("v", new ValueExp(new IntValue(32))), // v=32;
                                                                    new CompStmt(
                                                                            new PrintStmt(new VarExp("v")), // print(v);
                                                                            new PrintStmt(new ReadHeapExp(new VarExp("a"))) // print(rH(a));
                                                                    )
                                                            )
                                                    )
                                            ),
                                            new CompStmt(
                                                    new PrintStmt(new VarExp("v")), // print(v);
                                                    new PrintStmt(new ReadHeapExp(new VarExp("a"))) // print(rH(a));
                                            )
                                    )
                            )
                    )
            )
    );
}
