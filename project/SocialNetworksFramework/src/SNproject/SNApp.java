package SNproject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import SNproject.Controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Kuba
 */
public class SNApp extends Application{
    
    private static final String APP_TITLE = "Kuba Gasiorowski CSE392";
    private static final String EXIT_TEXT = "Exit";
    private static final String START_TEXT = "Start";
    private static final String CHOOSER_TEXT = "Choose graph data";
    private static final String PROGERSS_LABEL = "Default progress message";
    private static final String TEXT_AREA_DEFAULT_TEXT = "Text area text";
    
    private static final int APP_WIDTH = 500;
    private static final int APP_HEIGHT = 300;
    
    private Stage mainStage;
    
    private Scene mainScene;
    private Pane mainPane;
    
    private Button exitButton;
    private Button startButton;
    private Button chooseButton;
    
    private TextArea outputArea;
    
    private Label progressLabel;
    private ProgressBar progressBar;
    
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
        
        progressLabel = new Label("Progress: 0%");
        
        progressBar = new ProgressBar(0.0);
        progressBar.setPrefWidth(200);
        
        outputArea = new TextArea();
        
        mainPane = new HBox();
        
        VBox firstColumn = new VBox(5);
        VBox secondColumn = new VBox(5);
        VBox thirdColumn = new VBox();
        
        firstColumn.setPadding(new Insets(10));
        secondColumn.setPadding(new Insets(10));
        
        
        firstColumn.getChildren().addAll(exitButton, chooseButton, startButton);
        secondColumn.getChildren().addAll(progressLabel, progressBar);
        
        
        mainPane.getChildren().addAll(firstColumn, secondColumn);
        
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
    
    public void disableStart(boolean val){
        
        startButton.setDisable(val);
        
    }
    
    public void disableChoose(boolean val){
        
        chooseButton.setDisable(val);
        
    }
    
    public void disableButtons(){
        
        disableStart(true);
        disableChoose(true);
        
    }
    
    public void enableButtons(){
        
        disableStart(false);
        disableChoose(false);
        
    }
    
    public Stage getMainStage(){
        
        return mainStage;
        
    }
    
    public synchronized void setProgress(double d){
        
        setProgress(d, "Progress : " + (int)(d*100) + "%");
        
    }
    
    public synchronized void setProgress(double d, String s){
        
        progressLabel.setText(s);
        progressBar.setProgress(d);
        
    }
    
    public static void main(String args[]){
        
        launch(args);
        
    }
    
}
