package Gui;

import Controller.Controller;
import Model.PrgState;
import Model.Stmt.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;
import Utils.State.IBarrierTable;
import Utils.State.IHeap;
import Utils.State.MySymTbl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

class Pair<T1, T2> {
    T1 first;
    T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
}

public class ProgramExecutor {
    private Controller controller;
    @FXML
    private TextField noOfPrgStates;

    @FXML
    private TableView<Pair<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> valueColumn;

    @FXML
    private ListView<String> outListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<Integer> programStateIdentifiersListView;

    @FXML
    private TableView<Pair<String, Value>> symbolTableView;

    @FXML
    private TableColumn<Pair<String, Value>, String> variableNameColumn;

    @FXML
    private TableColumn<Pair<String, Value>, String> variableValueColumn;

    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private Button runOneStepButton;

    @FXML
    private TableView<Pair<Integer, Pair<Integer, List<Integer>>>> barrierTableView;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, Integer> barrierLocationColumn;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, Integer> barrierValueColumn;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, String> barrierListColumn;



    private PrgState getCurrentProgramState(){
        if(controller.getProgramStates().isEmpty())
            return null;
        else{
            int currentProgramStateId = programStateIdentifiersListView.getSelectionModel().getSelectedIndex();
            if(currentProgramStateId == -1)
                return controller.getProgramStates().get(0);
            else
                return controller.getProgramStates().get(currentProgramStateId);
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    @FXML
    public void initialize(){
        programStateIdentifiersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().first).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        variableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first));
        variableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        barrierLocationColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().first).asObject());
        barrierValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().second.first).asObject());
        barrierListColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.second.toString()));
    }

    public void populate(){
        populateNumberProgramStates();
        populateHeapTableView();
        populateOutListView();
        populateFileTableListView();
        populateProgramStateIdentifiersListView();
        populateSymbolTableView();
        populateExecutionStackListView();
        populateBarrierTableView();
    }

    public void populateBarrierTableView(){
        PrgState programState = getCurrentProgramState();
        IBarrierTable barrierTable = Objects.requireNonNull(programState).getBarrierTbl();
        ArrayList<Pair<Integer, Pair<Integer, List<Integer>>>> barrierTableEntries = new ArrayList<>();
            for(Map.Entry<Integer, javafx.util.Pair<Integer, List<Integer>>> entry: barrierTable.getContent().entrySet()) {
            barrierTableEntries.add(new Pair<>(entry.getKey(), new Pair<>(entry.getValue().getKey(), entry.getValue().getValue())));
        }
            barrierTableView.setItems(FXCollections.observableArrayList(barrierTableEntries));
    }
    public void populateNumberProgramStates() {
        noOfPrgStates.setText(String.valueOf(controller.getProgramStates().size()));
    }

    public void populateHeapTableView() {
        PrgState programState = getCurrentProgramState();
        IHeap heap= Objects.requireNonNull(programState).getHeap();
        ArrayList<Pair<Integer, Value>> heapTable = new ArrayList<>();
        for(Map.Entry<Integer, Value> entry: heap.getContent().entrySet())
            heapTable.add(new Pair<>(entry.getKey(), entry.getValue()));
        heapTableView.setItems(FXCollections.observableArrayList(heapTable));
    }

    public void populateOutListView(){
        PrgState programState = getCurrentProgramState();
        List<String> output=new ArrayList<>();
        List<String> outputList=Objects.requireNonNull(programState).getOut().getList();
        for(String value: outputList)
            output.add(value.toString());
        outListView.setItems(FXCollections.observableArrayList(output));
    }

    public void populateFileTableListView(){
        PrgState programState = getCurrentProgramState();
        ArrayList<String> fileTable=new ArrayList<>(Objects.requireNonNull(programState).getFileTable().keySet());
        fileTableListView.setItems(FXCollections.observableArrayList(fileTable));
    }

    public void populateProgramStateIdentifiersListView() {
        List<PrgState> programStates = controller.getProgramStates();
        List<Integer> programStateIdentifiers = programStates.stream().map(PrgState::getId).toList();
        programStateIdentifiersListView.setItems(FXCollections.observableArrayList(programStateIdentifiers));
        populateNumberProgramStates();
    }

    public void populateSymbolTableView(){
        PrgState programState = getCurrentProgramState();
        MySymTbl symbolTable= (MySymTbl) Objects.requireNonNull(programState).getSymTable();
        ArrayList<Pair<String, Value>> symbolTableEntries=new ArrayList<>();
        for(Map.Entry<String,Value> entry: symbolTable.getContent().entrySet())
            symbolTableEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        symbolTableView.setItems(FXCollections.observableArrayList(symbolTableEntries));
    }

    public void populateExecutionStackListView() {
        PrgState programState = getCurrentProgramState();
        List<String> executionStackToString = new ArrayList<>();

        if (programState != null) {
            // Assuming your stack has a method to retrieve items in reverse order
            for (IStmt statement : programState.getExeStack().getReversed()) {
                executionStackToString.add(statement.toString());
            }
        }

        // Update the ListView with the new stack representation
        exeStackListView.setItems(FXCollections.observableArrayList(executionStackToString));
    }



    @FXML
    public void runOneStep(javafx.scene.input.MouseEvent mouseEvent) {
        if(controller != null){
            try{
                List<PrgState> programStates = Objects.requireNonNull(controller.getProgramStates());
                if(!programStates.isEmpty()){
                    controller.oneStep();
                    populate();
                    programStates = controller.removeCompletedPrg(controller.getProgramStates());
                    controller.setProgramStates(programStates);
                    populateProgramStateIdentifiersListView();

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("There is nothing left to execute");
                    alert.showAndWait();
                }
            } catch (MyException | InterruptedException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Execution Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No program selected");
            alert.showAndWait();
        }
    }

    @FXML
    public void changePrgState(javafx.scene.input.MouseEvent mouseEvent) {
        populateExecutionStackListView();
        populateSymbolTableView();
    }
}
