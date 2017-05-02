package SNproject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import SNproject.Controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Kuba
 */
public class SNApp extends Application{
    
    private static final String APP_TITLE = "Kuba Gasiorowski CSE392";
    private static final String EXIT_TEXT = "Exit";
    private static final String START_TEXT = "Start";
    private static final String CHOOSER_TEXT = "Choose file";
    
    private static final int APP_WIDTH = 500;
    private static final int APP_HEIGHT = 300;
    
    private Stage mainStage;
    
    private Scene mainScene;
    private Pane mainPane;
    
    private Button exitButton;
    private Button startButton;
    private Button chooseButton;
    
    @Override
    public void start(Stage mainStage){
        
        this.mainStage = mainStage;
        
        initComponents();
        initHandlers();
        
        mainStage.show();
        
    }
    
    private void initComponents(){
        
        exitButton = new Button(EXIT_TEXT);
        startButton = new Button(START_TEXT);
        chooseButton = new Button(CHOOSER_TEXT);
        
        mainPane = new VBox();
        
        mainPane.getChildren().addAll(exitButton, startButton, chooseButton);
        
        mainScene = new Scene(mainPane);
        mainStage.setScene(mainScene);
        mainStage.setTitle(APP_TITLE);
        mainStage.setWidth(APP_WIDTH);
        mainStage.setHeight(APP_HEIGHT);
        mainStage.setResizable(false);
        
    }
    
    private void initHandlers(){
        
        Controller controller = new Controller(this);
        
        exitButton.setOnAction(e -> {
        
            controller.handleExitButton();
        
        });
        
        startButton.setOnAction(e -> {
            
            controller.handleStartButton();
            
        });
        
        chooseButton.setOnAction(e -> {
        
            controller.handleFileChooseButton();
        
        });
        
    }
    
    public Stage getMainStage(){
        
        return mainStage;
        
    }
    
    public static void main(String args[]){
        
        launch(args);
        
    }
    
}
