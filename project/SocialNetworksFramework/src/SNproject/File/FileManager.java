package SNproject.File;

import SNproject.AppComponent;
import SNproject.SNApp;
import java.io.File;

/**
 *
 * @author Kuba
 */
public class FileManager extends AppComponent {
    
    private File graphData;
    
    /**
     * Sets the source graph file
     * @param graphData 
     */
    public void setGraphFile(File graphData){this.graphData = graphData;}
    
    /**
     * Returns the source graph file
     * @return 
     */
    public File getGraphFile(){return graphData;}
    
    /**
     * Initializes the file component of this application
     * @param init 
     */
    public FileManager(SNApp init){super(init);}
    
}
