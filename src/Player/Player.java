/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;


import Enums.Color;

/**
 *
 * @author nehalpatel
 */
public class Player {
    private String name;
    private boolean isChecked;
    private Color color;
    
    public Player(String name, Color color){
        this.name = name;
        this.color = color;
    }
    
    public boolean isChecked(){
        return this.isChecked;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String n){
        this.name = n;
    }
    
    public Color getColor(){
        return this.color;
    }
}
