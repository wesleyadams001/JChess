/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Core.Constants;
import Jchess.Enums.MoveType;
import Jchess.Enums.PieceType;
import Jchess.Models.Tile;
import Jchess.Models.Pair;
import Jchess.Models.Board;
import java.util.Vector;

/**
 * The King piece
 * @author Wesley
 */
public class King extends Piece{

    public King(Player owner) {
        super(owner, PieceType.King);
    }
    
    private Boolean isEligibleToCastleWith(Piece rook, Board board) {
        Player enemyPlayer = getPlayer() == board.getLightPlayer() ? board.getDarkPlayer() : board.getLightPlayer();
        
        // The King cannot be in check, and it should not have moved already.
        Boolean kingIsNotInCheck = !(board.pairUnderAttack(this.getCurrentPosition(), enemyPlayer));
        Boolean kingHasNotMovedYet = !(this.hasTakenFirstMove());

        // Validating King and Rook with heuristics.
        return kingIsNotInCheck && kingHasNotMovedYet && isRookEligibleForCastling(rook);
    }
    
    public Boolean canCastleQueenSide(Board board) {
        // Get home row for Player and find left Rook.
        Tile[] homeRow = getPlayer().getHomeRow(board);
        Piece queenSideRook = homeRow[Constants.ROOK_COLUMN_QUEENSIDE].getPiece();
        Player enemyPlayer = getPlayer() == board.getLightPlayer() ? board.getDarkPlayer() : board.getLightPlayer();

        // Validating King and Rook with heuristics.
        if (isEligibleToCastleWith(queenSideRook, board)) {
            int pathStart = Constants.ROOK_COLUMN_QUEENSIDE + 1;
            int pathEnd = getCurrentPosition().getColumn();
            int rookSafetyException = pathStart;

            for (int position = pathStart; position < pathEnd; position++) {
                // Making sure each Tile between the Rook and the King is not under attack.
                Tile tile = board.getTile(new Pair(queenSideRook.getCurrentPosition().getRow(), position));
                if (tile.isOccupied()) {
                    return false;
                } else if (position != rookSafetyException && enemyPlayer.canAttack(tile, board)) {
                    // We have an extra Tile on the long side castle. We should ignore it,
                    // because when castling, it doesn't matter if the Rook's path is under attack.
                    return false;
                }
            }
        } else {
            return false;
        }
        
        return true;
    }
    
    public Boolean canCastleKingSide(Board board) {
        // Get home row for Player and find right Rook.
        Tile[] homeRow = getPlayer().getHomeRow(board);
        Piece kingSideRook = homeRow[Constants.ROOK_COLUMN_KINGSIDE].getPiece();
        Player enemyPlayer = getPlayer() == board.getLightPlayer() ? board.getDarkPlayer() : board.getLightPlayer();

        // Validating King and Rook with heuristics.
        if (isEligibleToCastleWith(kingSideRook, board)) {
            int pathStart = getCurrentPosition().getColumn() + 1;
            int pathEnd = Constants.ROOK_COLUMN_KINGSIDE;

            for (int position = pathStart; position < pathEnd; position++) {
                // Making sure each Tile between the Rook and the King is not under attack.
                Tile tile = board.getTile(new Pair(kingSideRook.getCurrentPosition().getRow(), position));
                if (tile.isOccupied() || enemyPlayer.canAttack(tile, board)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        
        return true;
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

        return !rook.hasTakenFirstMove();
    }

    @Override
    public Vector<Pair> getSpecialMoves(Board board) {
        Vector<Pair> specialMoves = new Vector<>();

        // Get home row for Player.
        Tile[] homeRow = getPlayer().getHomeRow(board);

        if (canCastleQueenSide(board)) {
            Piece queenSideRook = homeRow[Constants.ROOK_COLUMN_QUEENSIDE].getPiece();
            specialMoves.add(queenSideRook.getCurrentPosition().offsettingColumn(2));
        }
        
        if (canCastleKingSide(board)) {
            Piece kingSideRook = homeRow[Constants.ROOK_COLUMN_KINGSIDE].getPiece();
            specialMoves.add(kingSideRook.getCurrentPosition().offsettingColumn(-1));
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
