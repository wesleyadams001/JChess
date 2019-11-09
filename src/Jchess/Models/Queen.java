/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Models.Board;
import Jchess.Models.Pair;
import Jchess.Enums.PieceType;
import java.util.Vector;

/**
 * The class that contains the logic that 
 * @author jonathanjoiner
 */
public class Queen  extends Piece{
    
    public Queen(Player owner){
        super(owner, PieceType.Queen);
    }

    @Override
    public Vector<Pair> getSpecialMoves(Board board) {
        Vector<Pair> moves = new Vector<>();
        
        return moves;
    }

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
