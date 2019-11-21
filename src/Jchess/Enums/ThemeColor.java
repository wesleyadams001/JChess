/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Enums;

import java.awt.*;

/**
 * A Color enum that represents a players side ie "White" or "Black"
 * that is also extended with a string designation "w" or "b"
 * as well as an associated number such as 1 or 2
 * @author Wesley
 */
public enum ThemeColor {

    /**
     * Used to represent a Piece owned by the light Player.
     */
    LightPiece("w", 1, Color.WHITE),

    /**
     * Used to represent a Piece owned by the dark Player.
     */
    DarkPiece("b", 2, Color.BLACK),

    /**
     * Used to paint a light Tile.
     */
    LightTile("lb", 3, new Color(222, 184, 135)),

    /**
     * Used to paint a dark Tile.
     */
    DarkTile("db", 4, new Color(139, 69, 19)),

    /**
     * Used to represent the selected Piece or a possible move to an empty Tile.
     */
    Friendly("bl", 5, new Color(52, 152, 219)),

    /**
     * Used to represent a possible move to a Tile occupied by enemy.
     */
    Enemy("r", 6, new Color(231, 76, 60)),

    /**
     * Used to represent a special move.
     */
    Special("g", 7, new Color(5, 255, 30));

    private final int num;
    private final String abbr;
    private final Color col;

     /**
     * Returns a String representation of this theme.
     * @param abbr Represents the player side.
     * @param number Associated number for the enum.
     * @param col Color value.
     * @return Name of theme.
     */
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
     * @return Integer associated with color
     */
    public int getNum(){
        return this.num;
    }

    /**
     * gets the color
     * @return The color value.
     */
    public Color getColor() {
        return this.col;
    }

}
