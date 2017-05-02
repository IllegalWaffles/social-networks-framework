package SNproject;

/**
 *
 * @author Kuba
 */
public abstract class AppComponent {
    
    protected SNApp app;
    
    /**
     * 
     * @param app 
     */
    public AppComponent(SNApp app){
        
        this.app = app;
        
    }
    
    private AppComponent(){}
    
}
