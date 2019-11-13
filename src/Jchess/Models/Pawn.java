/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Enums.ThemeColor;
import Jchess.Enums.MoveType;
import Jchess.Enums.PieceType;
import java.util.EnumMap;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /**
     * Constructor for a Pawn Piece.
     * @param owner The Pawn's owner.
     */
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

    /**
     * Gets special moves for a pawn.
     * @param board Current board state.
     * @return  An empty vector.
     */
    @Override
    public Vector<Pair> getSpecialMoves(Board board) {
        Stream<Pair> pawnPromotionMoves = getPossibleMoves(board).stream().filter(
            // We're only interested in moves which would take the Pawn to the enemy's first rank.
            position -> position.getRow() == board.getEnemyPlayer().getHomeRow()
        );
        
        return pawnPromotionMoves.collect(Collectors.toCollection(Vector::new));
    }

    /**
     * Determines if the Pawn can advance two squares. Only possible for the Pawn's first move.
     * @param board A chess Board.
     * @return A Boolean indicating whether the Pawn can move up two squares.
     */
    private Boolean canAdvanceTwoTiles() {
        return getCurrentPosition().getRow() == getPlayer().getPawnRow();
    }
    
     /**
     * Evaluate pair for a pawn movement.
     * @param move Move to be performed.
     * @return  Pair for object to be moved to.
     */
    private Pair evaluatePair(Move move) {
        Pair position = this.getCurrentPosition();
        final int row = position.getRow();
        final int column = position.getColumn();
        
        return new Pair(row + rowOffset.get(move), column + columnOffset.get(move));
    }
    
    /**
     * Gets possible moves for pawn.
     * @param board Current board state.
     * @return  Vector of possible moves for the pawn.
     */
    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        Vector<Pair> moves = new Vector<>();
        
        boolean canAdvanceOneTile = false;
        
        Tile oneAbove = board.getTile(evaluatePair(Move.Up));
        if (canMoveTo(oneAbove, MoveType.EmptyTileOnly)) {
            moves.add(oneAbove.getPosition());
            canAdvanceOneTile = true;
        }
        
        if (canAdvanceOneTile && canAdvanceTwoTiles()) {
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
