/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Enums.PieceType;
import java.util.Vector;

/**
 * The class that implements the rook move generation logic
 * @author jonathanjoiner
 */
public class Rook extends Piece {

    /**
     * A Rook Piece.
     * @param owner The Rook's owner.
     * @param move Whether the set the hasTakenFirstMove property as true.
     */
    public Rook(Player owner, Boolean move) {
        super(owner, PieceType.Rook, move);
    }

    /**
     * Gets special moves for Rook.
     * @param board Current board state.
     * @return  An empty vector.
     */
    @Override
    public Vector<Pair> getSpecialMoves(Board board) {
        return new Vector<>();
    }

    /**
     * Gets possible moves for Rook.
     * @param board Current board state.
     * @return  Vector of possible moves for rook.
     */
    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        return this.getLaneMoves(board);
    }

}
