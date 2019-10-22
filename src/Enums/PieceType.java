/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author chris odom 
 * 
 * this enum is used for returning char values when handling conversion from FEN format to board states.
 * 
 */
public enum PieceType {
    King('K','k'),
    Queen('Q','q'),
    Bishop('B','b'),
    Knight('N','n'),
    Rook('R','r'),
    Pawn('P','p');

    private final char light;
    private final char dark;
    
    /**
     * The default constructor for Piece type enum
     * @param light
     * @param dark 
     */
    private PieceType(char light, char dark){
        this.light = light;
        this.dark = dark;
    }
    
    /**
     * Gets the Light Letter
     * @return char
     */
    public char getLightLetter() {
        return this.light;
    }
    
    /**
     * Gets the Dark letter
     * @return char
     */
    public char getDarkLetter() {
        return this.dark;
    }
}
