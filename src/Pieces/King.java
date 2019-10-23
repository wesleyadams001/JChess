/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Board.*;
import Enums.*;
import Player.Player;
import java.util.Vector;
import java.util.stream.IntStream;

/**
 * The King piece
 * @author Wesley
 */
public class King extends Piece{

    public King(Player owner) {
        super(owner, PieceType.King);
    }

    /**
     * Returns Tiles found between the Rook and the King.
     */
    private Tile[] getTilesLeadingTo(Piece rook, Board board) {
        int start = rook.getCurrentPosition().getColumn();
        int end = this.getCurrentPosition().getColumn();
        
        // We need to flip the values so end is larger. The range function won't work otherwise.
        if (end - start < 0) {
            int temp = start;
            start = end;
            end = temp;
        }

        // squares holds indexes of all the Tiles between the Rook and the King.
        int[] squares = IntStream.range(start + 1, end).toArray();
        Tile[] tiles = new Tile[squares.length];

        for (int i = 0; i < squares.length; i++) {
            tiles[i] = board.getTile(new Pair(rook.getCurrentPosition().getRow(), squares[i]));
        }

        return tiles;
    }

    /**
     * Determines if the Rook exists and hasn't moved yet.
     * @param rook The Rook.
     * @return
     */
    public Boolean isRookEligibleForCastling(Piece rook) {
        if (rook == null) {
            return false;
        }

        if (rook.getPieceType() != PieceType.Rook) {
            return false;
        }

        if (rook.hasTakenFirstMove()) {
            return false;
        }

        return true;
    }

    /**
     * Determines if the Rook can be used to castle.
     * @param rook The Rook.
     * @param board The game board.
     * @return
     */
    private Boolean canCastleWithRook(Piece rook, Board board) {
        Player enemyPlayer = board.getEnemyPlayer();

        // The King cannot be in check, and it should not have moved already.
        Boolean kingIsNotInCheck = !(Check.pairUnderAttack(this.getCurrentPosition(), board, enemyPlayer));
        Boolean kingHasNotMovedYet = !(this.hasTakenFirstMove());

        // Validating King and Rook with heuristics.
        if (kingIsNotInCheck && kingHasNotMovedYet && isRookEligibleForCastling(rook)) {
            // Making sure each Tile between the Rook and the King is not under attack.
            for (Tile tile : this.getTilesLeadingTo(rook, board)) {
                if (tile.isOccupied() || enemyPlayer.canAttack(tile, board)) {
                    return false;
                }
            }
        } else {
            return false;
        }

        // The Rook can castle.
        return true;
    }

    @Override
    public Vector<Pair> specialMoves(Board board) {
        Vector<Pair> specialMoves = new Vector<>();

        // Get home row for Player.
        Tile[] homeRow = board.getCurrentPlayer().getHomeRow(board);

        // Find Rooks.
        Piece left = homeRow[0].getPiece();
        Piece right = homeRow[7].getPiece();

        if (canCastleWithRook(left, board)) {
            specialMoves.add(left.getCurrentPosition().offsettingColumn(1));
        }

        if (canCastleWithRook(right, board)) {
            specialMoves.add(right.getCurrentPosition().offsettingColumn(-1));
        }

        return specialMoves;
    }

    @Override
    public Vector<Pair> getPossibleMoves(Board board) {
        //The king moves exactly one square horizontally, vertically, or diagonally. 
        Vector<Pair> moves = new Vector<>();
        
        Pair position = this.getCurrentPosition();  //Initialize Pair object with current position
        final int row = position.getRow();          //Initialize variable to hold row position
        final int column = position.getColumn();    //Initialize variable to hold column position
        
        //Tiles for 8 squares around the king to test for possible moves
        Tile oneAbove = board.getTile(new Pair(row - 1, column));
        Tile oneRight = board.getTile(new Pair(row, column + 1));
        Tile oneLeft = board.getTile(new Pair(row, column - 1));
        Tile oneDown = board.getTile(new Pair(row + 1, column));
        Tile aboveRight = board.getTile(new Pair(row - 1, column + 1));
        Tile aboveLeft = board.getTile(new Pair(row - 1, column - 1));
        Tile downRight = board.getTile(new Pair(row + 1, column + 1));
        Tile downLeft = board.getTile(new Pair(row + 1, column - 1));
        
        //Test which squares the king can move to
        if(canMoveTo(oneAbove, MoveType.EmptyOrEnemyPiece)) { 
            moves.add(oneAbove.getPosition());      //Add oneAbove to possible move set
        }
        if(canMoveTo(oneRight, MoveType.EmptyOrEnemyPiece)){
            moves.add(oneRight.getPosition());      //Add oneRight to possible move set
        }
        if(canMoveTo(oneLeft, MoveType.EmptyOrEnemyPiece)){
            moves.add(oneLeft.getPosition());       //Add oneLeft to possible move set
        }
        if(canMoveTo(oneDown, MoveType.EmptyOrEnemyPiece)){
            moves.add(oneDown.getPosition());       //Add oneDown to possible move set
        }
        if(canMoveTo(aboveRight, MoveType.EmptyOrEnemyPiece)){
            moves.add(aboveRight.getPosition());    //Add aboveRight to possible move set
        }
        if(canMoveTo(aboveLeft, MoveType.EmptyOrEnemyPiece)){
            moves.add(aboveLeft.getPosition());     //Add aboveLeft to possible move set
        }
        if(canMoveTo(downRight, MoveType.EmptyOrEnemyPiece)){
            moves.add(downRight.getPosition());     //Add downRight to possible move set
        }
        if(canMoveTo(downLeft, MoveType.EmptyOrEnemyPiece)){
            moves.add(downLeft.getPosition());      //Add downLeft to possible move set
        }
        
        //Return possible move set
        return moves;
    }

}
