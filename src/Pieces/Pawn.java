/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.Pair;
import Board.Grid;
import Board.Tile;
import java.util.Vector;

/**
 *
 * @author nehalpatel
 */
public class Pawn extends Piece {

    public Pawn() {
    }

    @Override
    public Vector<Pair> specialMoves() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector<Pair> getPossibleMoves(Grid grid) {
        
        Vector<Pair> moves = new Vector<>();
        
        Pair position = this.getCurrentPosition();
        final int row = position.getRow();
        final int column = position.getColumn();
        
        // check above
        if (!this.getHasTakenFirstMove()) {
            Tile twoAbove = grid.getTile(new Pair(row - 2, column));
            
            if (canMoveTo(twoAbove, MoveType.EmptyTileOrEnemyPiece)) {
                moves.add(twoAbove.getPosition());
            }
        }
        
        Tile oneAbove = grid.getTile(new Pair(row - 1, column));
        if (canMoveTo(oneAbove, MoveType.EmptyTileOrEnemyPiece)) {
            moves.add(oneAbove.getPosition());
        }

        // check diagonal
        Tile leftDiagonal = grid.getTile(new Pair(row - 1, column - 1));
        if (canMoveTo(leftDiagonal, MoveType.EnemyPieceOnly)) {
            moves.add(leftDiagonal.getPosition());
        }
        
        Tile rightDiagonal = grid.getTile(new Pair(row - 1, column + 1));
        if (canMoveTo(rightDiagonal, MoveType.EnemyPieceOnly)) {
            moves.add(rightDiagonal.getPosition());
        }
        
        return moves;
    }

}
