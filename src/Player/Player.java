/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;


import Enums.ThemeColor;
import Board.Pair;

/**
 *
 * @author nehalpatel
 */
public class Player {
    private String name;
    private boolean isChecked;
    private final ThemeColor color;
    private Pair kingPair;
    
    public Player(String name, ThemeColor color) {
        this.name = name;
        this.color = color;
    }
    
    /**
     * Mark Player as "in check."
     * @param check 
     */
    public void setIsChecked(boolean check){
        this.isChecked = check;
    }
    
    /**
     * Determine whether Player is "in check."
     * @return 
     */
    public boolean isChecked() {
        return this.isChecked;
    }
    
    /**
     * Returns the Player's name.
     * @return 
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the Player's color.
     * @return 
     */
    public ThemeColor getColor() {
        return this.color;
    }
    
    /**
     * Returns the location of Player's King.
     * @return 
     */
    public Pair getLocationOfKing(){
        return this.kingPair;
    }
    
    /**
     * Updates the location of Player's King.
     * @param king 
     */
    public void setLocationOfKing(Pair king){
        this.kingPair = king;
    }
}
