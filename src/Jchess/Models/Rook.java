/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Models.*;
import Jchess.Models.Board;
import Jchess.Models.Pair;
import Jchess.Models.Tile;
import Jchess.Enums.MoveType;
import Jchess.Enums.PieceType;
import java.util.Vector;

/**
 * The class that implements the rook move generation logic
 * @author jonathanjoiner
 */
public class Rook extends Piece {

    public Rook(Player owner) {
        super(owner, PieceType.Rook);
    }

    @Override
    public Vector<Pair> specialMoves(Board board) {
        Vector<Pair> moves = new Vector<>();
        
        return moves;
    }

    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        return this.getLaneMoves(board);
    }

}
