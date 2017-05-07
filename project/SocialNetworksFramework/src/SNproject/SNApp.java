package SNproject;

import SNproject.Controller.Controller;
import SNproject.Data.DataManager;
import SNproject.File.FileManager;
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Kuba
 */
public class SNApp extends Application{
    
    private static final String APP_TITLE = "Kuba Gasiorowski CSE392";
    private static final String EXIT_TEXT = "Exit";
    private static final String START_TEXT = "Start Calculations";
    private static final String CHOOSER_TEXT = "Choose graph data";
    private static final String PROGRESS_LABEL = "Default progress message";
    private static final String TEXT_AREA_DEFAULT = "Welcome\n";
    private static final String DEFAULT_CHOSEN_FILE_TEXT = "No file chosen";
    private static final String RANDOM_LABEL_TEXT = "Num random graphs:";
    
    private static final int APP_WIDTH = 700;
    private static final int APP_HEIGHT = 500;
    
    private static final int PROGRESS_BAR_WIDTH = 300;
    
    private static final int TEXT_AREA_WIDTH = PROGRESS_BAR_WIDTH;
    private static final int TEXT_AREA_HEIGHT = 375;
    
    private static final int FIRST_COLUMN_WIDTH = 300;
    private static final int SECOND_COLUMN_WIDTH = 0;
    
    private static final double LINK_PROB = .5;
    
    private Stage mainStage;
    
    private Scene mainScene;
    private Pane mainPane;
    
    private Button exitButton;
    private Button startButton;
    
    private Button chooseButton;
    private TextField fileChosenField;
    
    private Label labelNumGraphs;
    private TextField numGraphsField;
            
    private TextArea outputArea;
    
    private Label progressLabel;
    private ProgressBar progressBar;
    
    private DataManager dataManager;
    private FileManager fileManager;
    
    @Override
    public void start(Stage mainStage){
        
        this.mainStage = mainStage;
        
        initGUI();
        initHandlers();
        initComponents();
        
        mainStage.show();
        
    }
    
    /**
     * Initializes the GUI
     */
    private void initGUI(){
        
        exitButton = new Button(EXIT_TEXT);
        startButton = new Button(START_TEXT);
        
        HBox chooseBox = new HBox(10);
        
        chooseButton = new Button(CHOOSER_TEXT);
        fileChosenField = new TextField(DEFAULT_CHOSEN_FILE_TEXT);
        fileChosenField.setEditable(false);
        
        chooseBox.getChildren().addAll(chooseButton, fileChosenField);
        
        HBox numGraphsBox = new HBox(10);
        
        labelNumGraphs = new Label(RANDOM_LABEL_TEXT);
        numGraphsField = new TextField("0");
        numGraphsField.setPrefWidth(30);
        
        numGraphsBox.getChildren().addAll(labelNumGraphs, numGraphsField);
        
        progressLabel = new Label(PROGRESS_LABEL);
        
        progressBar = new ProgressBar(0.0);
        progressBar.setPrefWidth(PROGRESS_BAR_WIDTH);
        
        outputArea = new TextArea(TEXT_AREA_DEFAULT);
        outputArea.setEditable(false);
        outputArea.setPrefWidth(TEXT_AREA_WIDTH);
        outputArea.setPrefHeight(TEXT_AREA_HEIGHT);
        
        mainPane = new HBox();
        
        VBox firstColumn = new VBox(5);
        VBox secondColumn = new VBox(5);
        
        firstColumn.setPadding(new Insets(10));
        firstColumn.setPrefWidth(FIRST_COLUMN_WIDTH);
        
        secondColumn.setPadding(new Insets(10));
        
        firstColumn.getChildren().addAll(exitButton, chooseBox, numGraphsBox, startButton);
        secondColumn.getChildren().addAll(progressLabel, progressBar, outputArea);
        
        mainPane.getChildren().addAll(firstColumn, secondColumn);
        
        mainScene = new Scene(mainPane);
        mainStage.setScene(mainScene);
        mainStage.setTitle(APP_TITLE);
        mainStage.setWidth(APP_WIDTH);
        mainStage.setHeight(APP_HEIGHT);
        mainStage.setResizable(false);
        
    }
    
    /**
     * Initializes handlers for all our buttons
     */
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
    
    /**
     * Initializes the application's components
     */
    private void initComponents(){
    
        dataManager = new DataManager(this);
        fileManager = new FileManager(this);
    
        //Initialize random graphs here
        
        
    }
    
    /**
     * 
     * @param s 
     */
    public void appendTextArea(String s){
        
        if(Platform.isFxApplicationThread())
            outputArea.appendText(s);
        else
            Platform.runLater(() -> outputArea.appendText(s));
        
    }
    
    /**
     * 
     * @param s 
     */
    public void appendTextAreanl(String s){
        
        appendTextArea(s + "\n");
        
    }
    
    public void appendTextAreanl(){
        
        appendTextAreanl("");
        
    }
    
    /**
     * 
     * @param f 
     */
    public void setChosenFile(File f){
        
        if(f == null)
            fileChosenField.setText(DEFAULT_CHOSEN_FILE_TEXT);
        else
            fileChosenField.setText(f.getName());
        
    }
    
    /**
     * Sets the application's progress level
     * @param d 
     */
    public void setProgress(double d){
        
        setProgress(d, "Progress : " + (int)(d*100) + "%");
        
    }
    
    /**
     * Sets the application's progress level with a specific message
     * @param d
     * @param s 
     */
    public void setProgress(double d, String s){
        
        if(Platform.isFxApplicationThread()){
            progressLabel.setText(s);
            progressBar.setProgress(d);
        }else{
            
            Platform.runLater(() -> {
            
                progressLabel.setText(s);
                progressBar.setProgress(d);
            
            });
            
        }
        
    }
    
    /**
     * Disables the start button
     * @param val 
     */
    public void disableStart(boolean val){
        
        startButton.setDisable(val);
        
    }
    
    public String getNumGraphsData(){
        
        return numGraphsField.getText();
        
    }
    
    /**
     * Disables the choose file button
     * @param val 
     */
    public void disableChoose(boolean val){
        
        chooseButton.setDisable(val);
        
    }
    
    /**
     * Disables all relevant buttons
     */
    public void disableButtons(){
        
        disableStart(true);
        disableChoose(true);
        
    }
    
    /**
     * Enables all relevant buttons
     */
    public void enableButtons(){
        
        disableStart(false);
        disableChoose(false);
        
    }
    
    /**
     * Returns the main stage (gui)
     * @return 
     */
    public Stage getMainStage(){return mainStage;}
    
    /**
     * Returns the file component of this application
     * @return 
     */
    public FileManager getFileManager(){return fileManager;}
    
    /**
     * Returns the data component of this application
     * @return 
     */
    public DataManager getDataManager(){return dataManager;}
    
    /**
     * Launches the gui
     * @param args 
     */
    public static void main(String args[]){
        
        launch(args);
        
    }
    
}
