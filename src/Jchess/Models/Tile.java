/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

import Jchess.Enums.ThemeColor;
import java.awt.Color;

/**
 *
 * @author nehalpatel
 */
public class Tile {

    private Piece piece;
    private boolean highlighted;
    private final Color color;
    private final Pair position;
    private boolean special;
    
    /**
     * Maintains reference to Piece. The Board object combines these into a 2D array to represent the 8x8 Chess grid.
     * @param position The Pair representing the Tile's place on the Board.
     */
    public Tile(Pair position) {
        this.position = position;
        this.color = ((position.getRow() + position.getColumn()) % 2 == 0) ? ThemeColor.LightTile.getColor() : ThemeColor.DarkTile.getColor();
    }

    /**
     * Retrieve the Piece that is currently on this Tile.
     * @return The Piece if it exists, null otherwise.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Place a Piece on this Tile.
     * @param newPiece
     */
    public void setPiece(Piece newPiece) {
        // TODO: handle case where a Piece is already on this Tile.

        if (newPiece != null) {
            newPiece.setCurrentPosition(position);
        }

        this.piece = newPiece;
    }

    /**
     * Removes the piece
     * @return
     */
    public Piece removePiece() {
        Piece removedPiece = this.piece;
        setPiece(null);
        return removedPiece;
    }

    /**
     * Check if the Tile is occupied with a Piece.
     * @return True if a Piece is on this Tile, false otherwise.
     */
    public boolean isOccupied() {
        return this.piece != null;
    }

    /**
     * Check if the Tile is a move for the currently selected Piece.
     * @return True if this Tile should be highlighted on the Board.
     */
    public boolean isHighlighted() {
        return highlighted;
    }

    /**
     * Marks the Tile as highlighted.
     * @param highlighted
     */
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    /**
     * Check if the Tile is a special move for the currently selected Piece.
     * @return
     */
    public boolean isSpecial() {
        return special;
    }

    /**
     * Marks the Tile as special.
     * @param special
     */
    public void setSpecial(boolean special) {
        this.special = special;
    }

    /**
     * Returns the Tile's color on the Board.
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     * Retrieve the Tile's position.
     * @return A Pair representing the Tile's position in the Board.
     */
    public Pair getPosition() {
        return this.position;
    }

}
