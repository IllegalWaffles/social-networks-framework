package SNproject.FileChooserSingleton;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Kuba
 */
public class FileChooserSingleton {
    
    private static final String DEFAULT_CHOOSER_TITLE = "Choose A File";
    private static FileChooser chooser;
    
    /**
     * Returns the singleton with a specific title set
     * @param title
     * @return 
     */
    public static FileChooser getSingleton(String title){
        
        if(chooser == null)
            initSingleton();
        
        chooser.setTitle(title);
        
        return chooser;
        
    }
    
    /**
     * Returns the singleton with the default title
     * @return 
     */
    public static FileChooser getSingleton(){
        
        return getSingleton(DEFAULT_CHOOSER_TITLE);
        
    }
    
    /**
     * Private default constructor, so that it may not be ordinarily used.
     */
    private FileChooserSingleton(){}
    
    /**
     * Initializes the singleton. Only called once
     */
    private static void initSingleton(){
        
        chooser = new FileChooser();
        chooser.setTitle(DEFAULT_CHOOSER_TITLE);
        chooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("Graph Data Files", "*.grph")
        );
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        
    }
    
}
