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
     */
    public Rook(Player owner) {
        super(owner, PieceType.Rook);
    }

    @Override
    public Vector<Pair> getSpecialMoves(Board board) {
        return new Vector<>();
    }

    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        return this.getLaneMoves(board);
    }

}
