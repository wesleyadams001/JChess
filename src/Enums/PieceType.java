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
    
    private PieceType(char light, char dark){
        this.light = light;
        this.dark = dark;
    }
    
    public char getLightLetter() {
        return this.light;
    }
    
    public char getDarkLetter() {
        return this.dark;
    }
}
