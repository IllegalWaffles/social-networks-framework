/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNproject.FileChooserSingleton;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Kuba
 */
public class FileChooserSingleton {
    
    private static final String DEFAULT_CHOOSER_TITLE = "";
    private static FileChooser chooser;
    
    public static FileChooser getSingleton(String title){
        
        if(chooser == null)
            initSingleton();
        
        chooser.setTitle(title);
        
        return chooser;
        
    }
    
    public static FileChooser getSingleton(){
        
        if(chooser == null)
            initSingleton();
        
        return chooser;
        
    }
    
    private FileChooserSingleton(){}
    
    private static void initSingleton(){
        
        chooser = new FileChooser();
        chooser.setTitle(DEFAULT_CHOOSER_TITLE);
        chooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("Graph Data Files", "*.grph")
        );
        
    }
    
}
