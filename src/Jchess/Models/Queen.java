/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Enums.PieceType;
import java.util.Vector;

/**
 * The class that contains the logic for the queen piece
 * @author jonathanjoiner
 */
public class Queen extends Piece {
    
    /**
     * A Queen Piece.
     * @param owner The Queen's owner.
     */
    public Queen(Player owner) {
        super(owner, PieceType.Queen);
    }

    /**
     * Gets special moves for queen.
     * @param board Current board state.
     * @return  An empty vector.
     */
    @Override
    public Vector<Pair> getSpecialMoves(Board board) {
        return new Vector<>();
    }

    /**
     * Gets possible moves for queen.
     * @param board Current board state.
     * @return  Vector of possible moves for the queen.
     */
    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        Vector<Pair> moves = new Vector<>();

        // Add horizontal and vertical moves.
        moves.addAll(this.getLaneMoves(board));

        // Add diagonal moves.
        moves.addAll(this.getDiagonalMoves(board));
        
        //Return move set
        return moves;
    }
}
