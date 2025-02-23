package Gui;

import Controller.Controller;
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
import Utils.ADT.MyException;
import Utils.State.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import Repository.Repository;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramChooser {
    @FXML
    private ProgramExecutor programExecutor;
    @FXML
    private ListView<IStmt> programListView;
    @FXML
    private Button selectButton;

    @FXML
    private ObservableList<IStmt> getAllPrograms(){
        List<IStmt> allPrgs = new ArrayList<>();
        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        allPrgs.add(ex1);
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a",
                                new ArithExp('+',new ValueExp(new IntValue(2)),
                                        new ArithExp('*',new ValueExp(new IntValue(3)),
                                                new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",
                                        new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));
        allPrgs.add(ex2);
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v",new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
        allPrgs.add(ex3);
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",
                new ValueExp(new StringValue("/Users/maraielciu/Desktop/map_projects/toy_language_interpreter copy/src/View/test.in"))), new CompStmt(new OpenRFile(new VarExp("varf")),
                new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"),
                        "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(
                        new ReadFile(new VarExp("varf"), "varc"), new CompStmt(
                        new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
        allPrgs.add(ex4);
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
        allPrgs.add(ex5);
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
        allPrgs.add(ex6);
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
        allPrgs.add(ex7);
        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStmt ex8= new CompStmt(
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
        allPrgs.add(ex8);
        // int v; Ref int a; v=10; new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))
        IStmt ex9 = new CompStmt(
                new VarDeclStmt("v", new IntType()),  // int v
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),  // Ref int a
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),  // v = 10
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),  // new(a, 22)
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new WriteHeapStmt(new VarExp("a"), new ValueExp(new IntValue(30))),  // wH(a, 30)
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),  // v = 32
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),  // print v
                                                                                new PrintStmt(new ReadHeapExp(new VarExp("a")))  // print rH(a)
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),  // print v
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))  // print rH(a)
                                                )
                                        )
                                )
                        )
                )
        );
        allPrgs.add(ex9);

        //Ref int v1; Ref int v2; Ref int v3; int cnt;
        // new(v1,2);new(v2,3);new(v3,4);newLatch(cnt,rH(v2));
        // fork(wh(v1,rh(v1)*10));print(rh(v1));countDown(cnt);
        // fork(wh(v2,rh(v2)*10));print(rh(v2));countDown(cnt);
        // fork(wh(v3,rh(v3)*10));print(rh(v3));countDown(cnt))));
        // await(cnt); print(100); countDown(cnt); print(100);
