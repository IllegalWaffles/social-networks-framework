package SNproject.Controller;

import SNproject.AppComponent;
import SNproject.FileChooserSingleton.FileChooserSingleton;
import SNproject.Graph.Graph;
import SNproject.Graph.PathLengthReturn;
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
     * Handles when the user clicks the clear button
     */
    public void handleClearButton(){
    
        app.clearTextArea();
        app.clearProgress();
    
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
                    
                    ArrayList<Double> clusteringCoefficientValuesRandom = new ArrayList();
                    ArrayList<PathLengthReturn> averagePathLengthsRandom = new ArrayList();
                    
                    for(int i = 0; i < randomGraphs.size(); i++){
                        
                        app.appendTextAreanl("Calculating clustering coefficient for random graph " + (i+1));
                        Graph g = randomGraphs.get(i);
                        clusteringCoefficientValuesRandom.add(g.getClusteringCoefficient());
                        
                    }
                    
                    app.appendTextAreanl(DIVIDER);
                    double temp = 0;
                    for(double cc : clusteringCoefficientValuesRandom)
                        temp += cc;
                    
                    //This hold the average clustering coefficients of the randomly generated graphs
                    double averageRandomCC = temp/clusteringCoefficientValuesRandom.size();
                    
                    for(int i = 0; i < randomGraphs.size(); i++){
                    
                        app.appendTextAreanl("Calculating average path length for random graph " + (i+1));
                        Graph g = randomGraphs.get(i);
                        averagePathLengthsRandom.add(g.getAveragePathLength());
                    
                    }
                    
                    app.appendTextAreanl(DIVIDER);
                    temp = 0;
                    for(PathLengthReturn apl : averagePathLengthsRandom)
                        temp += apl.averagePathLength;
                    
                    //This holds the average average path length of the randomly generated graphs
                    double averagePathLength = temp/averagePathLengthsRandom.size();
                    
                    temp = 0;
                    for(PathLengthReturn apl : averagePathLengthsRandom)
                        temp += apl.numMisses;
                    
                    double averageMissCount = (double)temp/averagePathLengthsRandom.size();
                    
                    app.appendTextAreanl("Calculating test cluster coefficient...");
                    //Holds the clustering coefficient of the test graph
                    double testCC = app.getDataManager().getTestGraph().getClusteringCoefficient();
                    
                    app.appendTextAreanl("Calculating test average path length...");
                    //Holds the average path length of the test graph
                    PathLengthReturn testAPL = app.getDataManager().getTestGraph().getAveragePathLength();
                    
                    app.appendTextAreanl(DIVIDER);
                    
                    //Final values get printed here
                    app.appendTextAreanl(String.format("TEST CLUSTER COEFFICIENT: %f", testCC));
                    app.appendTextAreanl(String.format("RANDOM GRAPH CLUSTER COEFFICIENT: %f", averageRandomCC));
                    app.appendTextAreanl("TEST AVERAGE PATH LENGTH: " + testAPL.averagePathLength);
                    app.appendTextAreanl("RANDOM AVERAGE PATH LENGTH: " + averagePathLength);
                    app.appendTextAreanl("NUMBER OF MISSES IN TEST GRAPH: " + testAPL.numMisses);
                    app.appendTextAreanl("NUMBER OF MISSES IN RANDOM GRAPHS: " + averageMissCount);
                    
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
            System.out.println("Something was interrupted: " + ie.getMessage());
        }
        
        app.setChosenFile(app.getFileManager().getGraphFile());
        app.enableButtons();
        
    }
    
}
