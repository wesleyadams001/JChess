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

    /**
     * Adds observer to "to-notify" list.
     * @param o Observer object to be added to the list.
     */
    public void registerObserver(Observer o); 

    /**
     * Removes observer from "to-notify" list.
     * @param o Observer object to be removed from the list.
     */
    public void unregisterObserver(Observer o);

    /**
     * Notifies observers in "to-notify" list.
     */
    public void notifyObservers(); 

}
