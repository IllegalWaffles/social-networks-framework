package SNproject;

import SNproject.Controller.Controller;
import SNproject.Data.DataManager;
import SNproject.File.FileManager;
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
        chooseButton = new Button(CHOOSER_TEXT);
        
        progressLabel = new Label("Progress: 0%");
        
        progressBar = new ProgressBar(0.0);
        progressBar.setPrefWidth(200);
        
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefWidth(100);
        
        mainPane = new HBox();
        
        VBox firstColumn = new VBox(5);
        VBox secondColumn = new VBox(5);
        
        firstColumn.setPadding(new Insets(10));
        secondColumn.setPadding(new Insets(10));
        
        firstColumn.getChildren().addAll(exitButton, chooseButton, startButton);
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
    
    }
    
    /**
     * Sets the application's progress level
     * @param d 
     */
    public synchronized void setProgress(double d){
        
        setProgress(d, "Progress : " + (int)(d*100) + "%");
        
    }
    
    /**
     * Sets the application's progress level with a specific message
     * @param d
     * @param s 
     */
    public synchronized void setProgress(double d, String s){
        
        progressLabel.setText(s);
        progressBar.setProgress(d);
        
    }
    
    /**
     * Disables the start button
     * @param val 
     */
    public void disableStart(boolean val){
        
        startButton.setDisable(val);
        
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
