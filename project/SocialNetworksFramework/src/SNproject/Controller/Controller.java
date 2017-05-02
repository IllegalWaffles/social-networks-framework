/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNproject.Controller;

import SNproject.FileChooserSingleton.FileChooserSingleton;
import SNproject.SNApp;
import java.io.File;
import static java.lang.System.exit;
import javafx.stage.FileChooser;

/**
 *
 * @author Kuba
 */
public class Controller {
    
    SNApp app;
    
    public Controller(SNApp app){
        
        this.app = app;
        
    }
    
    public void handleExitButton(){
        
        app.getMainStage().close();
        exit(0);
        
    }
    
    public void handleStartButton(){
        
        
        
    }
    
    public void handleFileChooseButton(){
        
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
