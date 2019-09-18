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
public abstract class Piece {

    private int player;
    private Pair position;
    private boolean selected;
    private boolean taken;
    private boolean hasTakenFirstMove;

    public enum MoveType {
        EmptyTileOnly,
        EnemyPieceOnly
    }
    
    /**
     *
     * @return
     */
    public abstract Vector<Pair> specialMoves();

    /**
     * Get a list of possible moves this Piece can make.
     * @param grid
     * @return A collection of Pairs representing the possible moves for this Piece.
     */
    public abstract Vector<Pair> getPossibleMoves(Grid grid);

    /**
     *
     * @return
     */
    public int getPlayer() {
        return player;
    }

    /**
     *
     * @param player
     */
    public final void setPlayer(int player) {
        this.player = player;
    }

    /**
     *
     * @return
     */
    public Pair getCurrentPosition() {
        return position;
    }

    /**
     *
     * @param position
     */
    public void setCurrentPosition(Pair position) {
        this.position = position;
    }

    /**
     *
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     *
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     *
     * @return
     */
    public boolean isTaken() {
        return taken;
    }

    /**
     *
     * @param taken
     */
    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    /**
     *
     * @return
     */
    public boolean getHasTakenFirstMove() {
        return hasTakenFirstMove;
    }

    /**
     *
     * @param hasTakenFirstMove
     */
    public void setHasTakenFirstMove(boolean hasTakenFirstMove) {
        this.hasTakenFirstMove = hasTakenFirstMove;
    }
    
    /**
     * Check whether a move to a Tile is technically legal.(Only ensures that the Tile is not occupied by a friendly Piece.)
     * @param tile The Tile which you'd like to move this Piece to.
     * @param type
     * @return True if Tile is empty or holds an enemy Piece, false otherwise.
     */
    public boolean canMoveTo(Tile tile, MoveType type) {
        if (tile == null) {
            return false;
        }
        
        if (tile.getOccupied()) {
            if (tile.getPiece().player == this.player) {
                return false;
            }
            
            if (type == MoveType.EmptyTileOnly) {
                return false;
            }
        } else {
            if (type == MoveType.EnemyPieceOnly) {
                return false;
            }
        }
        
        return true;
    }

}
