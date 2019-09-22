/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
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
    
    public String getAbbr(){
        return this.abbr;
    }
    
    public int getNum(){
        return this.num;
    }
}
