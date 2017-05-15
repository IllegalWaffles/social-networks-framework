package SNproject.Controller;

import SNproject.AppComponent;
import SNproject.FileChooserSingleton.FileChooserSingleton;
import SNproject.Graph.Graph;
import SNproject.SNApp;
import static SNproject.SNApp.DIVIDER;
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
        
        app.appendTextAreanl(DIVIDER);
        app.appendTextAreanl("Starting...");
        
        
        
        if(app.getFileManager().getGraphFile() == null){
            
            System.out.println("No input file initialized");
            app.appendTextAreanl("Error: no input file initialized.\nPlease pick a file with a valid format");
            
            app.enableButtons();
            
        }else{
            
            app.appendTextAreanl("Generating random graphs...");
            
            
            Thread thread = new Thread(() -> {
            
                try{

                    ArrayList<Graph> randomGraphs = app.getDataManager().generateRandomGraphData();
                    app.appendTextAreanl("Finished initializing random graphs");
                    
                    ArrayList<Double> CCRandValues = new ArrayList();
                    
                    for(int i = 0; i < randomGraphs.size(); i++){
                        
                        app.appendTextAreanl("Calculating clustering coefficient for random graph " + (i+1));
                        Graph g = randomGraphs.get(i);
                        CCRandValues.add(g.getClusteringCoefficient());
                        //app.appendTextAreanl(String.format("Calculated cluster coefficient: %.9f", CCRandValues.get(i)));
                        
                    }
                    
                    app.appendTextAreanl(DIVIDER);
                    double temp = 0;
                    for(double cc : CCRandValues)
                        temp += cc;
                    
                    double averageCC = temp/CCRandValues.size();
                    
                    //app.appendTextAreanl(String.format("Average cluster coefficient: %.9f", averageCC));
                    
                    app.appendTextAreanl("Calculating test cluster coefficient...");
                    double testCC = app.getDataManager().getTestGraph().getClusteringCoefficient();
                    //app.appendTextAreanl(String.format("Test cluster coefficient: %.9f", testCC));
                    
                }catch(NumberFormatException nfe){
                    
                    System.out.println("Error: invalid number of graphs entered");
                    app.appendTextAreanl("Error: invalid number of graphs entered");

                }finally{
                
                    app.enableButtons();
                
                }
                
            });
            
            thread.start();
            
        }
        
    }
    
    /**
     * Handles when the user clicks the choose button
     */
    public void handleFileChooseButton(){
        
        System.out.println("Handle event for file choose button");

        app.disableButtons();

        FileChooser fc = FileChooserSingleton.getSingleton();

        final File chosenFile = fc.showOpenDialog(app.getMainStage());

        String filename;
        if(chosenFile == null) 
            filename = "No file chosen";
        else 
            filename = chosenFile.getName();

        System.out.printf("File choosen: %s\n", filename);
        app.appendTextArea("File chosen: " + filename + "\n");

        app.getFileManager().setGraphFile(chosenFile);
            
        Thread thread = new Thread(() -> {
            
            app.getDataManager().initData();
        
        });
        thread.start();
        
        try{
            thread.join();
        }catch(InterruptedException ie){
            System.err.println("Error: something was interrupted");
        }
        
        app.setChosenFile(app.getFileManager().getGraphFile());
        app.enableButtons();
        
    }
    
}