//        IStmt ex12 = new CompStmt(
//                new VarDeclStmt("v1", new RefType(new IntType())), // Ref int v1
//                new CompStmt(
//                        new VarDeclStmt("v2", new RefType(new IntType())), // Ref int v2
//                        new CompStmt(
//                                new VarDeclStmt("v3", new RefType(new IntType())), // Ref int v3 (fixed)
//                                new CompStmt(
//                                        new VarDeclStmt("cnt", new IntType()), // int cnt
//                                        new CompStmt(
//                                                new NewStmt("v1", new ValueExp(new IntValue(2))), // new(v1, 2)
//                                                new CompStmt(
//                                                        new NewStmt("v2", new ValueExp(new IntValue(3))), // new(v2, 3)
//                                                        new CompStmt(
//                                                                new NewStmt("v3", new ValueExp(new IntValue(4))), // new(v3, 4)
//                                                                new CompStmt(
//                                                                        new NewLatchStmt("cnt", new ReadHeapExp(new VarExp("v2"))), // newLatch(cnt, rH(v2))
//                                                                        new CompStmt(
//                                                                                new ForkStmt(
//                                                                                        new CompStmt(
//                                                                                                new WriteHeapStmt(
//                                                                                                        new VarExp("v1"),
//                                                                                                        new ArithExp('*',
//                                                                                                                new ReadHeapExp(new VarExp("v1")),
//                                                                                                                new ValueExp(new IntValue(10))
//                                                                                                        )
//                                                                                                ),
//                                                                                                new CompStmt(
//                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
//                                                                                                        new CountDownStmt("cnt")
//                                                                                                )
//                                                                                        )
//                                                                                ),
//                                                                                new CompStmt(
//                                                                                        new ForkStmt(
//                                                                                                new CompStmt(
//                                                                                                        new WriteHeapStmt(
//                                                                                                                new VarExp("v2"),
//                                                                                                                new ArithExp('*',
//                                                                                                                        new ReadHeapExp(new VarExp("v2")),
//                                                                                                                        new ValueExp(new IntValue(10))
//                                                                                                                )
//                                                                                                        ),
//                                                                                                        new CompStmt(
//                                                                                                                new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
//                                                                                                                new CountDownStmt("cnt")
//                                                                                                        )
//                                                                                                )
//                                                                                        ),
//                                                                                        new CompStmt(
//                                                                                                new ForkStmt(
//                                                                                                        new CompStmt(
//                                                                                                                new WriteHeapStmt(
//                                                                                                                        new VarExp("v3"),
//                                                                                                                        new ArithExp('*',
//                                                                                                                                new ReadHeapExp(new VarExp("v3")),
//                                                                                                                                new ValueExp(new IntValue(10))
//                                                                                                                        )
//                                                                                                                ),
//                                                                                                                new CompStmt(
//                                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v3"))),
//                                                                                                                        new CountDownStmt("cnt")
//                                                                                                                )
//                                                                                                        )
//                                                                                                ),
//                                                                                                new CompStmt(
//                                                                                                        new AwaitStmt("cnt"),
//                                                                                                        new CompStmt(
//                                                                                                                new PrintStmt(new ValueExp(new IntValue(100))),
//                                                                                                                new CompStmt(
//                                                                                                                        new CountDownStmt("cnt"),
//                                                                                                                        new PrintStmt(new ValueExp(new IntValue(100)))
//                                                                                                                )
//                                                                                                        )
//                                                                                                )
//                                                                                        )
//                                                                                )
//                                                                        )
//                                                                )
//                                                        )
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//        allPrgs.add(ex12);

        // Ref int v1; Ref int v2; Ref int v3; int cnt;
        // new(v1,2); new (v2,3); new(v3,4);
        // newLatch(cnt, rh(v2));
        // fork( wh(v1, rh(v1)*10; print(rh(v1)); countDown(cnt);
        //      fork( wh(v2,rh(v2)*10); print(rh(v2)); countDown(cnt);
        //          fork( wh(v3, rh(v3)*10); print(rh(V3)); countDown(cnt); );
        //      )
        // )
        IStmt ex10 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())),
                                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(2))),
                                        new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                new CompStmt(new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                        new CompStmt(new VarDeclStmt("cnt", new IntType()),
                                                        new CompStmt(new NewLatchStmt("cnt", new ReadHeapExp(new VarExp("v2"))),
                                                                new CompStmt(
                                                                new ForkStmt(
                                                                        new CompStmt(new WriteHeapStmt(new VarExp("v1"), new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                        new CompStmt(new CountDownStmt("cnt"),
                                                                                               new ForkStmt(
                                                                                                       new CompStmt(new WriteHeapStmt(new VarExp("v2"), new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                               new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                                                                                      new CompStmt(new CountDownStmt("cnt"),
                                                                                                                              new ForkStmt(
                                                                                                                                      new CompStmt(new WriteHeapStmt(new VarExp("v3"), new ArithExp('*', new ReadHeapExp(new VarExp("v3")), new ValueExp(new IntValue(10)))),
                                                                                                                                              new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v3"))),
                                                                                                                                                    new CountDownStmt("cnt")
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
                                                                ),
                                                                        new CompStmt(new AwaitStmt("cnt"),
                                                                                new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                        new CompStmt(new CountDownStmt("cnt"),
                                                                                                new PrintStmt(new ValueExp(new IntValue(100)))
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
        allPrgs.add(ex10);
        return FXCollections.observableArrayList(allPrgs);
    }

    @FXML
    private void initialize(){
        programListView.setItems(getAllPrograms());
        programListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void setProgramExecutor(ProgramExecutor programExecutor) {
        this.programExecutor=programExecutor;
    }

    @FXML
    public void displaySelectedProgram(javafx.event.ActionEvent actionEvent) {
        IStmt selectedProgram = programListView.getSelectionModel().getSelectedItem();
        if(selectedProgram==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No program selected");
            alert.showAndWait();
        }
        else{
            int id=programListView.getSelectionModel().getSelectedIndex();
            try{
                selectedProgram.typeCheck(new Utils.ADT.MyDictionary<>());
                PrgState prgState = new PrgState(new MyExeStack(), new MySymTbl(), new MyFileTbl(), new Heap(), new MyOut(),new LatchTable(),  selectedProgram);
                IRepository repo = new Repository(prgState, "log"+id+".txt");
                Controller controller = new Controller(repo);
                programExecutor.setController(controller);
            } catch (MyException exception){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }
}
