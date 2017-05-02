/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNproject;

/**
 *
 * @author Kuba
 */
public abstract class AppComponent {
    
    protected SNApp app;
    
    public AppComponent(SNApp app){
        
        this.app = app;
        
    }
    
    private AppComponent(){}
    
}
