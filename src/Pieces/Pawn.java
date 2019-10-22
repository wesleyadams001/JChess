/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.Board;
import Board.Tile;
import Board.Pair;
import Enums.ThemeColor;
import Enums.MoveType;
import Enums.PieceType;
import Player.Player;
import java.util.EnumMap;
import java.util.Map;
import java.util.Vector;

/**
 * The Class that implements the move generation logic for the pawn
 * @author nehalpatel
 */
public class Pawn extends Piece {

    private enum Move {
        LeftDiagonal, RightDiagonal, Up, UpTwice
    };

    private final Map<Move, Integer> rowOffset;
    private final Map<Move, Integer> columnOffset;

    public Pawn(Player owner) {
        super(owner, PieceType.Pawn);

        rowOffset = new EnumMap<>(Move.class);
        columnOffset = new EnumMap<>(Move.class);

        // All moves are negated if the Piece's owner is player 2.
        // This is done to handle player 2's moves,
        // which go towards the bottom of the board.
        int x = (owner.getColor() == ThemeColor.LightPiece) ? 1 : -1;

        // One ahead of this Piece.
        rowOffset.put(Move.Up, x * -1);
        columnOffset.put(Move.Up, 0);

        // Two ahead of this Piece.
        rowOffset.put(Move.UpTwice, x * -2);
        columnOffset.put(Move.UpTwice, 0);

        // One ahead and to the left of this Piece.
        rowOffset.put(Move.LeftDiagonal, x * -1);
        columnOffset.put(Move.LeftDiagonal, x * -1);

        // One ahead and to the right of this Piece.
        rowOffset.put(Move.RightDiagonal, x * -1);
        columnOffset.put(Move.RightDiagonal, x * 1);
    }

    @Override
    public Vector<Pair> specialMoves(Board board) {
        Vector<Pair> moves = new Vector<>();
        
        return moves;
    }
    
    private Pair evaluatePair(Move move) {
        Pair position = this.getCurrentPosition();
        final int row = position.getRow();
        final int column = position.getColumn();
        
        return new Pair(row + rowOffset.get(move), column + columnOffset.get(move));
    }

    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        Vector<Pair> moves = new Vector<>();
        
        boolean canMoveOnce = false;
        
        Tile oneAbove = board.getTile(evaluatePair(Move.Up));
        if (canMoveTo(oneAbove, MoveType.EmptyTileOnly)) {
            moves.add(oneAbove.getPosition());
            canMoveOnce = true;
        }
        
        if (canMoveOnce && !this.hasTakenFirstMove()) {
            Tile twoAbove = board.getTile(evaluatePair(Move.UpTwice));
            if (canMoveTo(twoAbove, MoveType.EmptyTileOnly)) {
                moves.add(twoAbove.getPosition());
            }
        }

        Tile leftDiagonal = board.getTile(evaluatePair(Move.LeftDiagonal));
        if (canMoveTo(leftDiagonal, MoveType.EnemyPieceOnly)) {
            moves.add(leftDiagonal.getPosition());
        }
        
        Tile rightDiagonal = board.getTile(evaluatePair(Move.RightDiagonal));
        if (canMoveTo(rightDiagonal, MoveType.EnemyPieceOnly)) {
            moves.add(rightDiagonal.getPosition());
        }
        
        return moves;
    }

}
