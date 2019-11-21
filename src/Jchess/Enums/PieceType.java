/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Enums;

import Jchess.Models.Player;

/**
 *
 * @author chris odom
 *
 *         this enum is used for returning char values when handling conversion
 *         from FEN format to board states.
 *
 */
public enum PieceType {

    /**
     * Piece is a King.
     */
    King('K','k'),

    /**
     * Piece is a Queen.
     */
    Queen('Q','q'),

    /**
     * Piece is a Bishop.
     */
    Bishop('B','b'),

    /**
     * Piece is a Knight.
     */
    Knight('N','n'),

    /**
     * Piece is a Rook.
     */
    Rook('R','r'),

    /**
     * Piece is a Pawn.
     */
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
     * Gets the correct letter representation based on an owner Player.
     * @param owner The Piece's owner.
     * @return Either the light or the dark letter.
     */
    public char determineLetter(Player owner) {
        return owner.getColor() == ThemeColor.LightPiece ? getLightLetter() : getDarkLetter();
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
