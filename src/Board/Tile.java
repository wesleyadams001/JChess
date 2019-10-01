/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Enums.ThemeColor;
import Pieces.Piece;
import java.awt.Color;

/**
 *
 * @author nehalpatel
 */
public class Tile {

    private Piece piece;
    private boolean highlighted;
    private Color color;
    private final Pair position;
    
    /**
     *
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
     * @param piece The Piece to place on this Tile.
     */
    public void setPiece(Piece newPiece) {
        // TODO: handle case where a Piece is already on this Tile.

        if (newPiece != null) {
            newPiece.setCurrentPosition(position);
        }

        this.piece = newPiece;
    }

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
     * Check if the Tile is highlighted.
     * @return True if this Tile should be highlighted on the Board.
     */
    public boolean isHighlighted() {
        return highlighted;
    }

    /**
     * 
     * @param highlighted
     */
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    /**
     *
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @param color
     */
    public void setColor(ThemeColor themeColor) {
        this.color = themeColor.getColor();
    }

    /**
     * Check if the Tile is part of the top or bottom row.
     * @return True if this Tile is in either the topmost or bottommost rows.
     */
    public boolean isHomeRow() {        
        return (this.position.getRow() == 0) || (this.position.getRow() == 7);
    }
    
    /**
     *
     */
    public void click() {
    }
    
    /**
     * Retrieve the Tile's position.
     * @return A Pair representing the Tile's position in the Board.
     */
    public Pair getPosition() {
        return this.position;
    }

}
