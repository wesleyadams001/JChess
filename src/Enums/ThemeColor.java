/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

import java.awt.*;

/**
 * A Color enum that represents a players side ie "White" or "Black" 
 * that is also extended with a string designation "w" or "b"
 * as well as an associated number such as 1 or 2
 * @author Wesley
 */
public enum ThemeColor {
    LightPiece("w", 1, Color.WHITE),
    DarkPiece("b", 2, Color.BLACK),
    LightTile("lb", 3, new Color(222, 184, 135)),
    DarkTile("db", 4, new Color(139, 69, 19)),
    Friendly("bl", 5, new Color(52, 152, 219)),
    Enemy("r", 6, new Color(231, 76, 60));

    private final int num;
    private final String abbr;
    private final Color col;

    private ThemeColor(String abbr, int number, Color col) 
    {
        this.abbr = abbr;
        this.num = number;
        this.col = col;
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

    public Color getColor() {
        return this.col;
    }

}
