/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNproject.Controller;

import SNproject.AppComponent;
import SNproject.FileChooserSingleton.FileChooserSingleton;
import SNproject.SNApp;
import java.io.File;
import static java.lang.System.exit;
import javafx.stage.FileChooser;

/**
 *
 * @author Kuba
 */
public class Controller extends AppComponent{
    
    public Controller(SNApp app){
        
        super(app);
        
    }
    
    public void handleExitButton(){
        
        System.out.println("Handle event for exit button");
        
        app.getMainStage().close();
        exit(0);
        
    }
    
    public void handleStartButton(){
        
        System.out.println("Handle event for start button event");
        
    }
    
    public void handleFileChooseButton(){
        
        System.out.println("Handle event for file choose button");
        
        FileChooser fc = FileChooserSingleton.getSingleton();
        
        File chosenFile = fc.showOpenDialog(app.getMainStage());
        
        String filename;
        if(chosenFile == null) 
            filename = "No file chosen";
        else 
            filename = chosenFile.getName();
        
        System.out.printf("File choosen: %s\n", filename);
        
    }
    
}
