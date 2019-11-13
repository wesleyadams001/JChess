/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Enums.PieceType;
import java.util.Vector;

/**
 * Class that holds the available moves for a Bishop.
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
    
     /**
     * Get special moves for piece.
     * @param board The board for the current game.
     * @return  An empty vector.
     */
    @Override
    public Vector<Pair> getSpecialMoves(Board board) {
        return new Vector<>();
    }

    /**
     * Get possible moves for piece.
     * @param board The board for the current game.
     * @return  Vector of possible moves for the bishop.
     */
    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        return getDiagonalMoves(board);
    }

}
