/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 * A Color enum that represents a players side ie "White" or "Black" 
 * that is also extended with a string designation "w" or "b"
 * as well as an associated number such as 1 or 2
 * @author Wesley
 */
public enum Color {
    white("w",1),
    black("b",2);
    
    private final int num;
    private final String abbr;
    
    private Color(String abbr, int number) 
    {
    this.abbr = abbr;
    this.num = number;      
    }
    
    /**
     * Gets the abbreviation associated with the color enum
     * @return String value such as "w" or "b"
     */
    public String getAbbr(){
        return this.abbr;
    }
    
    /**
     * Gets the number associated with the color
     * @return int associated with color
     */
    public int getNum(){
        return this.num;
    }
}
