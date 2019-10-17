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
    king('K','k'),
    queen('Q','q'),
    bishop('B','b'),
    knight('N','n'),
    rook('R','r'),
    pawn('P','p');

    private final char abr1;
    private final char abr2;
    
    private PieceType(char abr1, char abr2){
        this.abr1 = abr1;
        this.abr2 = abr2;
    }
    public char getAbr1(){
        return this.abr1;
    }
    public char getAbr2(){
        return this.abr2;
    }
}
