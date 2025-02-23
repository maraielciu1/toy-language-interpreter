package Gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader programLoader = new FXMLLoader();
        programLoader.setLocation(Main.class.getResource("ProgramChooser.fxml"));
        Parent root = programLoader.load();
        Scene scene = new Scene(root, 800, 500);
        ProgramChooser programChooser = programLoader.getController();
        primaryStage.setTitle("Select a program to run");
        primaryStage.setScene(scene);
        primaryStage.show();

        FXMLLoader programExecutorLoader = new FXMLLoader();
        programExecutorLoader.setLocation(Main.class.getResource("ProgramExecutor.fxml"));
        Parent root2 = programExecutorLoader.load();
        Scene scene2 = new Scene(root2, 800, 500);
        ProgramExecutor programExecutor = programExecutorLoader.getController();
        programChooser.setProgramExecutor(programExecutor);
        Stage stage2 = new Stage();
        stage2.setTitle("Program Executor");
        stage2.setScene(scene2);
        stage2.show();
    }
}
