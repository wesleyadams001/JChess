/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Core;

/**
 * Subject interface for observer pattern
 * Implemented by the subject to communicate 
 * with any registered observers
 * @author Wesley
 */
public interface Subject {
    
    public void registerObserver(Observer o); 
    
    public void unregisterObserver(Observer o);
    
    public void notifyObservers(); 
}
