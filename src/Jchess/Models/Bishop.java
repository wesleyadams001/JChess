/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Enums.PieceType;
import java.util.Vector;

/**
 * Class that holds the available moves for a bishop
 * @author jonathanjoiner
 */
public class Bishop extends Piece{
    
    /**
     * A Bishop Piece.
     * @param owner The Bishop's owner.
     */
    public Bishop(Player owner) {
        super(owner, PieceType.Bishop);
    }
    
    @Override
    public Vector<Pair> getSpecialMoves(Board board) {
        return new Vector();
    }

    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        return getDiagonalMoves(board);
    }

}
