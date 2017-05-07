/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNproject.Controller;

import SNproject.AppComponent;
import SNproject.FileChooserSingleton.FileChooserSingleton;
import SNproject.Graph.Graph;
import SNproject.SNApp;
import java.io.File;
import static java.lang.System.exit;
import java.util.ArrayList;
import javafx.stage.FileChooser;

/**
 *
 * @author Kuba
 */
public class Controller extends AppComponent{
    
    /**
     * Initializes this controller
     * @param app 
     */
    public Controller(SNApp app){
        
        super(app);
        
    }
    
    /**
     * Handles when the use clicks the exit button
     */
    public void handleExitButton(){
        
        System.out.println("Handle event for exit button");
        
        app.getMainStage().close();
        exit(0);
        
    }
    
    /**
     * Handles when the user clicks the start button
     */
    public void handleStartButton(){
        
        app.disableButtons();
        
        app.appendTextAreanl("-----------");
        app.appendTextAreanl("Starting...");
        
        if(app.getFileManager().getGraphFile() == null){
            
            System.out.println("No input file initialized");
            app.appendTextAreanl("Error: no input file initialized.\nPlease pick a file with a valid format");
            
        }else{
            
            app.appendTextAreanl("Generating random graphs...");
            
            try{
            
                ArrayList<Graph> randomGraphs = app.getDataManager().generateRandomGraphData();
            
            }catch(NumberFormatException nfe){
                
                System.out.println("Error: invalid number of graphs entered");
                app.appendTextAreanl("Error: invalid number of graphs entered");
                
            }
        }
        
        app.enableButtons();
        
    }
    
    /**
     * Handles when the user clicks the choose button
     */
    public void handleFileChooseButton(){
        
        System.out.println("Handle event for file choose button");
        
        app.disableButtons();
        
        FileChooser fc = FileChooserSingleton.getSingleton();
        
        File chosenFile = fc.showOpenDialog(app.getMainStage());
        
        String filename;
        if(chosenFile == null) 
            filename = "No file chosen";
        else 
            filename = chosenFile.getName();
        
        System.out.printf("File choosen: %s\n", filename);
        app.appendTextArea("File chosen: " + filename + "\n");
        
        app.getFileManager().setGraphFile(chosenFile);
        app.getDataManager().initData();
        app.setChosenFile(app.getFileManager().getGraphFile());
        
        app.enableButtons();
        
    }
    
}
